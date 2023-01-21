package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Mypage : AppCompatActivity() {

    lateinit var btn_info_modify:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        btn_info_modify = findViewById(R.id.btn_info_modify)

        btn_info_modify.setOnClickListener {
            startActivity(Intent(this@Mypage, UserModify::class.java))
        }
    }
}