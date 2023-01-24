package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TipFloggingEvent : AppCompatActivity() {
    lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_flogging_event)

        backBtn = findViewById(R.id.back)

        backBtn.setOnClickListener {
            var intent = Intent(this, Tip::class.java)
            startActivity(intent)
        }
    }
}