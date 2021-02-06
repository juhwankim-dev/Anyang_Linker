package com.example.anyang_linker.Intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.anyang_linker.MainActivity
import com.example.anyang_linker.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_login.*

class LoginActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    var isEmailNotEmpty = false
    var isPasswordNotEmpty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        // 텍스트 변화 감지 리스너
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isEmailNotEmpty = p0.toString().trim().isNotEmpty()
                activeButton()
            }
        })

        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isPasswordNotEmpty = p0.toString().trim().isNotEmpty()
                activeButton()
            }
        })

        // x 눌렀을 때
        btn_goLoginToHome.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        // 로그인 눌렀을 때
        btn_login.setOnClickListener {
            login()
        }
    }

    // 버튼 활성화 할지 말지 정하는 메소드
    private fun activeButton(){
        if(isEmailNotEmpty && isPasswordNotEmpty){
            btn_login.isEnabled = true
            btn_login.setBackgroundResource(R.drawable.border_blue_fill_round)
        } else {
            btn_login.isEnabled = false
            btn_login.setBackgroundResource(R.drawable.border_gray_fill_round)
        }
    }

    // 로그인
    private fun login(){
        if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()){
            editTextEmail.error = "올바른 이메일 형식이 아닙니다."
            editTextEmail.requestFocus()
            return
        }

        firebaseAuth!!.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) { // 로그인에 성공한 경우
                        val user: FirebaseUser = firebaseAuth!!.currentUser!!
                        if(user.isEmailVerified){ // 이메일 인증을 했다면
                            if(checkBox_auto_login.isChecked){ // 자동 로그인이 체크되어 있다면
                                // getSharedPreferences는 모든액티비티에서 사용가능
                                // getPreferences는 현재액티비티에서만 사용가능
                                // MODE_PRIVATE는 현재 앱에서만 사용가능
                                val pref = this.getSharedPreferences("login", Context.MODE_PRIVATE)
                                val editor = pref.edit()
                                editor.putString("email", editTextEmail.text.toString()).apply()
                                editor.putString("password", editTextPassword.text.toString()).apply()
                                editor.commit()
                            }
                            Toast.makeText(this,"로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                            currentUserInfo() // 현재 로그인한 유저의 정보 전역변수로 업데이트
                            startActivity(Intent(this, MainActivity::class.java))
                        } else{
                            Toast.makeText(this,"이메일 인증을 하지 않으셨습니다." + user.isEmailVerified.toString(), Toast.LENGTH_SHORT).show()
                        }
                    } else { // 로그인에 실패한 경우
                        Toast.makeText(this,"아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                    // ...
                })
    }

    // 현재 로그인한 유저의 정보 전역변수로 업데이트
    private fun currentUserInfo () {
        MainActivity.currentUserID = FirebaseAuth.getInstance().currentUser!!.uid

        val firebaseDatabase = FirebaseDatabase.getInstance() // 데이터베이스 생성
        val databaseReference = firebaseDatabase.reference

        databaseReference.child("user").child(MainActivity.currentUserID).addChildEventListener(object :
            ChildEventListener {
            override fun onChildAdded(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
                if(dataSnapshot.key == "userName"){
                    MainActivity.currentUserName = dataSnapshot.value.toString()
                }
                else if(dataSnapshot.key == "userStudentNumber"){
                    MainActivity.currentUserStudentNumber = dataSnapshot.value.toString()
                }
                else if(dataSnapshot.key == "userProfile"){
                    MainActivity.currentUserProfile = dataSnapshot.value.toString()
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
