package com.appintuitions.rvkotlin.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.appintuitions.rvkotlin.viewmodel.models.Movie
import com.appintuitions.rvkotlin.viewmodel.models.MovieSort

class Util {

    companion object {
        fun sortList(sort: MovieSort, list: ArrayList<Movie>) {

            when (sort) {
                MovieSort.TITLE -> list.sortWith { o1, o2 -> o1.title?.compareTo(o2.title!!)!! }
                MovieSort.RATING -> list.sortWith { o1, o2 -> o1.voteAverage?.compareTo(o2.voteAverage!!)!! }
                MovieSort.POPULARITY -> list.sortWith { o1, o2 -> o1.popularity?.compareTo(o2.popularity!!)!! }
            }

        }

        fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
        }
    }

}