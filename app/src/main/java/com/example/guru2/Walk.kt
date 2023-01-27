package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Walk : AppCompatActivity() {

    lateinit var btn_weather: Button
    lateinit var btn_trash: Button

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walk)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_weather = findViewById(R.id.btn_weather)
        btn_trash = findViewById(R.id.btn_trash)

        btn_weather.setOnClickListener {
            startActivity(Intent(this@Walk, AppMain::class.java))
        }

        btn_trash.setOnClickListener {
            startActivity(Intent(this@Walk, Trash::class.java))
        }
    }

    fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val marker = LatLng(35.241615, 128.695587)
        mMap.addMarker(MarkerOptions().position(marker).title("마커 제목"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
    }
}

private fun SupportMapFragment.getMapAsync(walk: Walk) {

}