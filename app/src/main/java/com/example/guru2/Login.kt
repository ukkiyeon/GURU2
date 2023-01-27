package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class Login : AppCompatActivity() {

    private val RC_SIGN_IN = 9001
    private var googleSignInClient: GoogleSignInClient?=null
    lateinit var auth: FirebaseAuth // 객체의 공유 인스턴스


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = Firebase.auth



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("82394092320-kga33mchu8l8j2dna7bi8arkjrt45ugr.apps.googleusercontent.com")
            .requestEmail()
            .build()

        //-----------------------------------------------------------------------
        val signInGoogleBtn: SignInButton = findViewById(R.id.sign_in_button) //구글 sign in 버튼 객체

        signInGoogleBtn.setOnClickListener { //구글 로그인 버튼 이벤트 처리

            //로그인 처리
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient!!.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        firebaseAuthSignOut()
    }// onDestory() 밑부분에 추가 해줍니다.(onCreate 외부)

    // 구글에 인증 하는 부분입니다
    //--------------------------------------------------------------------------------------
    //객체에서 ID 토큰을 가져 옮

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try { //로그인 성공시
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Mypage::class.java)
                startActivity(intent)
            } catch (e: ApiException) {//로그인 실패
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Tip::class.java)
                startActivity(intent)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { //성공
                    val user = auth.currentUser
                    user?.let {

                        val name = user.displayName
                        val email = user.email
                        val displayName = user.displayName
                        val photoUrl = user.photoUrl
                        val emailVerified = user.isEmailVerified
                        val uid = user.uid
                        Log.d("xxxx name",name.toString())
                        Log.d("xxxx email",email.toString())
                        Log.d("xxxx displayName",email.toString())

                    }

                } else { //실패
                    Log.d("xxxx ","signInWithCredential:failure", task.exception)
                }
            }
    }



    private fun firebaseAuthSignOut(){ //로그 아웃 시키기
        Firebase.auth.signOut()
        googleSignInClient!!.signOut()  //로그인 선택되게
    }

}




