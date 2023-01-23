package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Walk : AppCompatActivity() {

    lateinit var btn_weather: Button
    lateinit var btn_trash: Button

    //하단 버튼
    lateinit var btn_tipPage:Button
    lateinit var btn_homePage:Button
    lateinit var btn_communityPage:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walk)

        btn_weather = findViewById(R.id.btn_weather)
        btn_trash = findViewById(R.id.btn_trash)

        btn_tipPage = findViewById(R.id.btn_tipPage)
        btn_homePage = findViewById(R.id.btn_homePage)
        btn_communityPage = findViewById(R.id.btn_communityPage)

        btn_weather.setOnClickListener {
            startActivity(Intent(this@Walk, AppMain::class.java))
        }

        btn_trash.setOnClickListener {
            startActivity(Intent(this@Walk, Trash::class.java))
        }

        btn_tipPage.setOnClickListener {
            startActivity(Intent(this@Walk, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@Walk, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@Walk, AppMain::class.java))  //커뮤니티로 바꾸기
        }
    }
}
