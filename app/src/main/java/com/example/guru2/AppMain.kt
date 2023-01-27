package com.example.guru2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.util.*
import kotlin.concurrent.timer
import kotlin.math.roundToInt


class AppMain : AppCompatActivity() {

    //타이머
    lateinit var flogging_start:Button
    lateinit var flogging_time:TextView
    lateinit var btn_share:ImageView
    lateinit var flogging_distance:TextView
    private var time = 0
    private var distance = 0
    private var timerTask : Timer?=null

    //버튼 3개
    lateinit var btn_walk: AppCompatButton
    lateinit var btn_trash:AppCompatButton
    lateinit var btn_weather:AppCompatButton

    //팝업
    lateinit var popup_time :TextView
    lateinit var popup_distance:TextView
    lateinit var instagram :ImageButton

    lateinit var main_weather : LinearLayout
    lateinit var map_walk: FragmentContainerView
    lateinit var map_trash: FragmentContainerView

    lateinit var btn_mypage:ImageButton

    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_main)

        flogging_start = findViewById(R.id.flogging_start)
        flogging_start.setText("시작")
        flogging_time = findViewById(R.id.flogging_time)
        btn_share = findViewById(R.id.btn_share)
        flogging_distance = findViewById(R.id.flogging_distance)

        btn_mypage = findViewById(R.id.btn_mypage)

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
            startActivity(Intent(this@AppMain, Community::class.java))
        }

        flogging_start.setOnClickListener {
            flogging_start()
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
//            btn_weather.setBackgroundColor(R.color.green_1)
//            btn_walk.setBackgroundColor(R.color.green_2)
//            btn_trash.setBackgroundColor(R.color.green_1)
        }

        btn_trash.setOnClickListener {
            main_weather.visibility = View.GONE
            map_walk.visibility = View.GONE
            map_trash.visibility = View.VISIBLE

            //버튼 색상 변경
            btn_weather.resources.getColor(R.color.green_1)
            btn_walk.resources.getColor(R.color.green_1)
            btn_trash.resources.getColor(R.color.green_2)
        }

        //팝업
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.flogging_popup, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        popup_time = mDialogView.findViewById(R.id.popup_time)
        popup_distance = mDialogView.findViewById(R.id.popup_distance)
        instagram = mDialogView.findViewById(R.id.instagram)

        btn_share.setOnClickListener {
            // Dialog만들기
            mBuilder.show()
        }
        instagram.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
            startActivity(intent)
        }

        //마이페이지 이동
        btn_mypage.setOnClickListener{
            startActivity(Intent(this@AppMain, Mypage::class.java))
        }
    }

    //타이머 시작
    fun flogging_start() {
        flogging_start.setText("종료")
        time = 0
        distance = 0
        timerTask = timer(period = 10) {
            time ++

            var sec = time/100
            var milli = time%100

            runOnUiThread {
                flogging_time.text = "${sec} : ${milli}"
                popup_time.text = "시간  ${sec} : ${milli}"

                flogging_distance.text = "${distance} m"
                popup_distance.text = "이동거리  ${distance} m"
            }
            //이동 거리
            if(sec >= 2 && milli == 0) {
                Log.d("sec", "sec : " + sec)
                distance += 1
            }
        }
        flogging_start.setOnClickListener { //시작 클릭
            btn_share.visibility = View.VISIBLE
            flogging_stop()
        }
    }

    fun flogging_stop() {  //타이머 종료
        flogging_start.setText("초기화")
        timerTask?.cancel()

        flogging_start.setOnClickListener {  //값 초기화
            flogging_time.text = "00 : 00"
            flogging_distance.text = "00 m"
            flogging_start.setText("시작")
            btn_share.visibility = View.INVISIBLE
            flogging_restart()
        }
    }

    fun flogging_restart() {  //재시작
        timerTask?.cancel()

        flogging_start.setOnClickListener {  //값 초기화
            flogging_start()
        }
    }
}