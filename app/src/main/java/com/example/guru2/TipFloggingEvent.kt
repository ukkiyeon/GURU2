package com.example.guru2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class TipFloggingEvent : AppCompatActivity() {
    lateinit var backBtn: ImageButton
    lateinit var eventBtn01: Button
    lateinit var eventBtn02: Button
    lateinit var eventBtn03: Button
    lateinit var eventBtn04: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_flogging_event)

        //버튼 연결
        eventBtn01 = findViewById(R.id.tip_event01_btn)
        eventBtn02 = findViewById(R.id.tip_event02_btn)
        eventBtn03 = findViewById(R.id.tip_event03_btn)
        eventBtn04 = findViewById(R.id.tip_event04_btn)
        backBtn = findViewById(R.id.back)

        //버튼 클릭시 화면 이동
        eventBtn01.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://earthact.org:50002/community/bbs/board.php?bo_table=edu_schedule&device=pc"))
            startActivity(intent)
        }

        eventBtn02.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://mcrace.co.kr/index.php"))
            startActivity(intent)
        }

        eventBtn03.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://ggimarathon.com/"))
            startActivity(intent)
        }

        eventBtn04.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ulsanmaeilmara.com/html/00_main/main.php"))
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            var intent = Intent(this, Tip::class.java)
            startActivity(intent)
        }
    }
}