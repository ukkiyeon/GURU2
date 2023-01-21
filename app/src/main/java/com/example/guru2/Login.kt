package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //find_pwd 비밀번호 찾기 페이지로 이동
        val find_pwd = findViewById<Button>(R.id.find_pwd)

        find_pwd.setOnClickListener {
            val intent = Intent(this, Find_pwd::class.java)
            startActivity(intent)
        }

        //join 회원가입 페이지로 이동
        val join = findViewById<Button>(R.id.join)

        find_pwd.setOnClickListener {
            val intent = Intent(this, Join::class.java)
            startActivity(intent)
        }
    }
}