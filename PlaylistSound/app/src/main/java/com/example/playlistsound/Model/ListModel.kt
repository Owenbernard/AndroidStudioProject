package com.example.playlistsound.Model

import android.media.MediaPlayer

//model de tableau de music
data class ListModel(var image : Int,var nom : String, var duree : String, var song : Int)

//variable pour passer des variables entre fonctions
data class MusicDataTemp(val compteur: Int, val soundPlay: MediaPlayer)
