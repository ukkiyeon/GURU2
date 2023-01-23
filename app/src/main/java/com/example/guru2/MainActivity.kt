package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView

class MainActivity : AppCompatActivity() {

//    lateinit var containers : FrameLayout
//    lateinit var bottom_navigationview : BottomNavigationMenuView

    //타이머
    lateinit var flogging_start: Button
    lateinit var floggint_time: TextView
    lateinit var btn_share: ImageView

    private var isRunning = false
    private var sec = 0L
    private var milli = 0L

    //버튼 3개
    lateinit var btn_walk:Button
    lateinit var btn_trash:Button

    //하단 버튼
    lateinit var btn_tipPage:ImageButton
    lateinit var btn_homePage:ImageButton
    lateinit var btn_communityPage:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flogging_start = findViewById(R.id.flogging_start)
        floggint_time = findViewById(R.id.flogging_time)
        btn_share = findViewById(R.id.btn_share)
        btn_tipPage = findViewById(R.id.btn_tipPage)
        btn_homePage = findViewById(R.id.btn_homePage)
        btn_communityPage = findViewById(R.id.btn_communityPage)

//        containers = findViewById(R.id.containers)
//        bottom_navigationview = findViewById(R.id.bottom_navigationview)
//
//        supportFragmentManager.beginTransaction().add(containers.id, MainFragment()).commit()

        flogging_start.setOnClickListener {
            flogging_start.setText("종료")
            floggint_timer()
        }

        btn_walk = findViewById(R.id.btn_walk)
        btn_trash = findViewById(R.id.btn_trash)

        btn_walk.setOnClickListener {
            startActivity(Intent(this@MainActivity, Walk::class.java))
        }

        btn_trash.setOnClickListener {
            startActivity(Intent(this@MainActivity, Trash::class.java))
        }

        btn_tipPage.setOnClickListener {
            startActivity(Intent(this@MainActivity, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@MainActivity, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@MainActivity, AppMain::class.java))  //커뮤니티로 바꾸기
        }
    }

    //타이머
    fun floggint_timer() {

        flogging_start.setOnClickListener {
            flogging_start.visibility = View.INVISIBLE
            btn_share.visibility = View.VISIBLE
        }
    }
}