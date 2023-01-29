package com.example.guru2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guru2.data.Data
import com.example.guru2.data.Walk
//import com.example.guru2.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
//    private lateinit var binding: ActivityMapsBinding
    val TAG: String = "로그"

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는

    private lateinit var horizonManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

//        binding = ActivityMapsBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        val json = assets.open("map_walk.json").reader().readText()
        val data = JSONObject(json).getJSONObject("data")

        Log.d(TAG, "JSON: " + data)

        try {
            val information = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = information.signingInfo.apkContentsSigners
            for (signature in signatures) {
                val md = MessageDigest.getInstance("SHA").apply {
                    update(signature.toByteArray())
                }
                val HASH_CODE = String(Base64.encode(md.digest(), 0))

                Log.d(TAG, "HASH_CODE -> $HASH_CODE")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception -> $e")
        }



        mLocationRequest =  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        //현재 위치 가져오기
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            locationResult.lastLocation?.let { onLocationChanged(it) }
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        Log.d(TAG, "위도" + mLastLocation.latitude)
        Log.d(TAG,"경도" + mLastLocation.longitude)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        loadWalks()

        //특정 지역에 마커 표시
        val LATLNG = LatLng(37.628295, 127.090367)
        mMap.addMarker(MarkerOptions().position(LATLNG).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LATLNG))

//        var bitmapDrawable: BitmapDrawable
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            bitmapDrawable = getDrawable(R.drawable.map_icon) as BitmapDrawable
//        } else {
//            bitmapDrawable = resources.getDrawable(R.drawable.map_icon) as BitmapDrawable
//        }
    }


//
//        var discriptor = BitmapDescriptorFactory.fromBitmap(bitmapDrawable.bitmap)
//
//
//        var markerOptions = MarkerOptions()
//            .position(LATLNG)
//            .title("Marker in Seoul City Hall")
//            .icon(discriptor)
//
//        val cameraPosition = CameraPosition.Builder()
//            .target(LATLNG)
//            .zoom(15.0f)
//            .build()
//
//        mMap.clear()
//        mMap.addMarker(markerOptions)
//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//
//        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
//        mMap.moveCamera(cameraUpdate)
//
//        mMap.addMarker(markerOptions)
//    }

    fun loadWalks() {
        // 02~05는 여기에 입력합니다.
        val retrofit = Retrofit.Builder()
            .baseUrl(WalkOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val walkOpenService = retrofit.create(WalkOpenService::class.java)

        walkOpenService.getWalk(WalkOpenApi.API_KEY).enqueue(object : Callback<Walk> {
            override fun onResponse(call: Call<Walk>, response: Response<Walk>) {
                showWalk(response.body() as Data)
            }

            override fun onFailure(call: Call<Walk>, t: Throwable) {
                Toast.makeText(baseContext, "서버에서 데이터를 가져올 수 없습니다.", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showWalk(walks: Data) {
        val latLngBounds = LatLngBounds.Builder()

//        for (w in walks.data.row) {
//            val position = LatLng(w.XCNTS.toDouble(), w.YDNTS.toDouble())
//            val marker = MarkerOptions().position(position).title(Data.doro_name)
//            mMap.addMarker(marker)
//
//            latLngBounds.include(marker.position)
//        }

        val bounds = latLngBounds.build()
        val padding = 0
        val updated = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.moveCamera(updated)
    }
}