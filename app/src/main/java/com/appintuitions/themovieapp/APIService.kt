package com.appintuitions.themovieapp

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId : Int) :Response<JsonObject>

    @GET("now_playing")
    suspend fun getLatestMovies(@Query("region") region:String = "IN"):Response<JsonObject>

    @GET("popular")
    suspend fun getPopularMovies(@Query("region") region:String = "IN"):Response<JsonObject>

    @GET("top_rated")
    suspend fun getTopRatedMovies(@Query("region") region:String = "IN"):Response<JsonObject>


}