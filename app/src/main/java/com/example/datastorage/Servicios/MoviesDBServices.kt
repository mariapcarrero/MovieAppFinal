package com.example.datastorage.Servicios

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import com.example.datastorage.Modelos.User
import com.example.datastorage.Modelos.Movie

class MoviesDBServices (context: Context) : SQLiteOpenHelper(context, "MoviesDBService", null, 1), IMovieServices
{
    override fun onCreate(db: SQLiteDatabase?) {
         restoreDB(db)
    }
    fun restoreDB(db : SQLiteDatabase?) {
       // val sqlDestroy : String = "DROP TABLE movies";
        //db?.execSQL(sqlDestroy)

        val sql : String = "CREATE TABLE IF NOT EXISTS movies(idMovie integer primary key autoincrement," +
                " name text," +
                " synopsis text," +
                " duration integer," +
                " year integer," +
                " score float," +
                " director text," +
                " image BLOB)"
        db?.execSQL(sql)
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Sin implementación")
    }

    override fun existsMovie(movie: Movie) : Boolean
    {
        val sql : String = "SELECT name FROM movies" +
                " where name='${movie.name}'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var returnValue : Boolean = result.moveToFirst()

        this.close()
        return returnValue
    }


    override fun saveMovie(movie: Movie)
    {

        var localUser = ContentValues()
        localUser.put("name", movie.name)
        localUser.put("synopsis", movie.synopsis)
        localUser.put("duration", movie.duration)
        localUser.put("year", movie.year)
        localUser.put("score", movie.score)
        localUser.put("director", movie.director)
        localUser.put("image", movie.image)

        this.executeModification(localUser)
    }

    override fun consultMovies(): List<Movie>?
    {
        val sql : String = "SELECT idMovie, name, synopsis, duration, year, score, director, image FROM movies"
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


    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor
    {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModification(movie: ContentValues)
    {
        val bd = this.writableDatabase
        restoreDB(bd)
        bd.insert("movies", null, movie)
        bd.close()
    }
}