package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tip : AppCompatActivity() {
    lateinit var appguideBtn: Button
    lateinit var ecoguideBtn: Button
    lateinit var floggingeventBtn: Button
    lateinit var floggingBtn: Button
    lateinit var econewsBtn: Button
    lateinit var zerowasteBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip)

        // 버튼 연결
        appguideBtn = findViewById(R.id.imageButton)
        ecoguideBtn = findViewById(R.id.imageButton2)
        floggingeventBtn = findViewById(R.id.imageButton3)
        floggingBtn = findViewById(R.id.imageButton4)
        econewsBtn = findViewById(R.id.imageButton5)
        zerowasteBtn = findViewById(R.id.imageButton6)

        // 버튼 클릭 시 화면 이동
        appguideBtn.setOnClickListener {
            var intent = Intent(this, TipAppGuide::class.java)
            startActivity(intent)
        }

        ecoguideBtn.setOnClickListener {
            var intent = Intent(this, TipEcoGuide::class.java)
            startActivity(intent)
        }

        floggingeventBtn.setOnClickListener {
            var intent = Intent(this, TipFloggingEvent::class.java)
            startActivity(intent)
        }

        floggingBtn.setOnClickListener {
            var intent = Intent(this, TipFlogging::class.java)
            startActivity(intent)
        }

        econewsBtn.setOnClickListener {
            var intent = Intent(this, TipEcoNews::class.java)
            startActivity(intent)
        }

        zerowasteBtn.setOnClickListener {
            var intent = Intent(this, TipZeroWaste::class.java)
            startActivity(intent)
        }


    }
}