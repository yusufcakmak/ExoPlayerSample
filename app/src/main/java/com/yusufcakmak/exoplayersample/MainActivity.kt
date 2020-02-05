package com.yusufcakmak.exoplayersample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRadioPlayer.setOnClickListener {
            val intent = Intent(this@MainActivity, RadioPlayerActivity::class.java)
            startActivity(intent)
        }

        btnVideoPlayer.setOnClickListener {
            val intent = Intent(this@MainActivity, VideoPlayerActivity::class.java)
            startActivity(intent)
        }

        btnVideoPlayer2.setOnClickListener {
            val intent = Intent(this@MainActivity, VideoPlayerActivity2::class.java)
            startActivity(intent)
        }
    }

}

