package com.example.datastorage.Servicios

import com.example.datastorage.Modelos.Movie
import android.content.Context
import android.support.v7.app.AppCompatActivity

class CheckMovieServices(context: Context) {
    private lateinit var movie : Movie
    private var dbConnection : MoviesDBServices = MoviesDBServices(context)
   fun existsMovie(movie : Movie) : Boolean {
        return dbConnection.existsMovie(movie)
    }
    fun saveMovie(movie: Movie, context : AppCompatActivity) {
        MoviesDBServices(context).saveMovie(movie)
    }
}