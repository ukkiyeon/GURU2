package com.example.guru2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.community_writing.*

class CommunityWriting : AppCompatActivity() {
    lateinit var backBtn: ImageButton
    private val TAG = CommunityWriting::class.java.simpleName
    /*private lateinit var auth = FirebaseAuth*/
    /*private var uid:String=""*/

    /*lateinit var button: Button
    lateinit var title: EditText
    lateinit var content: EditText*/

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var mArrayList : ArrayList<DataModel>
    lateinit var mAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_writing)

        mArrayList = arrayListOf<DataModel>()
        mAdapter = DataAdapter(mArrayList)

        /*et_title = findViewById(R.id.title_text_form)
        et_content =findViewById(R.id.content_text_form)*/


        CommunityWriting.layoutManager = LinearLayoutManager(this)
        CommunityWriting.adapter = mAdapter
        CommunityWriting.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager(this).orientation
            )
        )

        mArrayList.clear()
        mAdapter.notifyDataSetChanged()

        initDatabase()

        form_button.setOnClickListener {
            val title = title_text_form.text.toString()
            val content = content_text_form.text.toString()

            // 데이터베이스에 데이터 삽입
            val databaseReference1 = firebaseDatabase.getReference("Data")
            databaseReference1.child(title).setValue(content)

            title_text_form.setText("")
            content_text_form.setText("")
        }

        /*뒤로가기 버튼 기능*/
        backBtn = findViewById(R.id.back)

        backBtn.setOnClickListener {
            var intent = Intent(this, Community::class.java)
            startActivity(intent)
        }
    }


    fun initDatabase() {
        // 파이어베이스 인스턴스 생성
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Data")
    }
        /*auth = FirebaseAuth.getInstance()*/

        /*// 버튼 연결
        button = findViewById(R.id.form_button)
        title = findViewById(R.id.title_text_form)
        content = findViewById(R.id.content_text_form)*/



        /*if(intent.hasExtra("uid")){

            uid= intent.getStringExtra("uid").toString()
        }*/

        /*button.setOnClickListener{
            val database = Firebase.database
            val myRef = database.getReference()

            val dataInput = DataModel(
                title.text.toString(),
                content.text.toString()
            )

            myRef.child("board").setValue(dataInput)
        }*/



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
    companion object {
            fun addItemDecoration(dividerItemDecoration: DividerItemDecoration) {

            }

            lateinit var adapter: DataAdapter
            lateinit var layoutManager: LinearLayoutManager
    }
}