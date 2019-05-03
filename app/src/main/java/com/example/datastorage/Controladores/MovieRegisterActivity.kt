package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.R
import com.example.datastorage.Servicios.CheckMovieServices
import com.example.datastorage.Modelos.Movie
import android.Manifest
import com.example.datastorage.Servicios.GalleryServiceForMovieRegister
import com.example.datastorage.Servicios.PermissionBuilder
import com.example.datastorage.Servicios.PermissionService
import android.widget.Button
import android.widget.RatingBar


class MovieRegisterActivity : AppCompatActivity() {

    companion object {
        const val USER_ALREADY_EXISTS = "La pelicula ya existe en el sistema."
        const val USER_REGISTERED_SUCCESSFULLY = "Se creo exitosamente la pelicula."
    }

    private lateinit var checkMovieServices: CheckMovieServices
    private lateinit var permissionServices: PermissionService
    private lateinit var galleryServiceForMovieRegister: GalleryServiceForMovieRegister
    private lateinit var userData : String
    private var imgArr : ByteArray = ByteArray(0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_register)
        checkMovieServices= CheckMovieServices(this)
        userData = this.intent.getStringExtra("user")
        permissionServices = PermissionService(this, PermissionBuilder().addPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
        permissionServices.requestPermissions(this)

        galleryServiceForMovieRegister = GalleryServiceForMovieRegister()
    }

    fun uploadPhoto(view : View?) {
        galleryServiceForMovieRegister.open(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_CANCELED) {
            galleryServiceForMovieRegister.onActivityCanceled(this, requestCode, resultCode, data)
            return
        }
        galleryServiceForMovieRegister.onActivityAccepted(this, requestCode, resultCode, data)
    }

    fun createProfile(view : View) {
        val name = findViewById<TextView>(R.id.nombre).text.toString()
        val synopsis = findViewById<TextView>(R.id.sinopsis).text.toString()
        val director = findViewById<TextView>(R.id.director).text.toString()
        val year = findViewById<TextView>(R.id.year).text.toString().toInt()
        val score = findViewById<RatingBar>(R.id.ratingBar).rating.toString().toFloat()
        val duration = findViewById<TextView>(R.id.duracion).text.toString().toInt()
        val movie = Movie(null, name, synopsis, duration, year, score, director, imgArr)
        val exists = this.checkMovieServices.existsMovie(movie)

        if (exists) {
            Toast.makeText(this, USER_ALREADY_EXISTS,  Toast.LENGTH_SHORT).show()
        } else {
            checkMovieServices.saveMovie(movie, this)
            Toast.makeText(this, USER_REGISTERED_SUCCESSFULLY,  Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MoviesListActivity::class.java)
            intent.putExtra("user", userData)
            startActivity(intent)
        }
    }

    fun setImageArray(array : ByteArray) {
        imgArr = array
    }

    fun goBack(view : View) {
        val intent = Intent(this, MoviesListActivity::class.java)
        intent.putExtra("user", userData)
        startActivity(intent)
    }

}
