package to.chip.dogsgallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import to.chip.dogsgallery.CDGApplication
import to.chip.dogsgallery.R
import to.chip.dogsgallery.constants.CDGConstants
import to.chip.dogsgallery.data.model.network.CDGBreed
import to.chip.dogsgallery.data.model.ui.CDGBreedItem
import to.chip.dogsgallery.data.model.ui.CDGImageItem
import to.chip.dogsgallery.data.persistance.CDGMainDao
import to.chip.dogsgallery.network.CDGDogsService
import to.chip.dogsgallery.utils.CDGUtils
import javax.inject.Inject

@HiltViewModel
class CDGMainViewModel @Inject constructor(
    private val mainDao: CDGMainDao,
    private val dogsService: CDGDogsService
) : ViewModel() {

    var breedsList = mainDao.getBreedsList()
    var errorLiveData = MutableLiveData<(String?) -> Unit> {}
    var totalCount = MutableLiveData<Int?>()

    fun getImageList(breedName: String): LiveData<List<CDGImageItem>?> {
        return mainDao.getImagesList(breedName)
    }

    init {
        viewModelScope.launch {
            loadBreedsList {
                (errorLiveData.value)?.let { error -> error(it) }
            }
        }

        totalCount.postValue(0)
    }

    suspend fun loadBreedsList(error: (String?) -> Unit) = withContext(Dispatchers.IO) {
        val breed = breedsList?.value
        if (breed == null || breed.isEmpty() ) {
            try {
                val result = dogsService.fetchBreeds()
                if (result.status == CDGConstants.SERVER_SUCCESS) {
                    convertAndSaveBreedsData(result)
                } else {
                    error(
                        CDGApplication.instance.applicationContext.getString(R.string.internal_server_error)
                    )
                }
            } catch (e: retrofit2.HttpException) {
                error(CDGApplication.instance.applicationContext.getString(R.string.communication_error))
            } catch (e: Exception) {
                error(CDGApplication.instance.applicationContext.getString(R.string.unknown_error))
            }
        }
    }

    suspend fun loadImageList(breedName: String, error: (String?) -> Unit) = withContext(Dispatchers.IO) {
        try {
            val result = dogsService.fetchImagesList(getModifiedBreedName(breedName))

            if (result.status == CDGConstants.SERVER_SUCCESS) {
                result.message.let {
                    if (it.size < 10) {
                        it.forEach {
                            mainDao.insertImage(
                                CDGImageItem(
                                    System.currentTimeMillis(),
                                    breedName,
                                    it
                                )
                            )
                        }
                    } else {
                        it.shuffle()
                        it.forEach {
                            mainDao.insertImage(
                                CDGImageItem(
                                    System.currentTimeMillis(),
                                    breedName,
                                    it
                                )
                            )
                        }
                    }
                }
            } else {
                error(
                    CDGApplication.instance.applicationContext.getString(R.string.internal_server_error)
                )
            }
        } catch (e: retrofit2.HttpException) {
            error(CDGApplication.instance.applicationContext.getString(R.string.communication_error))
        } catch (e: Exception) {
            error(CDGApplication.instance.applicationContext.getString(R.string.unknown_error))
        }
    }

    private fun getModifiedBreedName(breedName: String): String {
        return if (breedName.contains(" ")) {
            breedName.split(" ")[1] + "/" + breedName.split(" ")[0]
        } else {
            breedName
        }
    }

    private fun convertAndSaveBreedsData(result: CDGBreed) {
        var index = 1
        result.message.forEach {
            if (it.value.isEmpty()) {
                // Single breed
                mainDao.insertBreed(
                    CDGBreedItem(
                        index++,
                        it.key,
                        "",
                        CDGUtils.random17()
                        )
                )
            } else {
                // Muptiple breeds
                it.value.forEach { prefix ->
                    mainDao.insertBreed(
                        CDGBreedItem(
                            index++,
                            "$prefix ${it.key}",
                            "",
                            CDGUtils.random17()
                            )
                    )
                }
            }
        }

        totalCount.postValue(index)
    }
}