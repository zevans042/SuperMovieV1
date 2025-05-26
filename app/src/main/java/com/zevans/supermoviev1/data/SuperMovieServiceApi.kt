package com.zevans.supermoviev1.data

import com.zevans.supermoviev1.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SuperMovieServiceApi {

    @GET(".")
    suspend fun searchByName(
        @Query("apikey") apiKey: String = Constants.API_ACCESS_TOKEN,
        @Query("s") query: String
    ): Response<SuperMovieResponse>

    @GET(".")
    suspend fun findById(
        @Query("apikey") apiKey: String = Constants.API_ACCESS_TOKEN,
        @Query("i") identifier: String
    ): Response<Supermovie>
}

