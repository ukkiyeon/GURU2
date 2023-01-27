package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class Community : AppCompatActivity() {

    // 이미지 버튼
    lateinit var writingBtn: Button
    lateinit var viewBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community)

        // 버튼 연결
        writingBtn = findViewById(R.id.write_btn)
        viewBtn = findViewById(R.id.commu02_title)


        // 버튼 클릭 시 화면 이동
        writingBtn.setOnClickListener {
            startActivity(Intent(this@Community, CommunityWriting::class.java))
        }

        viewBtn.setOnClickListener {
            startActivity(Intent(this@Community, CommunityView::class.java))
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
            startActivity(Intent(this, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this, Community::class.java))
        }
    }
}