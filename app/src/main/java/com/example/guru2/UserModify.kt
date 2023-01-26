package com.example.guru2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class UserModify : AppCompatActivity() {

    lateinit var btn_modify: AppCompatButton
    lateinit var back:ImageButton
    lateinit var modify_pwd: EditText
    lateinit var modify_pwd_2:EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_modify)

        btn_modify = findViewById(R.id.btn_modify)
        back = findViewById(R.id.back)

        btn_modify.setOnClickListener {
            pwd_check()
        }

        back.setOnClickListener {
            startActivity(Intent(this@UserModify, Mypage::class.java))
        }

        //비밀번호 확인
        modify_pwd = findViewById(R.id.modify_pwd)
        modify_pwd_2 = findViewById(R.id.modify_pwd_2)
    }

    //비밀번호 체크
    fun pwd_check() {
        var pwd_1 = modify_pwd.text.toString()
        var pwd_2 = modify_pwd_2.text.toString()

        //비밀번호 null값 확인ㄷ
        if(pwd_1.equals("") || pwd_1 == null || pwd_2.equals("") || pwd_2 == null) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
        } else if (pwd_1 != pwd_2) { //일치하는지 확인
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "비밀번호가 수정되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@UserModify, Mypage::class.java))
        }
    }
}