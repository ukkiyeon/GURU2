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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import java.util.*
import kotlin.concurrent.timer

class AppMain : AppCompatActivity() {

    //타이머
    lateinit var flogging_start:Button
    lateinit var flogging_time:TextView
    lateinit var btn_share:ImageView
    lateinit var flogging_distance:TextView
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
    lateinit var walks_num1:TextView
    lateinit var walks_num2:TextView
    lateinit var walks_num3:TextView
    lateinit var walks_num4:TextView
    lateinit var walks_num5:TextView
    lateinit var walks_num6:TextView
    lateinit var walks_num7:TextView
    lateinit var walks_num8:TextView
    lateinit var walks_num9:TextView
    lateinit var walks_num10:TextView

    //지도
    private lateinit var mMap: GoogleMap
    val TAG: String = "로그"
    private val REQUEST_PERMISSION_LOCATION = 10

    private lateinit var binding: ActivityMapsBinding
    val itemList = arrayListOf<WalkData>()
    lateinit var walkAdapter : RecycleAdapter

    //DB
    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_main)

        //카카오 해시값
//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        }

        flogging_start = findViewById(R.id.flogging_start)
        flogging_start.setText("시작")
        flogging_time = findViewById(R.id.flogging_time)
        btn_share = findViewById(R.id.btn_share)
        flogging_distance = findViewById(R.id.flogging_distance)
        btn_mypage = findViewById(R.id.btn_mypage)
        main_weather = findViewById(R.id.main_weather)
        main_walk = findViewById(R.id.walk_map)

        //이름 불러오기
        val mypage_name = findViewById<TextView>(R.id.mypage_name2)
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            mypage_name.text = name+"님 안녕하세요"
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
            startActivity(Intent(this@AppMain, Tip::class.java))
        }

        btn_homePage.setOnClickListener {
            startActivity(Intent(this@AppMain, AppMain::class.java))
        }

        btn_communityPage.setOnClickListener {
            startActivity(Intent(this@AppMain, PostCommunity::class.java))
        }

        flogging_start.setOnClickListener {
            flogging_start()
        }

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
        walks_num1 = findViewById(R.id.walks_num1)
        walks_num2 = findViewById(R.id.walks_num2)
        walks_num3 = findViewById(R.id.walks_num3)
        walks_num4 = findViewById(R.id.walks_num4)
        walks_num5 = findViewById(R.id.walks_num5)
        walks_num6 = findViewById(R.id.walks_num6)
        walks_num7 = findViewById(R.id.walks_num7)
        walks_num8 = findViewById(R.id.walks_num8)
        walks_num9 = findViewById(R.id.walks_num9)
        walks_num10 = findViewById(R.id.walks_num10)

        val jsonString = assets.open("map_walk.json").reader().readText();
//        Log.d("JSON STR", jsonString)

        val jsonArray = JSONArray(jsonString)
        var str1 :String

