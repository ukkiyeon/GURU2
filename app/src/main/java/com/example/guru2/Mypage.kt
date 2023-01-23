package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Mypage : AppCompatActivity() {

    lateinit var btn_info_modify:Button
    lateinit var back:Button

    //하단 버튼
    lateinit var btn_tipPage:Button
    lateinit var btn_homePage:Button
    lateinit var btn_communityPage:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)

        btn_info_modify = findViewById(R.id.btn_info_modify)
        back = findViewById(R.id.back)
        btn_tipPage = findViewById(R.id.btn_tipPage)
        btn_homePage = findViewById(R.id.btn_homePage)
        btn_communityPage = findViewById(R.id.btn_communityPage)

        btn_info_modify.setOnClickListener {
            startActivity(Intent(this@Mypage, UserModify::class.java))
        }

        back.setOnClickListener {
            startActivity(Intent(this@Mypage, AppMain::class.java))
        }

        btn_tipPage.setOnClickListener {
            startActivity(Intent(this@Mypage, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@Mypage, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@Mypage, AppMain::class.java))  //커뮤니티로 바꾸기
        }

    }
}