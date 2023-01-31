package com.example.guru2

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import java.util.*
import kotlin.concurrent.timer

class AppMain : AppCompatActivity() {

    //타이머
    lateinit var plogging_start:Button
    lateinit var plogging_time:TextView
    lateinit var btn_share:ImageView
    lateinit var plogging_distance:TextView
    private var time = 0
    private var distance = 0
    private var timerTask : Timer?=null
    private var sec = 0
    private var milli = 0

    //버튼 3개
    lateinit var btn_walk: LinearLayout
    lateinit var btn_trash:LinearLayout
    lateinit var btn_weather:LinearLayout
    lateinit var btn_1 :AppCompatButton
    lateinit var btn_2 :AppCompatButton
    lateinit var btn_3 :AppCompatButton
    lateinit var btn_4 :AppCompatButton
    lateinit var btn_5 :AppCompatButton
    lateinit var btn_6 :AppCompatButton
    lateinit var btn_7 :AppCompatButton
    lateinit var btn_8 :AppCompatButton
    lateinit var btn_9 :AppCompatButton

    lateinit var btn_mypage:ImageButton

    //팝업
    lateinit var popup_time :TextView
    lateinit var popup_distance:TextView
    lateinit var instagram :ImageButton
    var pro: AlertDialog? = null

    lateinit var main_weather : LinearLayout
    lateinit var main_walk:LinearLayout
    lateinit var trash_num1:TextView
    lateinit var trash_num2:TextView
    lateinit var trash_num3:TextView
    lateinit var trash_num4:TextView
    lateinit var trash_num5:TextView
    lateinit var trash_num6:TextView
    lateinit var trash_num7:TextView
    lateinit var trash_num8:TextView
    lateinit var trash_num9:TextView
    lateinit var trash_num10:TextView
    
    lateinit var walks_num1:TextView
    lateinit var walks_num2:TextView
    lateinit var walks_num3:TextView
    lateinit var walks_num4:TextView
    lateinit var walks_num5:TextView
    lateinit var walks_num6:TextView
    lateinit var walks_num7:TextView
    lateinit var walks_num8:TextView
    lateinit var walks_num9:TextView
    lateinit var walks_more:TextView

    //DB
    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_main)

        plogging_start = findViewById(R.id.plogging_start)
        plogging_start.setText("시작")
        plogging_time = findViewById(R.id.plogging_time)
        btn_share = findViewById(R.id.btn_share)
        plogging_distance = findViewById(R.id.plogging_distance)
        btn_mypage = findViewById(R.id.btn_mypage)
        main_weather = findViewById(R.id.main_weather)
        main_walk = findViewById(R.id.layout_2)

        //이름 불러오기
        val mypage_name = findViewById<TextView>(R.id.mypage_name2)
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
//            Log.d("username" + name.toString())
//            if(name != null) {
                mypage_name.text = name+"님 안녕하세요"