//        for (i in 0 until jsonArray.length()) {
        str1 = jsonArray.getJSONObject(0).getString("연번") + " " +jsonArray.getJSONObject(0).getString("도로명") + " " +jsonArray.getJSONObject(0).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(0).getString("해당 동")
        walks_num1.text = str1
        str1 = jsonArray.getJSONObject(1).getString("연번") + " " +jsonArray.getJSONObject(1).getString("도로명") + " " +jsonArray.getJSONObject(1).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(1).getString("해당 동")
        walks_num2.text = str1
        str1 = jsonArray.getJSONObject(2).getString("연번") + " " +jsonArray.getJSONObject(2).getString("도로명") + " " +jsonArray.getJSONObject(2).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(2).getString("해당 동")
        walks_num3.text = str1
        str1 = jsonArray.getJSONObject(3).getString("연번") + " " +jsonArray.getJSONObject(3).getString("도로명") + " " +jsonArray.getJSONObject(3).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(3).getString("해당 동")
        walks_num4.text = str1
        str1 = jsonArray.getJSONObject(4).getString("연번") + " " +jsonArray.getJSONObject(4).getString("도로명") + " " +jsonArray.getJSONObject(4).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(4).getString("해당 동")
        walks_num5.text = str1
        str1 = jsonArray.getJSONObject(5).getString("연번") + " " +jsonArray.getJSONObject(5).getString("도로명") + " " +jsonArray.getJSONObject(5).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(5).getString("해당 동")
        walks_num6.text = str1
        str1 = jsonArray.getJSONObject(6).getString("연번") + " " +jsonArray.getJSONObject(6).getString("도로명") + " " +jsonArray.getJSONObject(6).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(6).getString("해당 동")
        walks_num7.text = str1
        str1 = jsonArray.getJSONObject(7).getString("연번") + " " +jsonArray.getJSONObject(7).getString("도로명") + " " +jsonArray.getJSONObject(7).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(7).getString("해당 동")
        walks_num8.text = str1
        str1 = jsonArray.getJSONObject(7).getString("연번") + " " +jsonArray.getJSONObject(8).getString("도로명") + " " +jsonArray.getJSONObject(8).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(8).getString("해당 동")
        walks_num9.text = str1
        str1 = jsonArray.getJSONObject(9).getString("연번") + " " +jsonArray.getJSONObject(9).getString("도로명") + " " +jsonArray.getJSONObject(9).getString("세부번지(도로명)") + " " +jsonArray.getJSONObject(9).getString("해당 동")
        walks_num10.text = str1
//        var str2 = jsonArray.getJSONObject(i).getString("도로명")
//        var str3 = jsonArray.getJSONObject(i).getString("세부번지(도로명)")
//        var str4 = jsonArray.getJSONObject(i).getString("해당 동")
        Log.d("jsonObject", str1)
//        }

        btn_1.setOnClickListener {
            main_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            trash.visibility = View.GONE

            btn_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            btn_trash.visibility = View.GONE
        }
        btn_4.setOnClickListener {
            main_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            trash.visibility = View.GONE

            btn_weather.visibility = View.VISIBLE
            btn_walk.visibility = View.GONE
            btn_trash.visibility = View.GONE
        }
        btn_7.setOnClickListener {
            main_weather.visibility = View.VISIBLE
            main_walk.visibility = View.GONE
            trash.visibility = View.GONE

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
//            checkPermissionForLocation(this)
//            binding = ActivityMapsBinding.inflate(layoutInflater)
//            setContentView(binding.root)
        }
        btn_5.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.VISIBLE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.VISIBLE
            btn_trash.visibility = View.GONE
