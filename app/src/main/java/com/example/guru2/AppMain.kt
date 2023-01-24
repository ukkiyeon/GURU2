package com.example.guru2

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import java.util.*
import kotlin.concurrent.timer

class AppMain : AppCompatActivity() {

    //타이머
    lateinit var flogging_start:Button
    lateinit var flogging_time:TextView
    lateinit var btn_share:ImageView
    lateinit var flogging_distance:TextView
    private var time = 0
    private var timerTask : Timer?=null

    //버튼 3개
    lateinit var btn_walk:Button
    lateinit var btn_trash:Button
    lateinit var btn_weather:Button

    //팝업
    lateinit var popup_time :TextView

    lateinit var main_weather : LinearLayout
    lateinit var map_walk: FragmentContainerView
    lateinit var map_trash: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_main)

        flogging_start = findViewById(R.id.flogging_start)
        flogging_time = findViewById(R.id.flogging_time)
        btn_share = findViewById(R.id.btn_share)
        flogging_distance = findViewById(R.id.flogging_distance)

        //하단 버튼 동작
        val fix_bottom = findViewById<View>(R.id.fix_bottom)
        var btn_tipPage: ImageButton
        var btn_homePage: ImageButton
        var btn_communityPage: ImageButton

        btn_tipPage = fix_bottom.findViewById(R.id.btn_tipPage)
        btn_homePage = fix_bottom.findViewById(R.id.btn_homePage)
        btn_communityPage = fix_bottom.findViewById(R.id.btn_communityPage)

        btn_tipPage.setOnClickListener {
            startActivity(Intent(this@AppMain, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@AppMain, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@AppMain, CommunityMain::class.java))
        }

        flogging_start.setOnClickListener {
            flogging_start.setText("종료")
            flogging_timer()
        }

        btn_walk = findViewById(R.id.btn_walk)
        btn_trash = findViewById(R.id.btn_trash)
        btn_weather = findViewById(R.id.btn_weather)

        main_weather = findViewById(R.id.main_weather)
        map_walk = findViewById(R.id.map_walk)
        map_trash = findViewById(R.id.map_trash)

        btn_weather.setOnClickListener {
            main_weather.visibility = View.VISIBLE
            map_walk.visibility = View.GONE
            map_trash.visibility = View.GONE
        }

        btn_walk.setOnClickListener {
            main_weather.visibility = View.GONE
            map_walk.visibility = View.VISIBLE
            map_trash.visibility = View.GONE

            //버튼 색상 변경
            btn_weather.resources.getColor(R.color.green_1)
            btn_walk.resources.getColor(R.color.green_2)
            btn_trash.resources.getColor(R.color.green_1)
            //startActivity(Intent(this@AppMain, Walk::class.java))
        }

        btn_trash.setOnClickListener {
            main_weather.visibility = View.GONE
            map_walk.visibility = View.GONE
            map_trash.visibility = View.VISIBLE

            //버튼 색상 변경
            btn_weather.resources.getColor(R.color.green_1)
            btn_walk.resources.getColor(R.color.green_1)
            btn_trash.resources.getColor(R.color.green_2)
            //startActivity(Intent(this@AppMain, Trash::class.java))
        }

        //팝업
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.flogging_popup, null)
        popup_time = mDialogView.findViewById(R.id.popup_time)

        btn_share.setOnClickListener {
            // Dialog만들기
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            mBuilder.show()
        }
    }

    //타이머 시작
    fun flogging_timer() {
        time = 0
        timerTask = timer(period = 10) {
            time ++

            val sec = time/100
            var milli = time%100

            runOnUiThread {
                flogging_time.text = "${sec} : ${milli}"
                popup_time.text = "시간 ${sec} : ${milli}"
            }
        }

        flogging_start.setOnClickListener {
            //flogging_start.visibility = View.INVISIBLE
            btn_share.visibility = View.VISIBLE
            flogging_stop()
        }
    }

    fun flogging_stop() {
        flogging_start.setText("재시작")
        timerTask?.cancel()

        flogging_start.setOnClickListener {
            time = 0
            flogging_time.text = "00 : 00"
            flogging_start.setText("시작")
            btn_share.visibility = View.INVISIBLE
        }
    }
}