package com.zevans.supermoviev1.utils


import com.zevans.supermoviev1.data.SuperMovieServiceApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit(): SuperMovieServiceApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SuperMovieServiceApi::class.java)
        }
    }
}