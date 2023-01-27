package com.example.guru2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class TipZeroWaste : AppCompatActivity() {
    lateinit var backBtn: ImageButton

    // 링크 버튼
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_zero_waste)

        backBtn = findViewById(R.id.back)

        button1 = findViewById(R.id.button1) // 링크 버튼
        button2 = findViewById(R.id.button2) // 링크 버튼
        button3 = findViewById(R.id.button3) // 링크 버튼

        button1.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jigushop.co.kr/"))
            Toast.makeText(applicationContext, "지구샵 홈페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        button2.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://almang.net/"))
            Toast.makeText(applicationContext, "알맹상점 홈페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        button3.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://thepicker.net/"))
            Toast.makeText(applicationContext, "더피커 홈페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }



        backBtn.setOnClickListener {
            var intent = Intent(this, Tip::class.java)
            startActivity(intent)
        }
    }
}