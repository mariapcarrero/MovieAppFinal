package com.example.datastorage.Servicios

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageView
import com.example.datastorage.Controladores.MovieRegisterActivity
import java.io.ByteArrayOutputStream
import com.example.datastorage.R

class  GalleryServiceForMovieRegister : ITemporarilyActivity<MovieRegisterActivity> {

    companion object {
        const val SELECT_ACTION_MESSAGE = "Seleccione una accion."
        const val SELECT_FROM_GALLERY_MESSAGE = "Seleccionar imagen de la galeria."
        const val CHOSE_FROM_GALLERY_ID = 0
        const val GALLERY = 1889
    }

    override fun open(source: MovieRegisterActivity) {
        val dialog = AlertDialog.Builder(source)
        dialog.setTitle(SELECT_ACTION_MESSAGE)
        dialog.setItems(arrayOf(SELECT_FROM_GALLERY_MESSAGE), DialogInterface.OnClickListener { dialog, which ->
            if (which == CHOSE_FROM_GALLERY_ID) {
                chosePhotoFromGallery(source)
            }
        })
        dialog.show()
    }

    fun chosePhotoFromGallery(source: MovieRegisterActivity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        source.startActivityForResult(intent, GALLERY)
    }

    override fun onActivityCanceled(source: MovieRegisterActivity, requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun onActivityAccepted(source: MovieRegisterActivity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GALLERY) {
            if (data != null) {
                val uri = data.data
                var bitmap = MediaStore.Images.Media.getBitmap(source.contentResolver, uri)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

                source.setImageArray(stream.toByteArray())
                source.findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
            }
        }
    }

}


