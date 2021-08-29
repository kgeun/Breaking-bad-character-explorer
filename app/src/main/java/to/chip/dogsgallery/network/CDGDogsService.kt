package to.chip.dogsgallery.network

import retrofit2.http.GET
import retrofit2.http.Path
import to.chip.dogsgallery.data.model.network.CDGBreed
import to.chip.dogsgallery.data.model.network.CDGImageList

interface CDGDogsService {

    @GET("/api/breeds/list/all")
    suspend fun fetchBreeds(): CDGBreed

    @GET("/api/breed/{breedName}/images")
    suspend fun fetchImagesList(
        @Path(value = "breedName", encoded = true) breedName: String
    ): CDGImageList

    companion object {
        const val DOGS_GALLERY_API_URL = "https://dog.ceo/"
    }
}