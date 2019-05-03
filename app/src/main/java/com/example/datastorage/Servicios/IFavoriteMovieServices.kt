package com.example.datastorage.Servicios

import com.example.datastorage.Modelos.MoviexUser
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User

interface IFavoriteMovieServices {
    // fun verifyUser(user: Movie) : Boolean
    fun saveFavoriteMovie(movie: MoviexUser)
    fun consultFavoriteMovies(id: Int) : List<Movie>?
    fun markAsFavorite(user : User, movie : Movie)
    fun unmarkAsFavorite(user : User, movie: Movie)
    fun checkFavoriteMovie(idUser : Int?, idMovie : Int?): Boolean
    //fun existsMovie(movie : MoviexUser) : Boolean
}


