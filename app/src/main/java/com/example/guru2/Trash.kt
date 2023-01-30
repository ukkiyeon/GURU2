package com.example.guru2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Trash : AppCompatActivity(), OnMapReadyCallback {

    // GoogleMap - 기본 지도 기능 및 데이터를 관리하기 위한 진입점
    private lateinit var mMap: GoogleMap
    //private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trash)

        // 프래그먼트에 핸들 가져오기 및 콜백 등록하기
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.trashmap) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        // 초기 위치 설정 및 마커 표시
        val LATLNG = LatLng(37.566418, 126.977943)

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            . zoom(15.0f)
            .build()

        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.moveCamera(cameraUpdate)
    }
}