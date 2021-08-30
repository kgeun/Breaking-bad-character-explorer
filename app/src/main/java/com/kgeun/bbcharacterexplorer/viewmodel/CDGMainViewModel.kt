package com.kgeun.bbcharacterexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.kgeun.bbcharacterexplorer.BBApplication
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.network.BBService
import javax.inject.Inject

@HiltViewModel
class CDGMainViewModel @Inject constructor(
    private val mainDao: BBMainDao,
    private val bbService: BBService
) : ViewModel() {

    var charactersList = mainDao.getCharactersList()
    var errorLiveData = MutableLiveData<(String?) -> Unit> {}

    init {
        viewModelScope.launch {
            loadCharactersList {
                (errorLiveData.value)?.let { error -> error(it) }
            }
        }
    }

    suspend fun loadCharactersList(error: (String?) -> Unit) = withContext(Dispatchers.IO) {
        val charactersList = charactersList.value
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