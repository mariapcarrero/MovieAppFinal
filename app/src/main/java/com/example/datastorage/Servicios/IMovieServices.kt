package com.example.datastorage.Servicios

import com.example.datastorage.Modelos.Movie

interface IMovieServices {
   // fun verifyUser(user: Movie) : Boolean
    fun saveMovie(movie: Movie)
    fun consultMovies() : List<Movie>?
    fun existsMovie(movie : Movie) : Boolean
}


