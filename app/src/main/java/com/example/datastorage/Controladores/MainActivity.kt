package com.example.datastorage.Controladores

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Modelos.User
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.MoviexUser
import com.example.datastorage.R
import com.example.datastorage.Servicios.LoginServices
import com.example.datastorage.Servicios.UserDBServices //Código Test
import com.example.datastorage.Servicios.MoviesDBServices
import com.example.datastorage.Servicios.FavoriteMovieDBServices
import com.example.datastorage.Servicios.CheckMovieServices
import com.google.gson.Gson

class MainActivity : AppCompatActivity()
{
    private lateinit var loginServices : LoginServices
    private lateinit var checkMovieServices : CheckMovieServices


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginServices= LoginServices(this)
        checkMovieServices= CheckMovieServices(this)
        val user = User(null, "Leo", "leo@gmail.com", 35, "secret", null)
        val movie = Movie(null, "Avengers: Endgame", "Awesome.", 181, 2019, 5.0f, "Anthony Russo, Joe Russo", null)
        val moviexuser = MoviexUser(null, 1,1)
        val moviexuser1 = MoviexUser(null, 2,2)
        MoviesDBServices(this).saveMovie(movie) //Código Test
        UserDBServices(this).saveUser(user) //Código Test
        FavoriteMovieDBServices(this).saveFavoriteMovie(moviexuser)
        FavoriteMovieDBServices(this).saveFavoriteMovie(moviexuser1)

    }

    fun login(view: View)
    {
        val email = findViewById<TextView>(R.id.email);
        val password = findViewById<TextView>(R.id.password);
        val user = User(null, null, email.text.toString(), null, password.text.toString(), null)

        if(this.loginServices.verifyUser(user))
        {
            val intent = Intent(this, MoviesListActivity::class.java)
            var jsonUser = Gson().toJson(loginServices.getUserByEmail(email.text.toString()))
            intent.putExtra("user", jsonUser)
            startActivity(intent)
            /* val intent = Intent(this, UsersListActivity::class.java)
             startActivity(intent)*/
        }
        else
        {
            Toast.makeText(this, "Datos incorrectos",  Toast.LENGTH_SHORT).show()
        }
    }

    fun register(view: View)
    {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}

