package com.example.anyang_linker.fragments.talk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.anyang_linker.MainActivity
import com.example.anyang_linker.MainActivity.Companion.currentUserID
import com.example.anyang_linker.MainActivity.Companion.currentUserName
import com.example.anyang_linker.MainActivity.Companion.currentUserProfile
import com.example.anyang_linker.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// 채팅방 페이지
class ChatActivity : AppCompatActivity() {

    private val databaseReference = FirebaseDatabase.getInstance().reference
    var myProfileUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var leaderUID = ""
        var chatUID = ""

        if(intent.getStringExtra("leaderUID") != null){ // 톡 남기기를 통해 넘어왔으면
            leaderUID = intent.getStringExtra("leaderUID") // 리더의 UID를 받아오고..
            chatUID = databaseReference.child("chat").push().key!! // 새로운 채팅방(UID)를 생성한다.
            Log.i("리더", leaderUID +"입니다")
            chatListUpdate(leaderUID, chatUID)
        } else {
            chatUID = intent.getStringExtra("chatUID") // 채팅방 리스트에서 클릭하고 넘어온 경우
        }

        Handler().postDelayed({
            userProfileUpdate()
        }, 400)


        // 잠시 지연 주기 (openChat이 실행되기전에 userName을 파이어베이스에서 받아와야하는데... 넘느려서..)
        Handler().postDelayed({
            openChat(chatUID) // 채팅 방 입장
        }, 400)

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_Send_Button.setOnClickListener {
            if (chating_Text!!.text.toString() == ""){ // 입력할 메시지 아무것도 입력안할 때

            }
            else{
                val now = System.currentTimeMillis()
                val date = Date(now)
                //나중에 바꿔줄것
                val sdf = SimpleDateFormat("hh:mm")
                val sdf2 = SimpleDateFormat("aa")
                val getTime = sdf.format(date) // 시간 분
                val getTime2 = sdf2.format(date) // 오전 오후

                val chat = ChatDTO(currentUserName, chating_Text!!.text.toString(), getTime2, getTime, myProfileUrl) //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(chatUID!!).push().setValue(chat) // 데이터 푸쉬
                chating_Text!!.setText("") //입력창 초기화
            }
        }
    }

    // 채팅방 내용 화면에 뿌려주기
    private fun openChat(chatUID: String?) { // 리스트 어댑터 생성 및 세팅
        val mAdapter = ChatAdapter(applicationContext, ArrayList())
        //어댑터 선언
        chat_recyclerview.adapter = mAdapter
        //레이아웃 매니저 선언
        val lm = LinearLayoutManager(this)
        chat_recyclerview.layoutManager = lm
        chat_recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatUID!!)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded( // 리스너를 처음 사용할때 혹은 데이터 추가 했을 때 할거
                    dataSnapshot: DataSnapshot,
                    s: String?
                ) {
                    val chatDTO = dataSnapshot.getValue(ChatDTO::class.java)!!
                    mAdapter.addItem(chatDTO)
                    mAdapter.notifyDataSetChanged() // 방에 처음 들어오거나, 새로운 메시지가 도착하면 refresh
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    //removeMessage(dataSnapshot, mAdapter)
                }
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    // 상대방과 나의 chatList를 업데이트한다. (새로운 채팅방이 생성되면, 그 채팅방의 UID를 서로가 저장한다)
    private fun chatListUpdate(leaderUID: String?, chatUID: String) {
        databaseReference.child("user").child(FirebaseAuth.getInstance().uid!!).child("chatList").push().setValue(chatUID)
        databaseReference.child("user").child(leaderUID!!).child("chatList").push().setValue(chatUID)
    }

    // 각각 사용자의 프로필을 가져와 채팅방정보에 저장한다.
    private fun userProfileUpdate() {
        FirebaseStorage.getInstance().reference.child("images").child(currentUserProfile).downloadUrl.addOnSuccessListener {
            myProfileUrl = it.toString()
        }
    }
}