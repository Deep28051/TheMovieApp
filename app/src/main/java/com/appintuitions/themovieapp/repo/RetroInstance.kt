package com.appintuitions.themovieapp.repo

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroInstance {

    companion object {
        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        private lateinit var retro: Retrofit
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private const val API_KEY = "33cae82b982fa5d63df458b6322d5681"


        init {
            httpClient.addInterceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("language", "en-US")
                    .build()

                // Request customization: add request headers
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
        }

        fun getInstance(): Retrofit {

            if (!::retro.isInitialized) {
                retro = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }

            return retro

        }
    }

}