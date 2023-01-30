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
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PostCommunity : AppCompatActivity() {

    lateinit var button : Button
    lateinit var maincontent : FrameLayout
    var firestore : FirebaseFirestore? = null // DB 접근을 위함
    var postDetailViewFragment = PostDetailViewFragment() // 프래그먼트 받아오기

    val fragmentManager = supportFragmentManager
    // fragmentManager.beginTransaction().replace(R.id.main_content, PostDetailViewFragment).commit()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_community)

        button = findViewById(R.id.addbutton) // 어디에 넣어야 하쥐 ................????????????

        //버튼 클릭 시 화면 이동 => 어디에 넣어야 하지 ...
        button.setOnClickListener {

            // 갤러리 권한 받기
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

            // 권한 확인
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this, PostPhoto::class.java))
            }

            // 메인 화면이 뜨면 detailview fragment 뜨도록?

        }


    }


}