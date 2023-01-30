package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Fix_bottom : AppCompatActivity() {

    //하단 버튼
    lateinit var btn_tipPage: ImageButton
    lateinit var btn_homePage: ImageButton
    lateinit var btn_communityPage: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fix_bottom)

        btn_tipPage = findViewById(R.id.btn_tipPage)
        btn_homePage = findViewById(R.id.btn_homePage)
        btn_communityPage = findViewById(R.id.btn_communityPage)

        btn_tipPage.setOnClickListener {
            Log.i("에러", "에러");
            startActivity(Intent(this@Fix_bottom, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@Fix_bottom, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@Fix_bottom, PostCommunity::class.java))
        }
    }
}
