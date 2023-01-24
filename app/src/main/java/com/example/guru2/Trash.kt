package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Trash : AppCompatActivity() {

    lateinit var btn_weather:Button
    lateinit var btn_walk:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trash)

        btn_weather = findViewById(R.id.btn_weather)
        btn_walk = findViewById(R.id.btn_walk)

        btn_weather.setOnClickListener {
            startActivity(Intent(this@Trash, AppMain::class.java))
        }

        btn_walk.setOnClickListener {
            startActivity(Intent(this@Trash, Walk::class.java))
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
            startActivity(Intent(this@Trash, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@Trash, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@Trash, CommunityMain::class.java))
        }

    }
}
