package com.example.guru2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient.CustomViewCallback
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PostCommunity : AppCompatActivity() {

    lateinit var write_btn : Button
    lateinit var maincontent : FrameLayout
    var firestore : FirebaseFirestore? = null // DB 접근을 위함

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_community)

        write_btn = findViewById(R.id.write_btn)

        // 글쓰기 버튼 클릭
        write_btn.setOnClickListener {

            // 갤러리 권한 받기
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

            // 권한 확인
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this, PostPhoto::class.java))
            }

            // 메인 화면이 뜨면 detailview fragment 뜨도록?
        }

        var postDetailViewFragment = PostDetailViewFragment() // 프래그먼트 받아오기

        // 프래그먼트매니저로 트랜잭션 실행 (프래그먼트를 올리거나 교체)
        supportFragmentManager.beginTransaction().replace(R.id.main_content, postDetailViewFragment).commit() // 프래그먼트를 FrameLayout의 자식으로 등록하고 commit하면 프래그먼트가 화면에 보임

        //하단 버튼 동작
        val fix_bottom = findViewById<View>(R.id.fix_bottom)
        var btn_tipPage: ImageButton
        var btn_homePage: ImageButton

        btn_tipPage = fix_bottom.findViewById(R.id.btn_tipPage)
        btn_homePage = fix_bottom.findViewById(R.id.btn_homePage)

        btn_tipPage.setOnClickListener {
            startActivity(Intent(this, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this, AppMain::class.java))
        }
    }


}