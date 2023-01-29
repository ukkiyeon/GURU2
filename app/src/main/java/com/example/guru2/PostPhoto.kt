package com.example.guru2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class PostPhoto : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    lateinit var postphoto_btn_upload : Button
    lateinit var postphoto_image : ImageView
    lateinit var editText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_photo)

        // 위젯 변수 연결
        postphoto_btn_upload = findViewById(R.id.postphoto_btn_upload)
        postphoto_image = findViewById(R.id.postphoto_image)
        editText = findViewById(R.id.editText)

        // 스토리지 초기화
        storage = FirebaseStorage.getInstance()

        // 앨범 열기
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        // 버튼에 이미지 업로드 이벤트
        postphoto_btn_upload.setOnClickListener {
            contentUpload() // 메소드 실행
        }


    }
    // 선택한 이미지 받기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_FROM_ALBUM) {
            if(resultCode == Activity.RESULT_OK) {
                // 사진 선택하면 이미지 경로가 여기로 넘어옴
                photoUri = data?.data
                postphoto_image.setImageURI(photoUri)
            } else {
                // 취소 버튼 눌렀을 때 작동
                finish()
            }
        }
    }

    // 업로드 할 때 실행되는 메소드
    fun contentUpload() {
        // 파일명 만들어주는 코드
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + timestamp + "_.png"
        var storageRef = storage?.reference?.child("images")?.child(imageFileName)

        // 파일 업로드
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
            // 성공시 메시지
            Toast.makeText(this, getString(R.string.upload_success), Toast.LENGTH_LONG).show()
        }

    }
}