//            } else {
//                mypage_name.text = "먼저 로그인 하세요."
//                startActivity(Intent(this@AppMain, Login::class.java))
//            }
        }

        //DB
        myHelper = myDBHelper(this)
        sqlDB = myHelper.writableDatabase
        myHelper.onUpgrade(sqlDB, 1, 2)

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
            startActivity(Intent(this@AppMain, PostCommunity::class.java))
        }

        //플로깅 시작 버튼 클릭
        plogging_start.setOnClickListener {
            plogging_start()
        }

        //버튼 3개
        btn_walk = findViewById(R.id.btn_walk)
        btn_trash = findViewById(R.id.btn_trash)
        btn_weather = findViewById(R.id.btn_weather)

        btn_1 = findViewById(R.id.btn_1)
        btn_2 = findViewById(R.id.btn_2)
        btn_3 = findViewById(R.id.btn_3)
        btn_4 = findViewById(R.id.btn_4)
        btn_5 = findViewById(R.id.btn_5)
        btn_6 = findViewById(R.id.btn_6)
        btn_7 = findViewById(R.id.btn_7)
        btn_8 = findViewById(R.id.btn_8)
        btn_9 = findViewById(R.id.btn_9)

        val trash = findViewById<View>(R.id.trash)

        //쓰레기통 목록 변수
        trash_num1 = findViewById(R.id.trash_num)
        trash_num2 = findViewById(R.id.trash_num2)
        trash_num3 = findViewById(R.id.trash_num3)
        trash_num4 = findViewById(R.id.trash_num4)
        trash_num5 = findViewById(R.id.trash_num5)
        trash_num6 = findViewById(R.id.trash_num6)
        trash_num7 = findViewById(R.id.trash_num7)
        trash_num8 = findViewById(R.id.trash_num8)
        trash_num9 = findViewById(R.id.trash_num9)
        trash_num10 = findViewById(R.id.trash_num10)

        //산책로 목록 변수
        walks_num1 = findViewById(R.id.walks_num)
        walks_num2 = findViewById(R.id.walks_num2)
        walks_num3 = findViewById(R.id.walks_num3)
        walks_num4 = findViewById(R.id.walks_num4)
        walks_num5 = findViewById(R.id.walks_num5)
        walks_num6 = findViewById(R.id.walks_num6)
        walks_num7 = findViewById(R.id.walks_num7)
        walks_num8 = findViewById(R.id.walks_num8)
        walks_num9 = findViewById(R.id.walks_num9)
        walks_more = findViewById(R.id.walks_more)
        walks_more.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://data.seoul.go.kr/dataList/OA-394/S/1/datasetView.do"))
            startActivity(intent)
        }

        //공공 데이터 연결
        val jsonString_w = assets.open("json_walk.json").reader().readText();
        val jsonString_t = assets.open("json_trash.json").reader().readText();

        val jsonArray_w = JSONArray(jsonString_w)
        val jsonArray_t = JSONArray(jsonString_t)
        var str1 :String

        str1 = jsonArray_w.getJSONObject(0).getString("p_park") + " (" + jsonArray_w.getJSONObject(0).getString("p_addr") + ")"
        walks_num2.text = str1
        str1 = jsonArray_w.getJSONObject(1).getString("p_park") + " (" + jsonArray_w.getJSONObject(1).getString("p_addr") + ")"
        walks_num3.text = str1
        str1 = jsonArray_w.getJSONObject(2).getString("p_park") + " (" + jsonArray_w.getJSONObject(2).getString("p_addr") + ")"
        walks_num4.text = str1
        str1 = jsonArray_w.getJSONObject(3).getString("p_park") + " (" + jsonArray_w.getJSONObject(3).getString("p_addr") + ")"
        walks_num5.text = str1
        str1 = jsonArray_w.getJSONObject(4).getString("p_park") + " (" + jsonArray_w.getJSONObject(4).getString("p_addr") + ")"
        walks_num6.text = str1
        str1 = jsonArray_w.getJSONObject(5).getString("p_park") + " (" + jsonArray_w.getJSONObject(5).getString("p_addr") + ")"
        walks_num7.text = str1
        str1 = jsonArray_w.getJSONObject(6).getString("p_park") + " (" + jsonArray_w.getJSONObject(6).getString("p_addr") + ")"
        walks_num8.text = str1
        str1 = jsonArray_w.getJSONObject(7).getString("p_park") + " (" + jsonArray_w.getJSONObject(7).getString("p_addr") + ")"
        walks_num9.text = str1

        str1 = jsonArray_t.getJSONObject(0).getString("연번") + " " +jsonArray_t.getJSONObject(0).getString("도로명") + " " +jsonArray_t.getJSONObject(0).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(0).getString("해당 동")
        trash_num2.text = str1
        str1 = jsonArray_t.getJSONObject(1).getString("연번") + " " +jsonArray_t.getJSONObject(1).getString("도로명") + " " +jsonArray_t.getJSONObject(1).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(1).getString("해당 동")
        trash_num3.text = str1
        str1 = jsonArray_t.getJSONObject(2).getString("연번") + " " +jsonArray_t.getJSONObject(2).getString("도로명") + " " +jsonArray_t.getJSONObject(2).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(2).getString("해당 동")
        trash_num4.text = str1
        str1 = jsonArray_t.getJSONObject(3).getString("연번") + " " +jsonArray_t.getJSONObject(3).getString("도로명") + " " +jsonArray_t.getJSONObject(3).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(3).getString("해당 동")
        trash_num5.text = str1
        str1 = jsonArray_t.getJSONObject(4).getString("연번") + " " +jsonArray_t.getJSONObject(4).getString("도로명") + " " +jsonArray_t.getJSONObject(4).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(4).getString("해당 동")
        trash_num6.text = str1
        str1 = jsonArray_t.getJSONObject(5).getString("연번") + " " +jsonArray_t.getJSONObject(5).getString("도로명") + " " +jsonArray_t.getJSONObject(5).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(5).getString("해당 동")
        trash_num7.text = str1
        str1 = jsonArray_t.getJSONObject(6).getString("연번") + " " +jsonArray_t.getJSONObject(6).getString("도로명") + " " +jsonArray_t.getJSONObject(6).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(6).getString("해당 동")
        trash_num8.text = str1
        str1 = jsonArray_t.getJSONObject(7).getString("연번") + " " +jsonArray_t.getJSONObject(7).getString("도로명") + " " +jsonArray_t.getJSONObject(7).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(7).getString("해당 동")
        trash_num9.text = str1
        str1 = jsonArray_t.getJSONObject(7).getString("연번") + " " +jsonArray_t.getJSONObject(8).getString("도로명") + " " +jsonArray_t.getJSONObject(8).getString("세부번지(도로명)") + " " +jsonArray_t.getJSONObject(8).getString("해당 동")
        trash_num10.text = str1

        //버튼 색상 변경, view 숨기기/보여지게 하기
        btn_1.setOnClickListener {
            main_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            btn_trash.visibility = View.GONE
        }
        btn_4.setOnClickListener {
            main_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.VISIBLE
            btn_walk.visibility = View.GONE
            btn_trash.visibility = View.GONE
        }
        btn_7.setOnClickListener {
            main_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.VISIBLE
            btn_walk.visibility = View.GONE
            btn_trash.visibility = View.GONE
        }

        btn_2.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.VISIBLE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.VISIBLE
            btn_trash.visibility = View.GONE
        }
        btn_5.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.VISIBLE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.VISIBLE
            btn_trash.visibility = View.GONE
        }
        btn_8.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.VISIBLE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.VISIBLE
            btn_trash.visibility = View.GONE
        }

        btn_3.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.GONE
            trash.visibility = View.VISIBLE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.GONE
            btn_trash.visibility = View.VISIBLE
        }
        btn_6.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.GONE
            trash.visibility = View.VISIBLE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.GONE
            btn_trash.visibility = View.VISIBLE
        }
        btn_9.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.GONE
            trash.visibility = View.VISIBLE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.GONE
            btn_trash.visibility = View.VISIBLE
        }

        //팝업
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.plogging_popup, null)
        val mBuilder = AlertDialog.Builder(this)

        popup_time = mDialogView.findViewById(R.id.popup_time)
        popup_distance = mDialogView.findViewById(R.id.popup_distance)
        instagram = mDialogView.findViewById(R.id.instagram)

        btn_share.setOnClickListener {
            // Dialog만들기
            mBuilder.setView(mDialogView).show()
        }
        //인스타 그램 이미지 클릭 시 이동
        instagram.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
            startActivity(intent)
        }

        //마이페이지 이동
        btn_mypage.setOnClickListener{
            startActivity(Intent(this@AppMain, Mypage::class.java))
        }
    }

    //DB
    inner class myDBHelper(context : Context) : SQLiteOpenHelper(context, "plogging", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE plogging (sec Integer, milli Integer, distance Integer);")
            sqlDB.execSQL("INSERT INTO plogging VALUES('"+0+"','"+0+"','"+0+"');")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS plogging")
            onCreate(db)
        }
    }

    //타이머 시작
    fun plogging_start() {
        plogging_start.setText("종료")
        time = 0
        distance = 0
        timerTask = timer(period = 10) {
            time ++

            sec = time/100
            milli = time%100
            runOnUiThread {
                plogging_time.text = "${sec} : ${milli}"
                popup_time.text = "시간  ${sec} : ${milli}"

                plogging_distance.text = "${distance} m"
                popup_distance.text = "이동거리  ${distance} m"
            }
            //이동 거리 증가
            if(sec >= 2 && milli == 0) {
                distance += 1
            }
        }
        //타이머 종료 클릭
        plogging_start.setOnClickListener {
            btn_share.visibility = View.VISIBLE
            plogging_stop()
        }
    }

    //타이머 종료
    fun plogging_stop() {
        plogging_start.setText("초기화")
        //타이머 멈추기
        timerTask?.cancel()
        //DB에 시간, 이동거리 넣기
        sqlDB.execSQL("INSERT INTO plogging VALUES('"+sec+"','"+milli+"','"+distance+"');")
        sqlDB.close()

        plogging_start.setOnClickListener {  //값 초기화
            plogging_time.text = "00 : 00"
            plogging_distance.text = "0 m"
            plogging_start.setText("시작")
            btn_share.visibility = View.INVISIBLE
            plogging_restart()
        }
    }

    //타이머 재시작
    fun plogging_restart() {
        timerTask?.cancel()

        plogging_start.setOnClickListener {  //값 초기화
            plogging_start()
        }
    }
}