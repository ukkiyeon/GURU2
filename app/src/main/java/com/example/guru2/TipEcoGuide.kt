package com.example.guru2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class TipEcoGuide : AppCompatActivity() {
    lateinit var backBtn: ImageButton
    lateinit var recycleBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_eco_guide)

        //버튼 연결
        recycleBtn = findViewById(R.id.recycle_btn)
        backBtn = findViewById(R.id.back)

        //버튼 클릭시 화면 이동
        recycleBtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://keep.go.kr/portal/141?action=read&action-value=a7d2cf8d4f8797c7049acbbc27f4c54e"))
            startActivity(intent)
        }
        backBtn.setOnClickListener {
            var intent = Intent(this, Tip::class.java)
            startActivity(intent)
        }
    }
}