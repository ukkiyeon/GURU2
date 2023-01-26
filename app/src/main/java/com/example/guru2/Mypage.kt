package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class Mypage : AppCompatActivity() {

    lateinit var btn_info_modify:Button
    lateinit var back:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)

        btn_info_modify = findViewById(R.id.btn_info_modify)
        back = findViewById(R.id.back)

        btn_info_modify.setOnClickListener {
            startActivity(Intent(this@Mypage, UserModify::class.java))
        }

        back.setOnClickListener {
            startActivity(Intent(this@Mypage, AppMain::class.java))
        }

        //하단 버튼 동작
        val fix_bottom = findViewById<View>(R.id.fix_bottom)
        var btn_tipPage: ImageButton
        var btn_homePage: ImageButton
        var btn_communityPage: ImageButton

        btn_tipPage = fix_bottom.findViewById(R.id.btn_tipPage)
        btn_homePage = fix_bottom.findViewById(R.id.btn_homePage)
        btn_communityPage = fix_bottom.findViewById(R.id.btn_communityPage)

        btn_tipPage.setOnClickListener {
            Log.i("에러", "에러");
            startActivity(Intent(this@Mypage, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@Mypage, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@Mypage, CommunityMain::class.java))
        }

    }
}