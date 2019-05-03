package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.datastorage.Adapters.MoviesListAdapter
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.R
import com.example.datastorage.Servicios.FavoriteMovieDBServices
//import com.example.datastorage.Servicios.FavoriteMoviesDBServices
import com.google.gson.Gson

import com.example.datastorage.Modelos.User
import com.example.datastorage.Servicios.PermissionService
import com.example.datastorage.Servicios.UserDBServices

class FavoriteMoviesListActivity : AppCompatActivity()
{
    private lateinit var listView: ListView
    private lateinit var data : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritemovies_list)
        data = this.intent.getStringExtra("user")
        val user : User = Gson().fromJson(data, User::class.javaObjectType)
        var userId = 1
        if (user.idUser != null) {
            userId = user.idUser
        }

        val listPosts: List<Movie>? = FavoriteMovieDBServices(this).consultFavoriteMovies(userId)

        listView = findViewById<ListView>(R.id.listMovies) as ListView
        val adapter = MoviesListAdapter(this, listPosts)
        listView.adapter = adapter

        listView.setClickable(true)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, MovieInformationActivity::class.java)
            var jsonUser = Gson().toJson(adapter.getItem(i))
            intent.putExtra("user", data)
            intent.putExtra("movie", jsonUser)

            startActivity(intent)
        }
    }
    fun goBackMoviesList(view : View) {
        val intent = Intent(this, MoviesListActivity::class.java)
        intent.putExtra("user", data)
        startActivity(intent)
    }

    fun goToFavorites(view : View) {
        val intent = Intent(this, MovieRegisterActivity::class.java)
        intent.putExtra("user", data)
        startActivity(intent)
    }
}
