package com.example.guru2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AppMain : AppCompatActivity() {

    lateinit var flogging_start:Button
    lateinit var floggint_time:TextView

    private var isRunning = false
    private var sec = 0L
    private var milli = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)

        flogging_start = findViewById(R.id.flogging_start)
        floggint_time = findViewById(R.id.flogging_time)

        flogging_start.setOnClickListener {
            flogging_start.setText("종료")

        }
    }
}