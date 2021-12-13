package com.bittahlover.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click_audio_btn(view: View) {
        val intent = Intent(this, AudioPlayerActivity::class.java)
        startActivity(intent)
    }

    fun click_video_btn(view: View) {
        val intent = Intent(this, VideoActivity::class.java)
        startActivity(intent)
    }
}