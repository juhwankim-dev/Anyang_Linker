package com.example.anyang_linker.fragments.talk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anyang_linker.MainActivity.Companion.currentUserID
import com.example.anyang_linker.MainActivity.Companion.currentUserName
import com.example.anyang_linker.MainActivity.Companion.currentUserProfile
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.push.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//const val TOPIC = "/topics/myTopic"

// 채팅방 페이지
class ChatActivity : AppCompatActivity() {

    private val databaseReference = FirebaseDatabase.getInstance().reference
    var myProfileUrl = ""

    //val TAG = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        registerPushToken()

/*        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token // it.token이 만들어낸 임시 UID 값인거지..
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)*/

        var leaderUID = ""
        var chatUID = ""

        if(intent.getStringExtra("leaderUID") != null){ // 톡 남기기를 통해 넘어왔으면
            leaderUID = intent.getStringExtra("leaderUID") // 리더의 UID를 받아오고..
            chatUID = databaseReference.child("chat").push().key!! // 새로운 채팅방(UID)를 생성한다.
            databaseReference.child("chat").child(chatUID).child("participants").push().setValue(leaderUID) // 참가자에 리더 추가
            databaseReference.child("chat").child(chatUID).child("participants").push().setValue(currentUserID) // 참가자에 나 추가
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
                val message = chating_Text!!.text.toString()

                val chat = ChatDTO(currentUserName, message, getTime2, getTime, myProfileUrl) //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(chatUID!!).push().setValue(chat) // 데이터 푸쉬
                chating_Text!!.setText("") //입력창 초기화

                pushMessage(chatUID, message) // 푸시알림 보내기
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
                    Log.i("이게뭐로나오냐", dataSnapshot.children.toString())
                    Log.i("자식이 있는걸로 나옵니까?", dataSnapshot.childrenCount.toString())
                    if(dataSnapshot.childrenCount.toInt() != 2){
                        val chatDTO = dataSnapshot.getValue(ChatDTO::class.java)!!
                        mAdapter.addItem(chatDTO)
                        mAdapter.notifyDataSetChanged() // 방에 처음 들어오거나, 새로운 메시지가 도착하면 refresh
                    }
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

    // 푸시알림 보내기
    private fun pushMessage(chatUID: String, message: String) {
        databaseReference.child("chat").child(chatUID).child("participants").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded( // 리스너를 처음 사용할때 혹은 데이터 추가 했을 때 할거
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
                if(dataSnapshot.value.toString() != currentUserID){
                    Log.i("너는 왜 안들어와", message)
                    FcmPush.instance.sendMessage(dataSnapshot.value.toString(), currentUserName + "님이 메시지를 보냈습니다", message)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                //removeMessage(dataSnapshot, mAdapter)
            }
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

/*    private fun pushMessage() {
        val title = "Hi"
        val message = "test"

        if(title.isNotEmpty() && message.isNotEmpty()){
            PushNotification(
                NotificationData(title, message),
                //TOPIC
                //recipientToken // 받는 사람의 토큰 (UID)
                "y1KIMKg7v0VKlZHU9kw1pG4FTbM2"
            ).also{
                sendNotification((it))
            }
        }
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try{
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful){
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        }catch (e: Exception){
            Log.e(TAG, e.toString())
        }
    }*/

    private fun registerPushToken() {
        //v17.0.0 이전까지는
        ////var pushToken = FirebaseInstanceId.getInstance().token
        //v17.0.1 이후부터는 onTokenRefresh()-depriciated
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            task ->
            val token = task.result?.token
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val map = mutableMapOf<String,Any>()
            map["pushToken"] = token!!

            FirebaseFirestore.getInstance().collection("pushtokens").document(uid!!).set(map)
        }
    }

 /*   private fun pushmsg(){
        var message = user?.email + getString(R.string.app_name) + "테스트입니다" + getString(R.string.project_id)
        fcmPush?.sendMessage(destinationUid, "알림 메세지 입니다.", message)
    }*/

/*    override fun onStop() {
        super.onStop()

        FcmPush.instance.sendMessage("y1KIMKg7v0VKlZHU9kw1pG4FTbM2", "hi", "bye")
    }*/
}