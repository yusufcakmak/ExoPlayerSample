package com.yusufcakmak.exoplayersample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yusufcakmak.exoplayersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRadioPlayer.setOnClickListener {
            val intent = Intent(this@MainActivity, RadioPlayerActivity::class.java)
            startActivity(intent)
        }

        binding.btnVideoPlayer.setOnClickListener {
            val intent = Intent(this@MainActivity, VideoPlayerActivity::class.java)
            startActivity(intent)
        }
    }

}

