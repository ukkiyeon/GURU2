package com.example.guru2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*

class PostPhoto : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    var auth : FirebaseAuth? = null //2. 유저 정보 가져올 수 있도록
    var firestore : FirebaseFirestore? = null //2. db 사용할 수 있도록

    lateinit var postphoto_btn_upload : Button
    lateinit var postphoto_image : ImageView
    lateinit var postphoto_edit_explain : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_photo)

        // 위젯 변수 연결
        postphoto_btn_upload = findViewById(R.id.postphoto_btn_upload)
        postphoto_image = findViewById(R.id.postphoto_image)
        postphoto_edit_explain = findViewById(R.id.postphoto_edit_explain)

        // 파이어베이스 관련 초기화
        storage = FirebaseStorage.getInstance() // db 초기화
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // 앨범 열기
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        // UPLOAD 버튼 클릭
        postphoto_btn_upload.setOnClickListener {
            contentUpload() // 만들어둔 업로드 메소드 실행
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

        // 파이어베이스에 올라갈 이미지 이름 만들어주는 코드
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + timestamp + "_.png" // 파일명

        var storageRef = storage?.reference?.child("images")?.child(imageFileName) // images라는 폴더에 파일명으로 저장

        // 2번째 방법: Promise method (권장)
        storageRef?.putFile(photoUri!!)?.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl // 이미지 주소를 리턴함
        }?.addOnSuccessListener { uri ->

            // DB 입력 시작
            var contentDTO = ContentDTO() // 이미지 주소를 받아오자마자 데이터 모델 만들기 시작. contentDTO 선언

            contentDTO.imageUrl = uri.toString() // 이미지 다운로드 url
            contentDTO.uid = auth?.currentUser?.uid // uid
            contentDTO.userId = auth?.currentUser?.displayName // 이름
            contentDTO.explain = postphoto_edit_explain.text.toString() // 이미지 설명
            contentDTO.timestamp = System.currentTimeMillis() // timestamp

            firestore?.collection("images")?.document()?.set(contentDTO)

            setResult(Activity.RESULT_OK) // 정상적으로 닫혔다고 RESULT_OK 넘김
            finish() // DB 업로드 후 창 닫기기
        }

        /* 다른 방법
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
            // 성공시 메시지
            Toast.makeText(this, getString(R.string.upload_success), Toast.LENGTH_LONG).show()

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                var contentDTO = ContentDTO()

                // Insert downloadUrl of image
                contentDTO.imageUrl = uri.toString()

                // Insert uid of user
                contentDTO.uid = auth?.currentUser?.uid

                // Insert userId
                contentDTO.userId = auth?.currentUser?.email

                // Insert explain of content
                contentDTO.explain = addphoto_edit_explain.text.toString()

                // Insert timestamp
                contentDTO.timestamp = System.currentTimeMillis()

                firestore?.collection("images")?.document()?.set(contentDTO)

                setResult(Activity.RESULT_OK)
                finish()
            }
        }*/

    }
}