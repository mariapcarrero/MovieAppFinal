package com.example.datastorage.Adapters

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.datastorage.R
import com.example.datastorage.Modelos.MoviexUser

class FavoriteMoviesListAdapter(private val activity: Activity, moviesList: List<MoviexUser>?) : BaseAdapter(){
    private var moviesList = ArrayList<MoviexUser>()

    init {
        this.moviesList = moviesList as ArrayList<MoviexUser>
    }

    override fun getCount(): Int {
        return moviesList.size
    }

    override fun getItem(i: Int): Any {
        return moviesList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    fun getName(i: Int): Int? {
        return moviesList[i].idMovie
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.row_item, null)
        val nombre = vi.findViewById<TextView>(R.id.Nombre)
        val sinopsis = vi.findViewById<TextView>(R.id.Edad)


        /*val img = moviesList[i].image
        if (img != null) {
            vi.findViewById<ImageView>(R.id.userImage).setImageBitmap(
                BitmapFactory.decodeByteArray(img, 0, img.size)
            )
        }*/

        nombre.text = moviesList[i].idMovie.toString()
        sinopsis.text = moviesList[i].idUser.toString()
        return vi
    }
}