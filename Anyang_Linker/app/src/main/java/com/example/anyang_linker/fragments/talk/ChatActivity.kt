package com.example.anyang_linker.fragments.talk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.talk.TalkFragment.Companion.userId
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private val firebaseDatabase = FirebaseDatabase.getInstance() // 데이터베이스 생성
    private val databaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // 위젯 ID 참조

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        val CHAT_NAME = intent.getStringExtra("chatName") // 인텐트에서 받은 채팅방이름
        //val CHAT_NAME = userChat
        val USER_ID = userId

        /*if(intent.getBooleanExtra("wantNewRoom", false)){
            databaseReference.child("chat").push().setValue("")
        }*/

        //databaseReference.child("chat").child(chatName!!)
        //databaseReference.child("chat").child(CHAT_NAME!!).push().setValue(chat)

        // 채팅 방 입장
        openChat(CHAT_NAME, USER_ID)

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

                val chat = ChatDTO(USER_ID, chating_Text!!.text.toString(), getTime2, getTime) //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME!!).push().setValue(chat) // 데이터 푸쉬
                chating_Text!!.setText("") //입력창 초기화
            }
        }
    }

    private fun addMessage(
        dataSnapshot: DataSnapshot,
        adapter: ChatAdapter
    ) {
        val chatDTO = dataSnapshot.getValue(ChatDTO::class.java)!!
        //adapter.add(chatDTO.userName + " : " + chatDTO.message)
        adapter.addItem(chatDTO)
    }

    private fun removeMessage(
        dataSnapshot: DataSnapshot,
        adapter: ChatAdapter
    ) {
        val chatDTO = dataSnapshot.getValue(ChatDTO::class.java)!!
        //adapter.remove(chatDTO.userName + " : " + chatDTO.message)
    }

    private fun openChat(chatName: String?, userId: String?) { // 리스트 어댑터 생성 및 세팅

        val mAdapter = ChatAdapter(applicationContext, ArrayList())
        //어댑터 선언
        chat_recyclerview.adapter = mAdapter
        //레이아웃 매니저 선언
        val lm = LinearLayoutManager(this)
        chat_recyclerview.layoutManager = lm
        chat_recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName!!)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded( // 리스너를 처음 사용할때 혹은 데이터 추가 했을 때 할거
                    dataSnapshot: DataSnapshot,
                    s: String?
                ) {
                    addMessage(dataSnapshot, mAdapter)
                    mAdapter.notifyDataSetChanged() // 방에 처음 들어오거나, 새로운 메시지가 도착하면 refresh
                }

                override fun onChildChanged(
                    dataSnapshot: DataSnapshot,
                    s: String?
                ) {

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    removeMessage(dataSnapshot, mAdapter)
                }

                override fun onChildMoved(
                    dataSnapshot: DataSnapshot,
                    s: String?
                ) {
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
}
