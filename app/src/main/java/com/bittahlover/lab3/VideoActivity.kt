package com.bittahlover.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.ArrayList

class VideoActivity : AppCompatActivity() {
    lateinit var current_movie : String
    var list_movie = mutableListOf<String>("cowboy", "spider", "pavuk")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)


        val movieList = findViewById<ListView>(R.id.list_view_movie)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, list_movie )
        movieList.adapter = adapter


        movieList.setOnItemClickListener(AdapterView.OnItemClickListener { parent, v, position, id ->
            val selectedItem: String = list_movie.get(position)
            println("position in list_item $position")

            current_movie = selectedItem
            val intent: Intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("current_movie", current_movie)
            intent.putStringArrayListExtra("list_movie", list_movie as ArrayList<String>)
            startActivity(intent)
        })
    }
}