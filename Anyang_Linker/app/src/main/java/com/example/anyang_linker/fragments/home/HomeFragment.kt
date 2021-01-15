package com.example.anyang_linker.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

import com.example.anyang_linker.R
import com.example.anyang_linker.SplashActivity
import com.example.anyang_linker.fragments.home.memo.AppDatabase
import com.example.anyang_linker.fragments.home.memo.TodoActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.today_list_item.*

class HomeFragment : Fragment(){
    lateinit var todoAdapter : ThisWeekAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        /* ------------------------------ DB 읽어오기 -------------------------------*/
        val db = Room.databaseBuilder(
            context!!,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
            .build()
        /* ------------------------------ DB 읽어오기 -------------------------------*/

        /* ------------------------------ 더보기 클릭했을 때 -------------------------------*/
        view.noticeLinearLayout.setOnClickListener { view ->
            val goAllnoticesActivity = Intent(activity, AllNoticesActivity::class.java)
            startActivity(goAllnoticesActivity)
        }
        /* ------------------------------ 더보기 클릭했을 때 -------------------------------*/

        /* ------------------------------ Adapter랑 Manager -------------------------------*/
        /* 뭔지 모르겠지만 Fragment에 Adapter추가하려면 해야하는거 */
        // val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val thisWeekRecyclerView = view.findViewById(R.id.thisWeekRecyclerView) as RecyclerView

        /* 매니저랑 어댑터 추가 */
        var todoList = db.todoDao().getAll()
        todoAdapter = ThisWeekAdapter(todoList)
        thisWeekRecyclerView.adapter = todoAdapter // 어댑터 생성
        thisWeekRecyclerView.layoutManager = LinearLayoutManager(activity)
        /* ------------------------------ Adapter랑 Manager -------------------------------*/

        /* ------------------------------ 플로팅 액션 버튼 -------------------------------*/
        view.fab.setOnClickListener { view ->
            var goTodoActivity = Intent(activity, TodoActivity::class.java)
            startActivityForResult(goTodoActivity, 1)
        }
        /* ------------------------------ 플로팅 액션 버튼 -------------------------------*/

        return view
    }

    override fun onStart() {
        super.onStart()

        /* ------------------------------ 메인 공지 -------------------------------*/
        var str = SplashActivity.mainNotice

        /* [ACE+] 지우고 문장이 너무 길면 뒤에 ... 으로 처리 */
        if(str.length > 30) str = str.slice(7..32) + "..."
        txtMainNotice.text = str
        /* ------------------------------ 메인 공지 -------------------------------*/

        /* ------------------------------ 바로 가기 -------------------------------*/
        imgSubmitReport.setOnClickListener {
            var goReport = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.kr/drive/apps.html"))
            startActivity(goReport);
        }

        imgKakaoTalk.setOnClickListener {
            var goKakao = Intent(Intent.ACTION_VIEW, Uri.parse("https://pf.kakao.com/_jxehRd"))
            startActivity(goKakao);
        }
        /* ------------------------------ 바로 가기 -------------------------------*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            1 -> {
                /* ------------------------------ DB 읽어오기 -------------------------------*/
                val db = Room.databaseBuilder(
                    context!!,
                    AppDatabase::class.java, "database-name"
                ).allowMainThreadQueries()
                    .build()
                /* ------------------------------ DB 읽어오기 -------------------------------*/

                /* ------------------------------ 메모 불러오기 -------------------------------*/
                // 메모 불러오기
                val callRunnable = Runnable {
                    var todoList = db.todoDao().getAll()
                    todoAdapter = ThisWeekAdapter(todoList)

                    thisWeekRecyclerView.adapter = todoAdapter
                    thisWeekRecyclerView.layoutManager = LinearLayoutManager(activity)
                    thisWeekRecyclerView.setHasFixedSize(true)
                }
                /* ------------------------------ 메모 불러오기 -------------------------------*/

                val callThread = Thread(callRunnable)
                callThread.start()
            }
        }
    }
}