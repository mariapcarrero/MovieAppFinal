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
import com.example.datastorage.Modelos.User
import com.example.datastorage.R
import com.example.datastorage.Servicios.MoviesDBServices
import com.google.gson.Gson


class MoviesListActivity : AppCompatActivity()
{
    private lateinit var listView: ListView
    private lateinit var userData : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        val listPosts: List<Movie>? = MoviesDBServices(this).consultMovies()
        listView = findViewById<ListView>(R.id.listMovies) as ListView
        val adapter = MoviesListAdapter(this, listPosts)
        listView.adapter = adapter

        userData = this.intent.getStringExtra("user")

        listView.setClickable(true)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, MovieInformationActivity::class.java)
            var jsonUser = Gson().toJson(adapter.getItem(i))
            intent.putExtra("user", userData)
            intent.putExtra("movie", jsonUser)
            startActivity(intent)
        }
    }
    fun goBackMoviesList(view : View) {
        val intent = Intent(this, MovieRegisterActivity::class.java)
        intent.putExtra("user", userData)
        startActivity(intent)
    }

    fun goToFavorites(view : View) {
        val intent = Intent(this, FavoriteMoviesListActivity::class.java)
        intent.putExtra("user", userData)
        startActivity(intent)
    }
}
