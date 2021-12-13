package com.bittahlover.lab3

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.SeekBar

import android.widget.TextView




class AudioPlayerActivity : AppCompatActivity() {
    var current_song = "music1"
    var list_song = mutableListOf<String> ("music", "music1", "music2")
    lateinit var id_array : IntArray

    private lateinit var  mp: MediaPlayer
    private var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)


        new_song(current_song)

    }

    fun new_song (current_song : String) {
        if(current_song == "music") {
            mp = MediaPlayer.create(this, R.raw.music)
        }
        else if(current_song == "music1") {
            mp = MediaPlayer.create(this, R.raw.music1)
        }
        else if(current_song == "music2") {
            mp = MediaPlayer.create(this, R.raw.music2)
        }

        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        var volumeBar = findViewById<SeekBar>(R.id.volumeBar)
        volumeBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        var volumeNum = progress / 100.0f
                        mp.setVolume(volumeNum, volumeNum)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )

        positionBarFun()
    }

    fun positionBarFun () {
        //Position Bar
        var positionBar = findViewById<SeekBar>(R.id.positionBar)
        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )
        @SuppressLint("HandlerLeak")
        var handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                var currentPosition = msg.what

                //Update positionBar
                positionBar.progress = currentPosition

                //Update Labels
                var elapsedTime = createTimeLabel(currentPosition)
                var elapsedTimeLabel = findViewById<TextView>(R.id.elapsedTimeLabel)
                elapsedTimeLabel.text = elapsedTime

                var remainingTime = createTimeLabel(totalTime - currentPosition)
                var remainingTimeLabel = findViewById<TextView>(R.id.remainingTimeLabel)
                remainingTimeLabel.text = "-$remainingTime"
            }
        }

        //Thread
        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    fun prevBtnClick(view: View) {
        playBtnClick(view)
        var current_index = list_song.indexOf(current_song)
        when (current_index) {
            0 -> {
                current_song = list_song[2]
            }
            1 -> {
                current_song = list_song[0]
            }
            2 -> {
                current_song = list_song[1]
            }
        }
        new_song(current_song)
    }
    fun leftBtnClick(view: View) {
        mp.seekTo(mp.currentPosition - 5000)
        positionBarFun()
    }
    fun playBtnClick(view: View) {
        if (mp.isPlaying) {
            //Stop
            mp.pause()
            var playBtn = findViewById<Button>(R.id.playBtn)
            playBtn.setBackgroundResource(R.drawable.play)
        } else {
            //Start
            mp.start()
            var playBtn = findViewById<Button>(R.id.playBtn)
            playBtn.setBackgroundResource(R.drawable.stop)
        }

    }
    fun rightBtnClick(view: View) {
        mp.seekTo(mp.currentPosition + 5000)
        positionBarFun()
    }


    fun nextBtnClick(view: View) {
        playBtnClick(view)
        var current_index = list_song.indexOf(current_song)
        when (current_index) {
            0 -> {
                current_song = list_song[1]
            }
            1 -> {
                current_song = list_song[2]
            }
            2 -> {
                current_song = list_song[0]
            }
        }
        new_song(current_song)
    }
}