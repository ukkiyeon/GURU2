package com.example.guru2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommunityWriting : AppCompatActivity() {
    lateinit var backBtn: ImageButton
    private val TAG = CommunityWriting::class.java.simpleName
    /*private lateinit var auth = FirebaseAuth*/
    /*private var uid:String=""*/

    lateinit var button: Button
    lateinit var title: EditText
    lateinit var content: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_writing)

        /*auth = FirebaseAuth.getInstance()*/

        // 버튼 연결
        button = findViewById(R.id.form_button)
        title = findViewById(R.id.title_text_form)
        content = findViewById(R.id.content_text_form)

        /*뒤로가기 버튼 기능*/
        backBtn = findViewById(R.id.back)

        backBtn.setOnClickListener {
            var intent = Intent(this, Community::class.java)
            startActivity(intent)
        }

        /*if(intent.hasExtra("uid")){

            uid= intent.getStringExtra("uid").toString()
        }*/

        button.setOnClickListener{
            val database = Firebase.database
            val myRef = database.getReference()

            val dataInput = DataModel(
                title.text.toString(),
                content.text.toString()
            )

            myRef.child("board").setValue(dataInput)
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