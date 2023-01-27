package com.example.guru2

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Mypage : AppCompatActivity() {

    lateinit var btn_delete: AppCompatButton
    lateinit var btn_logout : AppCompatButton
    lateinit var back:ImageButton
    lateinit var auth: FirebaseAuth // 객체의 공유 인스턴스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)

        auth = Firebase.auth

        btn_delete = findViewById(R.id.btn_delete)
        btn_logout = findViewById(R.id.btn_logout)
        back = findViewById(R.id.back)


        //이름 불러오기
        val mypage_name = findViewById<TextView>(R.id.mypage_name)
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            mypage_name.text = name
        }


        //회원 탈퇴
        btn_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            //val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
            builder.setTitle("회원 탈퇴 확인창")
            builder.setMessage("정말 회원탈퇴 하시겠습니까?")

            builder.setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                auth?.currentUser?.delete()
                Toast.makeText(this, "회원 탈퇴 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            })
            builder.setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "회원탈퇴 취소", Toast.LENGTH_SHORT).show()
            })

            builder.create()
            builder.show()
        }

        //로그아웃
        btn_logout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        //뒤로가기
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