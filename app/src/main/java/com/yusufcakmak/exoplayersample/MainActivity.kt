package com.yusufcakmak.exoplayersample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val radioPlayerButton: Button by lazy { findViewById<Button>(R.id.button_radio_player) }
    private val videoPlayerButton: Button by lazy { findViewById<Button>(R.id.button_video_player) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioPlayerButton.setOnClickListener {

            val intent = Intent(this@MainActivity, RadioPlayerActivity::class.java)
            startActivity(intent)
        }

        videoPlayerButton.setOnClickListener {

            val intent = Intent(this@MainActivity, VideoPlayerActivity::class.java)
            startActivity(intent)

        }

    }

}