//            checkPermissionForLocation(this)
//            binding = ActivityMapsBinding.inflate(layoutInflater)
//            setContentView(binding.root)
        }
        btn_8.setOnClickListener {
            main_weather.visibility = View.GONE
            main_walk.visibility = View.VISIBLE
            trash.visibility = View.GONE
            //버튼 색상 변경
            btn_weather.visibility = View.GONE
            btn_walk.visibility = View.VISIBLE
            btn_trash.visibility = View.GONE
//            checkPermissionForLocation(this)
//            binding = ActivityMapsBinding.inflate(layoutInflater)
//            setContentView(binding.root)
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
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.flogging_popup, null)
        val mBuilder = AlertDialog.Builder(this)

        popup_time = mDialogView.findViewById(R.id.popup_time)
        popup_distance = mDialogView.findViewById(R.id.popup_distance)
        instagram = mDialogView.findViewById(R.id.instagram)

        btn_share.setOnClickListener {
            // Dialog만들기
            mBuilder.setView(mDialogView).show()
        }

        instagram.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
            startActivity(intent)
        }

        //마이페이지 이동
        btn_mypage.setOnClickListener{
            startActivity(Intent(this@AppMain, Mypage::class.java))
        }

        //DB
        myHelper = myDBHelper(this)

    }
    //DB
    inner class myDBHelper(context : Context) : SQLiteOpenHelper(context, "flogging", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE flogging (sec text, milli text, distance text);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("INSERT INTO flogging VALUES('"+"0"+"','"+"0"+"','"+"0"+"');")
            db!!.execSQL("DROP TABLE IF EXISTS flogging")
            onCreate(db)
        }
    }

    //타이머 시작
    fun flogging_start() {
        flogging_start.setText("종료")
        time = 0
        distance = 0
        timerTask = timer(period = 10) {
            time ++

            sec = time/100
            milli = time%100


            runOnUiThread {
                flogging_time.text = "${sec} : ${milli}"
                popup_time.text = "시간  ${sec} : ${milli}"

                flogging_distance.text = "${distance} m"
                popup_distance.text = "이동거리  ${distance} m"
            }
            //이동 거리
            if(sec >= 2 && milli == 0) {
                distance += 1
            }
        }
        flogging_start.setOnClickListener { //시작 클릭
            btn_share.visibility = View.VISIBLE

            sqlDB = myHelper.writableDatabase
            myHelper.onUpgrade(sqlDB, 1, 2)
            sqlDB.execSQL("INSERT INTO flogging VALUES('"+sec.toString()+"','"+milli.toString()+"','"+distance.toString()+"');")
            Log.d("sec", "DB 입력 2 " + sec + " " + milli + " " + distance)
            sqlDB.close()

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

//    fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        val SEOUL = LatLng(37.56, 126.97)
//        val markerOptions = MarkerOptions() // 마커 생성
//        markerOptions.position(SEOUL)
//        markerOptions.title("서울") // 마커 제목
//        markerOptions.snippet("한국의 수도") // 마커 설명
//        mMap.addMarker(markerOptions)
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)) // 초기 위치
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f)) // 줌의 정도
//        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID // 지도 유형 설정
//    }

    // 위치 권한이 있는지 확인하는 메서드
    fun checkPermissionForLocation(context: Context): Boolean {
        Log.d(TAG, "checkPermissionForLocation()")
        Log.d(TAG, context.toString())
        // Android 6.0 Marshmallow 이상에서는 지리 확보(위치) 권한에 추가 런타임 권한이 필요합니다.
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "checkPermissionForLocation() 권한 상태 : O")
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                Log.d(TAG, "checkPermissionForLocation() 권한 상태 : X")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }
    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult()")
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult() _ 권한 허용 클릭")
                //startLocationUpdates()
            } else {
                Log.d(TAG, "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this@AppMain, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private val mapIcon: BitmapDescriptor by lazy {
//        val color = ContextCompat.getColor(this, R.color.green_2)
////        BitmapHelper.vectorToBitmap(this, R.drawable.mapIcon, color)
//    }

//    private fun addMarkers(googleMap: GoogleMap) {
//        places.forEach { place ->
//            val marker = googleMap.addMarker(
//                MarkerOptions()
//                    .title(place.name)
//                    .position(place.latLng)
//                    .icon(mapIcon)
//            )
//
//            // Set place as the tag on the marker object so it can be referenced within
//            // MarkerInfoWindowAdapter
//            if (marker != null) {
//                marker.tag = place
//            }
//        }
//    }

//    private fun addClusteredMarkers(googleMap: GoogleMap) {
//        // Create the ClusterManager class and set the custom renderer.
//        val clusterManager = ClusterManager<Place>(this, googleMap)
//        clusterManager.renderer =
//            PlacesReader(
//                this,
//                googleMap,
//                clusterManager
//            )
//
//        // Set custom info window adapter
//        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
//
//        // Add the places to the ClusterManager.
//        clusterManager.addItems(places)
//        clusterManager.cluster()
//
//        // Set ClusterManager as the OnCameraIdleListener so that it
//        // can re-cluster when zooming in and out.
//        googleMap.setOnCameraIdleListener {
//            clusterManager.onCameraIdle()
//        }
//    }
}