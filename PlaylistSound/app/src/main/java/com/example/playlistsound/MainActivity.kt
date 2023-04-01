package com.example.playlistsound

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistsound.Adapter.ListHolder
import com.example.playlistsound.Model.ListModel
import com.example.playlistsound.Model.MusicDataTemp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var newArrayList : ArrayList<ListModel>
    private lateinit var imageId : Array<Int>
    private lateinit var NomId : Array<String>
    private lateinit var DureeId : Array<String>
    private lateinit var Song : Array<Int>


    // variable globale pour le choix de musique
    private var compteur: Int = 0
    // variable globale pour l'affichage
    private var btncompteur: Int = 0
    private var btncompteurTri: Int = 0
    private var soundPlay = MediaPlayer()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //initialisation des tableaux
        imageId = arrayOf(R.drawable.telec,
            R.drawable.telech,R.drawable.telec,
            R.drawable.telec,R.drawable.telech,
            R.drawable.telec,R.drawable.telech,
            R.drawable.telech,R.drawable.telec,
            )
        NomId = arrayOf(
            "Monster",
            "Natural",
            "Unbecoming",
            "Let it die",
            "Bones",
            "Frequency",
            "Warrior",
            "Sharks",
            "Demons",
        )
        DureeId = arrayOf("04:16","03:09","04:19","04:34","02:45","04:52","02:50","03:15","03:43")
        Song = arrayOf(R.raw.starset_monster,
            R.raw.id_natural,R.raw.starset_unbecoming,
            R.raw.starset_letitdie,R.raw.id_bones,
            R.raw.starset_frequency,R.raw.id_warrior,
            R.raw.id_sharks,R.raw.starset_demon
        )

        //mise en place de la liste de music
        newRecyclerview = findViewById(R.id.recyclerView)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)
        newArrayList = arrayListOf()
        getUserdata()

        //listener de btn
        val btnStop = findViewById<FloatingActionButton>(R.id.floatingActionButtonStop)
        val btnPlay = findViewById<FloatingActionButton>(R.id.floatingActionButton3)
        val btnPrevious = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        val btnNext = findViewById<FloatingActionButton>(R.id.floatingActionButton4)
        val btnSuffle = findViewById<FloatingActionButton>(R.id.floatingActionButtonPlay)
        val btnTri = findViewById<Button>(R.id.btnTri)
        //image
        val imageMsc = findViewById<ImageView>(R.id.imageView)
        imageMsc.setImageResource(newArrayList[compteur].image)
        //son
        soundPlay = MediaPlayer.create(this,R.raw.starset_demon)

        //texte
        val txtTitre = findViewById<TextView>(R.id.txtTitre)
        txtTitre.text = newArrayList[compteur].nom
        val txtActif = findViewById<TextView>(R.id.txtIsplaying)
        txtActif.text = ""

        btnSuffle.setOnClickListener {
            btncompteur++
            if (btncompteur>2){btncompteur=0}

            when (btncompteur) {
                0 ->
                    btnSuffle.setImageResource(R.drawable.baseline_navigate_next_24)
                1 ->
                    btnSuffle.setImageResource(R.drawable.baseline_repeat_24)
                2 ->
                    btnSuffle.setImageResource(R.drawable.baseline_shuffle_24)
            }
        }

        btnStop.setOnClickListener {
            if (soundPlay.isPlaying) {
                soundPlay.stop()
            }

            txtActif.text = ""
        }
        btnPlay.setOnClickListener {
            if (soundPlay.isPlaying) {
                soundPlay.stop()
            }
            txtTitre.text = newArrayList[compteur].nom
            imageMsc.setImageResource(newArrayList[compteur].image)
            txtActif.text = newArrayList[compteur].nom
            soundPlay.start()

/*
            //décompte jusqu'a la fin du son
            val musicData = OnEndMusic(soundPlay, btncompteur, newArrayList, compteur, txtActif, txtTitre, imageMsc)
            compteur = musicData.compteur
            soundPlay = musicData.soundPlay
*/

        }

        btnNext.setOnClickListener {
            compteur++
            if (compteur > newArrayList.size-1){
                compteur = 0
            }

            txtTitre.text = newArrayList[compteur].nom
            imageMsc.setImageResource(newArrayList[compteur].image)
        }

        btnPrevious.setOnClickListener {
            compteur--
            if (compteur < 0){
                compteur = newArrayList.size-1
            }

            txtTitre.text = newArrayList[compteur].nom
            imageMsc.setImageResource(newArrayList[compteur].image)
        }

        btnTri.setOnClickListener {
            btncompteurTri++
            if (btncompteurTri > 2) {
                btncompteurTri = 0
            }
            when (btncompteurTri) {
                0 -> {
                    newArrayList.sortBy { it.duree }
                    newRecyclerview.adapter = ListHolder(newArrayList)
                }
                1 -> {
                    newArrayList.sortBy { it.nom }
                    newRecyclerview.adapter = ListHolder(newArrayList)
                    }
                2 -> {
                    newArrayList.sortBy { it.image }
                    newRecyclerview.adapter = ListHolder(newArrayList)
                }
            }
        }

    }

    private fun getUserdata() {
//insertion des valeurs dans le tableau de playlist
        for (i in imageId.indices) {
            val new = ListModel(imageId[i],NomId[i],DureeId[i],Song[i])
            newArrayList.add(new)
        }
        newRecyclerview.adapter = ListHolder(newArrayList)
    }

    private fun OnEndMusic(
        soundPlay: MediaPlayer,
        btncompteur: Int,
        newArrayList: ArrayList<ListModel>,
        compteur: Int,
        txtActif: TextView,
        txtTitre: TextView,
        imageMsc: ImageView,
    ): MusicDataTemp {

        var cmpt = compteur
        var sd = soundPlay

        if (!soundPlay.isPlaying) {return MusicDataTemp(cmpt,sd) }
        val handler = Handler()
        handler.postDelayed({
            soundPlay.reset()
            when (btncompteur) {
                0 ->
                {
                    //jouer le prochain son
                    cmpt++
                    if (cmpt > newArrayList.size - 1) {
                        cmpt = 0
                    }
                    txtTitre.text = newArrayList[cmpt].nom
                    imageMsc.setImageResource(newArrayList[cmpt].image)
                    txtActif.text = newArrayList[cmpt].nom
                    sd = MediaPlayer.create(this,newArrayList[cmpt].song)
                    sd.start()
                }
                2 ->
                {
                    //jouer un son aléatoire
                    cmpt = Random.nextInt(newArrayList.size - 1)
                    txtTitre.text = newArrayList[cmpt].nom
                    imageMsc.setImageResource(newArrayList[cmpt].image)
                    txtActif.text = newArrayList[cmpt].nom
                    sd = MediaPlayer.create(this,newArrayList[cmpt].song)
                    sd.start()
                }
            }
            sd.start()
        }, sd.duration.toLong())

        return MusicDataTemp(cmpt,sd)
    }

}

