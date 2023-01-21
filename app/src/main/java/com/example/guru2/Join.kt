package com.example.guru2

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Join : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join)

        //clause (이용약관)약관확인 페이지로 이동
        val clause = findViewById<Button>(R.id.clause)

        clause.setOnClickListener {
            val intent = Intent(this, Clause::class.java)
            startActivity(intent)
        }
        //clause2 (개인정보처리)약관확인 페이지로 이동
        val clause2 = findViewById<Button>(R.id.clause2)

        clause2.setOnClickListener {
            val intent = Intent(this, Clause2::class.java)
            startActivity(intent)
        }
    }
}


