package com.zevans.supermoviev1.utils


import com.zevans.supermoviev1.data.SuperMovieServiceApi
import com.zevans.supermoviev1.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit(): SuperMovieServiceApi {
            // Crear un interceptor para logging (nivel BODY para máximo detalle)
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Construir cliente OkHttp con interceptor de logging
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            // Crear Retrofit con baseUrl, cliente y GsonConverterFactory
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Crear e instanciar la interfaz de servicio API
            return retrofit.create(SuperMovieServiceApi::class.java)
        }
    }
}

