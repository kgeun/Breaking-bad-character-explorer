package com.kgeun.bbcharacterexplorer.viewmodel

import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kgeun.bbcharacterexplorer.BBApplication
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.constants.BBConstants
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.model.ui.BBSeasonItem
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.network.BBService
import com.kgeun.bbcharacterexplorer.utils.BBUtils.createDynamicQueryForKeywordAndSeasonSearch
import com.kgeun.bbcharacterexplorer.utils.BBUtils.createDynamicQueryForSeasonSearch
import com.kgeun.bbcharacterexplorer.utils.BBUtils.numberOfSelectedSeasons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BBMainViewModel @Inject constructor(
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
        seasonLiveData.postValue(BBConstants.seasonItems.clone() as HashMap<Int, BBSeasonItem>)
    }

    fun getCharacterByCharId(charId: Long): LiveData<BBCharacter?> {
        return mainDao.getCharacterByCharId(charId)
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