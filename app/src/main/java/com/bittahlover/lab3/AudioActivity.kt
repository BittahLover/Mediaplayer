package com.bittahlover.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import java.util.ArrayList



class AudioActivity : AppCompatActivity() {
    var audio_list = mutableListOf<String> ("music", "music1", "music2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)

        val audioList = findViewById<ListView>(R.id.list_view_audio)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, audio_list )
        audioList.adapter = adapter



        audioList.setOnItemClickListener(OnItemClickListener { parent, v, position, id ->
            val selectedItem: String = audio_list.get(position)
            println("position in list_item $position")

            val intent: Intent = Intent(this, AudioPlayerActivity::class.java)
            intent.putExtra("selected_song", selectedItem)
            intent.putStringArrayListExtra("all_song", audio_list as ArrayList<String>)
            startActivity(intent)
        })
    }
}