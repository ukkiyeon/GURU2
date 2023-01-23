package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Clause : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clause)

        //뒤로가기
        val back = findViewById<ImageButton>(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, Join::class.java)
            startActivity(intent)
        }
    }
}


