package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Mypage : AppCompatActivity() {

    lateinit var btn_info_modify: AppCompatButton
    lateinit var btn_logout : AppCompatButton
    lateinit var back:ImageButton
    private var googleSignInClient: GoogleSignInClient?=null
    lateinit var auth: FirebaseAuth // 객체의 공유 인스턴스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)

        auth = Firebase.auth

        btn_info_modify = findViewById(R.id.btn_info_modify)
        btn_logout = findViewById(R.id.btn_logout)
        back = findViewById(R.id.back)

        btn_info_modify.setOnClickListener {
            startActivity(Intent(this@Mypage, UserModify::class.java))
        }

        btn_logout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
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
            startActivity(Intent(this@Mypage, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@Mypage, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@Mypage, Community::class.java))
        }

    }

}