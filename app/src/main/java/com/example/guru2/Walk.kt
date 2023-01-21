package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Walk : AppCompatActivity() {

    lateinit var btn_weather: Button
    lateinit var btn_trash: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walk)

        btn_weather = findViewById(R.id.btn_weather)
        btn_trash = findViewById(R.id.btn_trash)

        btn_weather.setOnClickListener {
            startActivity(Intent(this@Walk, AppMain::class.java))
        }

        btn_trash.setOnClickListener {
            startActivity(Intent(this@Walk, Trash::class.java))
        }
    }
}
