package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Tip : AppCompatActivity() {
    // 이미지 버튼
    lateinit var appguideBtn: ImageButton
    lateinit var ecoguideBtn: ImageButton
    lateinit var floggingeventBtn: ImageButton
    lateinit var floggingBtn: ImageButton
    lateinit var econewsBtn: ImageButton
    lateinit var zerowasteBtn: ImageButton

    // 하단 버튼
    lateinit var MainBtn: ImageButton
    lateinit var CommunityBtn: ImageButton

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

        MainBtn = findViewById(R.id.btn_homePage2)
        CommunityBtn = findViewById(R.id.btn_communityPage2)

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

        MainBtn.setOnClickListener {
            var intent = Intent(this, AppMain::class.java)
            startActivity(intent)
        }

        CommunityBtn.setOnClickListener {
            var intent = Intent(this, CommunityMain::class.java)
            startActivity(intent)
        }


    }
}