package com.example.datastorage.Servicios

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.widget.Toast
import com.example.datastorage.Modelos.MoviexUser
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User

class FavoriteMovieDBServices(context: Context) : SQLiteOpenHelper(context, "MoviesDBService", null, 1), IFavoriteMovieServices
{
    override fun onCreate(db: SQLiteDatabase?) {
        restoreDB(db)
    }
    fun restoreDB(db : SQLiteDatabase?) {
        val sql : String = "CREATE TABLE IF NOT EXISTS moviexuser(idMoviexUser integer primary key AUTOINCREMENT," +
                " idMovie integer," +
                " idUser integer)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Sin implementación")
    }

    override fun markAsFavorite(user: User, movie: Movie) {
        val movie : MoviexUser = MoviexUser(null, movie.idMovie, user.idUser)
        saveFavoriteMovie(movie)
    }

    override fun unmarkAsFavorite(user: User, movie: Movie) {
        val sql : String = "DELETE FROM moviexuser WHERE idUser='${user.idUser}' AND idMovie='${movie.idMovie}'"
        this.writableDatabase.execSQL(sql)
    }

    override fun checkFavoriteMovie(idUser: Int?, idMovie: Int?): Boolean{

        val sql : String = "SELECT idMoviexUser FROM moviexuser WHERE idUser ='${idUser}' and idMovie = '${idMovie}'"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var returnValue : Boolean = result.moveToFirst()
        this.close()
        return returnValue
    }
    

    override fun consultFavoriteMovies(id: Int): List<Movie>?
    {
        val sql : String = "SELECT * FROM movies JOIN moviexuser USING(idMovie) WHERE moviexuser.idUser = '$id'"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var listMovies : MutableList<Movie>? = ArrayList<Movie>()
        result.moveToFirst()

        var inside : HashSet<String> = HashSet<String>()
        while(!result.isAfterLast)
        {
            val name = result.getString(1)
            var movie : Movie = Movie(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                result.getInt(4),
                result.getFloat(5),
                result.getString(6),
                result.getBlob(7)
            )
            if (!inside.contains(name)) {
                listMovies?.add(movie)
                inside.add(name)
            }
            result.moveToNext()
        }

        return listMovies
    }

    override fun saveFavoriteMovie(movie: MoviexUser)
    {

        var localUser = ContentValues()
        localUser.put("idMovie", movie.idMovie)
        localUser.put("idUser", movie.idUser)
        this.executeModification(localUser)
    }

    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor
    {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModification(movie: ContentValues)
    {
        restoreDB(this.writableDatabase)
        val bd = this.writableDatabase
        bd.insert("moviexuser", null, movie)
        bd.close()
    }
}
//package com.example.datastorage.Servicios
/*
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.widget.Toast
import com.example.datastorage.Modelos.MoviexUser
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User

class FavoriteMovieDBServices(context: Context) : SQLiteOpenHelper(context, "MoviesDBService", null, 1), IFavoriteMovieServices
{
    override fun onCreate(db: SQLiteDatabase?) {
        //restoreDB(db)

    }
    fun restoreDB(db : SQLiteDatabase?) {
        val sql : String = "CREATE TABLE IF NOT EXISTS moviexuser(idMoviexUser int primarykey," +
                " idMovie integer," +
                " idUser integer)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {

    }

    override fun markAsFavorite(user: User, movie: Movie) {
        val movie : MoviexUser = MoviexUser(null, movie.idMovie, user.idUser)
        saveFavoriteMovie(movie)
    }

    override fun consultFavoriteMovies(id: Int): List<Movie>?
    {
        val sql : String = "SELECT movies.idMovie, movies.name, movies.synopsis, movies.duration, movies.year, movies.score, movies.director, movies.image FROM moviexuser JOIN movies USING (idMovie) where moviexuser.idUser = '$id'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var listMovies : MutableList<Movie>? = ArrayList<Movie>()
        result.moveToFirst()

        var inside : HashSet<String> = HashSet<String>()
        while(!result.isAfterLast)
        {
            val name = result.getString(1)
            var movie : Movie = Movie(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                result.getInt(4),
                result.getFloat(5),
                result.getString(6),
                result.getBlob(7)
            )
            if (!inside.contains(name)) {
                listMovies?.add(movie)
                inside.add(name)
             }
            result.moveToNext()
        }

        return listMovies
    }

    override fun saveFavoriteMovie(movie: MoviexUser)
    {

        var localUser = ContentValues()
        localUser.put("idMovie", movie.idMovie)
        localUser.put("idUser", movie.idUser)
        this.executeModification(localUser)
    }

    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor
    {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModification(movie: ContentValues)
    {
        restoreDB(this.writableDatabase)
        val bd = this.writableDatabase
        bd.insert("moviexuser", null, movie)
        bd.close()
    }
}*/