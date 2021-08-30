package com.kgeun.bbcharacterexplorer.viewmodel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.kgeun.bbcharacterexplorer.BBApplication
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.constants.CDGConstants
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.model.ui.BBSeasonItem
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.network.BBService
import com.kgeun.bbcharacterexplorer.utils.BBTypeConverter
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
    var seasonListStr = ""
    var searchKeyword = ""

    var charactersLiveData = MediatorLiveData<List<BBCharacter>?>().apply {
        addSource(defaultCharactersList) { value ->
            setValue(value)
        }
        addSource(searchKeywordLiveData) { value ->
            searchKeyword = value

            if (value == "" && seasonListStr == "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.getCharactersListSync())
                    }
                }
            } else if (value == "" && seasonListStr != "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.findCharactersListBySeasonListSync(seasonListStr))
                    }
                }
            } else if (value != "" && seasonListStr == "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.findCharactersListByKeywordSync(value))
                    }
                }
            } else {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(
                            mainDao.findCharactersListByKeywordAndSeasonListSync(
                                value,
                                seasonListStr
                            )
                        )
                    }
                }
            }
        }

        addSource(seasonLiveData) check@{ value ->
            if (value == null)
                return@check

            val str = BBTypeConverter().writingStringFromList(
                value.values.filter { it.selected }.map { it.season }.toList()
            )
            seasonListStr = str

            if (str == "" && searchKeyword == "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.getCharactersListSync())
                    }
                }
            } else if (str == "" && searchKeyword != "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.findCharactersListByKeywordSync(searchKeyword))
                    }
                }
            } else if (str != "" && searchKeyword == "") {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(mainDao.findCharactersListBySeasonListSync(str))
                    }
                }
            } else {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        postValue(
                            mainDao.findCharactersListByKeywordAndSeasonListSync(
                                searchKeyword,
                                str
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