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
            startActivity(Intent(this@Tip, TipAppGuide::class.java))
        }

        ecoguideBtn.setOnClickListener {
            startActivity(Intent(this@Tip, TipEcoGuide::class.java))
        }

        floggingeventBtn.setOnClickListener {
            startActivity(Intent(this@Tip, TipFloggingEvent::class.java))
        }

        floggingBtn.setOnClickListener {
            startActivity(Intent(this@Tip, TipFlogging::class.java))
        }

        econewsBtn.setOnClickListener {
            startActivity(Intent(this@Tip, TipEcoNews::class.java))
        }

        zerowasteBtn.setOnClickListener {
            startActivity(Intent(this@Tip, TipZeroWaste::class.java))
        }

        MainBtn.setOnClickListener {
            startActivity(Intent(this@Tip, AppMain::class.java))
        }

        CommunityBtn.setOnClickListener {
            startActivity(Intent(this@Tip, CommunityMain::class.java))
        }


    }
}