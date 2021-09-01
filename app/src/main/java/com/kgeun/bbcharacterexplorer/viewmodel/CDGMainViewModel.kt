package com.kgeun.bbcharacterexplorer.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.room.Query
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kgeun.bbcharacterexplorer.BBApplication
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.constants.CDGConstants
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.model.ui.BBSeasonItem
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.network.BBService
import com.kgeun.bbcharacterexplorer.utils.BBTypeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CDGMainViewModel @Inject constructor(
    private val mainDao: BBMainDao,
    private val bbService: BBService
) : ViewModel() {

    var defaultCharactersList = mainDao.getCharactersList()
    var errorLiveData = MutableLiveData<(String?) -> Unit> {}
    var searchKeywordLiveData = MutableLiveData<String>()
    var seasonLiveData = MutableLiveData<HashMap<Int,BBSeasonItem>?>()
    var searchKeyword = ""

    var charactersLiveData = MediatorLiveData<List<BBCharacter>?>().apply {

        addSource(defaultCharactersList) { value ->
            setValue(value)
        }

        addSource(searchKeywordLiveData) { value ->
            searchKeyword = value
            val seasonList = seasonLiveData.value?.let {
                seasonLiveData.value!!.values.filter { it.selected }.map { it.season }.toList()
            } ?: listOf()

            if (value == "" && numberOfSelectedSeasons(seasonLiveData.value) == 0) {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.getCharactersListSync())
                    }
                }
            } else if (value == "" && numberOfSelectedSeasons(seasonLiveData.value) > 0) {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(
                            mainDao.findCharactersListBySeasonListSync(
                                createDynamicQueryForSeasonSearch(
                                    seasonList
                                )
                            )
                        )
                    }
                }
            } else if (value != "" && numberOfSelectedSeasons(seasonLiveData.value) == 0) {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.findCharactersListByKeywordSync(searchKeyword))
                    }
                }
            } else {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(
                            mainDao.findCharactersListByKeywordAndSeasonListSync(
                                createDynamicQueryForKeywordAndSeasonSearch(
                                    value,
                                    seasonList
                                )
                            )
                        )
                    }
                }
            }
        }

        addSource(seasonLiveData) check@{ value ->
            if (value == null)
                return@check

            val seasonList = value.values.filter { it.selected }.map { it.season }.toList()

            Log.i("kglee", "seasonList: $seasonList")

            if (numberOfSelectedSeasons(value) == 0 && searchKeyword == "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.getCharactersListSync())
                    }
                }
            } else if (numberOfSelectedSeasons(value) == 0 && searchKeyword != "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(
                            mainDao.findCharactersListByKeywordSync(
                                searchKeyword
                            )
                        )
                    }
                }
            } else if (numberOfSelectedSeasons(value) > 0 && searchKeyword == "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(
                            mainDao.findCharactersListBySeasonListSync(
                                createDynamicQueryForSeasonSearch(
                                    seasonList
                                )
                            )
                        )
                    }
                }
            } else {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(
                            mainDao.findCharactersListByKeywordAndSeasonListSync(
                                createDynamicQueryForKeywordAndSeasonSearch(
                                    searchKeyword,
                                    seasonList
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            loadCharactersList {
                (errorLiveData.value)?.let { error -> error(it) }
            }
        }
        searchKeywordLiveData.postValue("")
        seasonLiveData.postValue(CDGConstants.seasonItems as HashMap<Int, BBSeasonItem>)
    }

    fun numberOfSelectedSeasons(list :HashMap<Int, BBSeasonItem>?) :Int =
        list?.let { list.values.filter { it.selected }.map { it.season }.toList().size } ?: 0

    fun createDynamicQueryForSeasonSearch(list: List<Int>): SupportSQLiteQuery {
        val args: ArrayList<Any> = ArrayList()

        val sb = StringBuffer("SELECT * FROM character WHERE appearance LIKE ?")
        args.add("%${list[0]}%")

        if (list.size > 2) {
            for (i in 1 until list.size) {
                sb.append(" AND appearance LIKE ?")
                args.add("%${list[i]}%")
            }
        }

        sb.append(" ORDER BY char_id ASC;")

        return SimpleSQLiteQuery(sb.toString(), args.toArray());
    }

    fun createDynamicQueryForKeywordAndSeasonSearch(value: String, list: List<Int>): SupportSQLiteQuery {
        val args = ArrayList<Any>()

        val sb = StringBuffer("SELECT * FROM character WHERE appearance LIKE ?")
        args.add("%${list[0]}%")

        if (list.size > 2) {
            for (i in 1 until list.size) {
                sb.append(" AND appearance LIKE ?")
                args.add("%${list[i]}%")
            }
        }

        sb.append(" AND name LIKE ?")
        args.add("%$value%")

        sb.append(" ORDER BY char_id ASC;")

        return SimpleSQLiteQuery(sb.toString(), args.toArray());
    }

    suspend fun loadCharactersList(error: (String?) -> Unit) = withContext(Dispatchers.IO) {
        val charactersList = defaultCharactersList.value
        if (charactersList == null || charactersList.isEmpty() ) {
            try {
                val result = bbService.fetchCharacters()
                saveCharactersData(result)
            } catch (e: retrofit2.HttpException) {
                error(BBApplication.instance.applicationContext.getString(R.string.communication_error))
            } catch (e: Exception) {
                error(BBApplication.instance.applicationContext.getString(R.string.unknown_error))
            }
        }
    }

    private fun saveCharactersData(result: List<BBCharacter>) {
        mainDao.insertCharacter(result)
    }
}