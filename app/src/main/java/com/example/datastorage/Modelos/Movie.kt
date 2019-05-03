package com.example.datastorage.Modelos

data class Movie(
    val idMovie: Int?,
    var name: String?,
    val synopsis: String?,
    val duration: Int?,
    var year: Int?,
    var score: Float?,
    var director: String?,
    var image: ByteArray?
)