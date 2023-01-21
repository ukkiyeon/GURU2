package com.example.guru2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationMenuView

class MainActivity : AppCompatActivity() {
    lateinit var containers : FrameLayout
    lateinit var bottom_navigationview : BottomNavigationMenuView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        containers = findViewById(R.id.containers)
        bottom_navigationview = findViewById(R.id.bottom_navigationview)

        supportFragmentManager.beginTransaction().add(containers.id, MainFragment()).commit()


    }
}