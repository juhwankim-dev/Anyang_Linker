package com.example.anyang_linker.Intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.anyang_linker.MainActivity.Companion.currentUserProfile
import com.example.anyang_linker.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_register.*


class RegisterActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null // 인증을 위한거

    var isNameNotEmpty = false
    var isStudentNumberNotEmpty = false
    var isEmailNotEmpty = false
    var isPasswordNotEmpty = false
    var isAgreeChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        // 이름 적는 칸
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isNameNotEmpty = p0.toString().trim().isNotEmpty()
                if(isNameNotEmpty) activeButton()
            }
        })

        // 학번 적는 칸
        editTextStudentNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isStudentNumberNotEmpty = p0.toString().trim().isNotEmpty()
                if(isStudentNumberNotEmpty) activeButton()
            }
        })

        // 이메일 적는 칸
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isEmailNotEmpty = p0.toString().trim().isNotEmpty()
                if(isEmailNotEmpty) activeButton()
            }
        })

        // 비번 적는 칸
        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isPasswordNotEmpty = p0.toString().trim().isNotEmpty()
                if(isPasswordNotEmpty) activeButton()
            }
        })

        // 비번 확인 적는 칸
        editTextPassword2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().trim().isNotEmpty()) activeButton()
            }
        })

        // 홈으로 가기 (엑스 버튼)
        btn_go_home.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        // 가입버튼 누를시
        btn_go_certify_activity.setOnClickListener {
            signUpUser()
        }

        // 체크박스 동의 누를시
        checkbox_agree.setOnClickListener {
            isAgreeChecked = checkbox_agree.isChecked
            activeButton()
        }
    }

    // 버튼 활성화 할지 말지 정하는 메소드
    private fun activeButton(){
        if(isNameNotEmpty && isStudentNumberNotEmpty && isEmailNotEmpty && isPasswordNotEmpty && isAgreeChecked){
            btn_go_certify_activity.isEnabled = true
            btn_go_certify_activity.setBackgroundResource(R.drawable.border_blue_fill_round)
        } else {
            btn_go_certify_activity.isEnabled = false
            btn_go_certify_activity.setBackgroundResource(R.drawable.border_gray_fill_round)
        }
    }

    private fun signUpUser() {
        if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()){
            editTextEmail.error = "이메일을 입력해주세요"
            editTextEmail.requestFocus()
            return
        }
        if ( (editTextPassword.text.toString() != editTextPassword2.text.toString())){
            editTextPassword2.error = "입력하신 두 비밀번호가 일치하지 않습니다."
            editTextPassword2.requestFocus()
            return
        }
        /*else if (!editTextEmail.text.toString().contains("@ayum.anyang.ac.kr")){
            editTextEmail.error = "학교 이메일을 입력해주세요"
            editTextEmail.requestFocus()
            return
        }*/

        btn_go_certify_activity.isEnabled = false // 가입버튼을 더블 클릭 방지

        firebaseAuth!!.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        Snackbar.make(layout_register,"인증 메일을 보내는 중입니다...", Snackbar.LENGTH_LONG).show()
                        userInfoUpdate()
                        verifyEmail()
                    } else { // If sign in fails, display a message to the user.
                        val e = task.exception
                        if(task.exception is FirebaseAuthUserCollisionException) {
                            Snackbar.make(layout_register,"이미 가입된 계정입니다.", Snackbar.LENGTH_LONG).show()
                        }

                        btn_go_certify_activity.isEnabled = true
                        Toast.makeText(this,"가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun verifyEmail(){

        Handler().postDelayed({}, 10L) // 1초 지연시키기 (파이어베이스 업데이트 기다리기)

        var user = firebaseAuth!!.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) { //해당 이메일에 확인메일을 보냄
                    startActivity(Intent(this, CertifyActivity::class.java))
                } else { //메일 보내기 실패
                    val e = task.exception
                    Log.w("인증실패: ", "@@@@@@@@@@@@@@", e);

                    Snackbar.make(layout_register,"인증 메일 전송에 실패하였습니다.\\n관리자에게 문의하세요.", Snackbar.LENGTH_LONG).show()
                }
            })
    }

    private fun userInfoUpdate(){
        var firebaseDatabase = FirebaseDatabase.getInstance() // 데이터베이스 생성
        var databaseReference = firebaseDatabase.reference

        var uri = "default_profile_icon"
        // 파이어베이스에 정보 올리기
        Log.i("여기서 왜막힐까", uri +"@@@@@@@@@@@@@@@@@@@@@@")
        var user = UserDTO(editTextName.text.toString(), editTextStudentNumber.text.toString(), editTextEmail.text.toString(), uri)
        Log.i("여기서 왜막힐까", user.userProfile +"@@@@@@@@@@@@@@@@@@@@@@")
        databaseReference.child("user").child(firebaseAuth!!.currentUser!!.uid).setValue(user)
    }
}
