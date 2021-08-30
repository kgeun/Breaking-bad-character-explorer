package com.kgeun.bbcharacterexplorer.network

import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import retrofit2.http.GET

interface BBService {

    @GET("/api/characters")
    suspend fun fetchCharacters(): List<BBCharacter>

    companion object {
        const val BREAKING_BAD_API_URL = "https://breakingbadapi.com/"
    }
}