package com.example.guru2

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner

class CommunityWriting : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_writing)

        // Spinner
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
        monthSpinner.adapter = monthAdapter
    }
}