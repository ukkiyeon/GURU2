package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Trash : AppCompatActivity() {

    lateinit var btn_weather:Button
    lateinit var btn_walk:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trash)

        btn_weather = findViewById(R.id.btn_weather)
        btn_walk = findViewById(R.id.btn_walk)

        btn_weather.setOnClickListener {
            startActivity(Intent(this@Trash, AppMain::class.java))
        }

        btn_walk.setOnClickListener {
            startActivity(Intent(this@Trash, Walk::class.java))
        }
    }
}
