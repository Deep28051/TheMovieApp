package com.appintuitions.rvkotlin.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appintuitions.rvkotlin.APIService
import com.appintuitions.rvkotlin.repo.AppDatabase
import com.appintuitions.rvkotlin.repo.RetroInstance.Companion.getInstance
import com.appintuitions.rvkotlin.util.Util.Companion.isNetworkAvailable
import com.appintuitions.rvkotlin.viewmodel.models.Movie
import com.appintuitions.rvkotlin.viewmodel.models.MovieTypes
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import kotlinx.coroutines.launch

class MainViewModel(var context: Application) : AndroidViewModel(context) {

    private val apiService = getInstance().create(APIService::class.java)

    val latestMovies = MutableLiveData<ArrayList<Movie>>()
    val topMovies = MutableLiveData<ArrayList<Movie>>()
    val popularMovies = MutableLiveData<ArrayList<Movie>>()

    val isLoading = MutableLiveData<Boolean>()

    val appDatabase: AppDatabase = AppDatabase.getAppDatabase(context)

    fun loadLatestMovies() {
        if (isNetworkAvailable(context))
            viewModelScope.launch {
                isLoading.postValue(true)
                val response = apiService.getLatestMovies()

                Log.e("response latest", response.body().toString())

                if (response.isSuccessful) {
                    val list = ArrayList<Movie>()

                    val results = response.body()?.getAsJsonArray("results")

                    results?.forEach {
                        val movie = Gson().fromJson(it, Movie::class.java)
                        list.add(movie)
                    }
                    appDatabase.userDao()?.insertAll(list, MovieTypes.PLAYING)
                    isLoading.postValue(false)
                    latestMovies.postValue(list)
                }
            }
        else {
            latestMovies.postValue(appDatabase.userDao()?.getAll(MovieTypes.PLAYING) as ArrayList<Movie>?)
            isLoading.postValue(false)
        }
    }

    fun loadPopularMovies() {

        if (isNetworkAvailable(context))
            viewModelScope.launch {
                isLoading.postValue(true)
                val response = apiService.getPopularMovies()

                Log.e("response popular", response.body().toString())

                if (response.isSuccessful) {

                    val list = ArrayList<Movie>()

                    val results = response.body()?.getAsJsonArray("results")

                    results?.forEach {
                        val movie = Gson().fromJson(it, Movie::class.java)
                        list.add(movie)
                    }
                    appDatabase.userDao()?.insertAll(list, MovieTypes.POPULAR)
                    isLoading.postValue(false)
                    popularMovies.postValue(list)
                }

            }
        else {
            popularMovies.postValue(appDatabase.userDao()?.getAll(MovieTypes.POPULAR) as ArrayList<Movie>?)
            isLoading.postValue(false)
        }
    }

    fun loadTopMovies() {
        if (isNetworkAvailable(context))
            viewModelScope.launch {
                isLoading.postValue(true)
                val response = apiService.getTopRatedMovies()

                Log.e("response top", response.body().toString())

                if (response.isSuccessful) {

                    val list = ArrayList<Movie>()

                    val results = response.body()?.getAsJsonArray("results")

                    results?.forEach {
                        val movie = Gson().fromJson(it, Movie::class.java)
                        list.add(movie)
                    }
                    appDatabase.userDao()?.insertAll(list, MovieTypes.TOP_RATED)
                    isLoading.postValue(false)
                    topMovies.postValue(list)
                }
            }
        else {
            topMovies.postValue(appDatabase.userDao()?.getAll(MovieTypes.TOP_RATED) as ArrayList<Movie>?)
            isLoading.postValue(false)
        }
    }

}