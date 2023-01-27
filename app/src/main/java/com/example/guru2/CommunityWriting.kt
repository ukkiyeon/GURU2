package com.example.guru2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class CommunityWriting : AppCompatActivity() {
    lateinit var backBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_writing)

        backBtn = findViewById(R.id.back)

        backBtn.setOnClickListener {
            var intent = Intent(this, Community::class.java)
            startActivity(intent)
        }

        /*// Spinner
        val yearSpinner = findViewById<View>(R.id.spinner_city) as Spinner
        val yearAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.city, android.R.layout.simple_spinner_item
        )
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        yearSpinner.adapter = yearAdapter
        val monthSpinner = findViewById<View>(R.id.spinner_gu) as Spinner
        val monthAdapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.gu, android.R.layout.simple_spinner_item
        )
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        monthSpinner.adapter = monthAdapter*/
    }
}