package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class Join : AppCompatActivity() {

    private val RC_SIGN_IN = 9001
    private var googleSignInClient: GoogleSignInClient?=null
    lateinit var auth: FirebaseAuth // 객체의 공유 인스턴스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join)


        //clause (이용약관)약관확인 페이지로 이동
        val clause = findViewById<Button>(R.id.clause)

        clause.setOnClickListener {
            val intent = Intent(this, Clause::class.java)
            startActivity(intent)
        }
        //clause2 (개인정보처리)약관확인 페이지로 이동
        val clause2 = findViewById<Button>(R.id.clause2)

        clause2.setOnClickListener {
            val intent = Intent(this, Clause2::class.java)
            startActivity(intent)
        }



        var sign_in_button2 = findViewById<SignInButton>(R.id.sign_in_button2)
        setGoogleButtonText(sign_in_button2,"Google 계정으로 회원가입")

        auth = FirebaseAuth.getInstance() // 바꿈


        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val checkBox2 = findViewById<CheckBox>(R.id.checkBox2)

        sign_in_button2.setOnClickListener(View.OnClickListener {
            if (checkBox.isChecked() && checkBox2.isChecked()){
                var signInIntent = googleSignInClient?.signInIntent
                googleSignInClient!!.signOut()  //선택되게
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
            else{
                if (checkBox.isChecked()){
                    Toast.makeText(this, "개인정보 처리방침에 동의해주세요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    if (checkBox2.isChecked()){
                        Toast.makeText(this, "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
                    }

                    else{
                        Toast.makeText(this, "약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("82394092320-kga33mchu8l8j2dna7bi8arkjrt45ugr.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

// 구글에 인증 하는 부분입니다
//--------------------------------------------------------------------------------------
//객체에서 ID 토큰을 가져 옮

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            try { //회원가입 성공시
                val account = result?.signInAccount //(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
                Toast.makeText(this, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AppMain::class.java)
                startActivity(intent)
            } catch (e: ApiException) {//회원가입 실패
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                    task ->
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


    private fun setGoogleButtonText(loginButton: SignInButton, buttonText: String){
        var i = 0
        while (i < loginButton.childCount){
            var v = loginButton.getChildAt(i)
            if (v is TextView) {
                var tv = v
                tv.setText(buttonText)
                tv.setGravity(Gravity.CENTER)
                return
            }
            i++

        }
    }

}