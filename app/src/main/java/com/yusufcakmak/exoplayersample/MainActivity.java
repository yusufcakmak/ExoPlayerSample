package com.yusufcakmak.exoplayersample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yusufcakmak.exoplayersample.customview.CustomVideoPlayerActivity;

public class MainActivity extends AppCompatActivity {

    Button radioButton;
    Button customVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioButton = (Button) findViewById(R.id.radioButton);
        customVideoButton = (Button) findViewById(R.id.customVideoButton);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RadioPlayerActivity.class);
                startActivity(intent);
            }
        });

        customVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomVideoPlayerActivity.class);
                startActivity(intent);
            }
        });

    }
}
