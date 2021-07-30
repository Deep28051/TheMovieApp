package com.appintuitions.themovieapp.repo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.appintuitions.themovieapp.viewmodel.models.Movie
import com.appintuitions.themovieapp.viewmodel.models.MovieTypes

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies where movie_type = :type")
    fun getAll(type: MovieTypes): List<Movie>

    @Insert(onConflict = REPLACE)
    fun insertAll(users: ArrayList<Movie>)

    fun insertAll(users: ArrayList<Movie>, type: MovieTypes){
        users.forEach {
            it.movie_type = type
        }
        //deleteAll(type)
        insertAll(users)
    }

    @Delete
    fun delete(user: Movie)

    @Query("delete from movies where movie_type = :type")
    fun deleteAll(type: MovieTypes)

}