package com.example.anyang_linker.fragments.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.anyang_linker.R
import com.example.anyang_linker.SplashActivity
import com.example.anyang_linker.fragments.home.memo.Memo
import com.example.anyang_linker.fragments.home.memo.MemoDB
import com.example.anyang_linker.fragments.home.memo.MemoList
import kotlinx.android.synthetic.main.activity_popup.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.lang.Exception

class HomeFragment : Fragment(){
    private var memoListDB: MemoDB? = null
    private var memoList = listOf<MemoList>()
    private var test = "기본"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        /* ------------------------------ 더보기 클릭했을 때 -------------------------------*/
        view.txtBtnMore.setOnClickListener { view ->
            val goAllnoticesActivity = Intent(activity, AllNoticesActivity::class.java)
            startActivity(goAllnoticesActivity)
        }
        /* ------------------------------ 더보기 클릭했을 때 -------------------------------*/


        /* ------------------------------ Adapter랑 Manager -------------------------------*/
        /* 뭔지 모르겠지만 Fragment에 Adapter추가하려면 해야하는거 */
        // val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val thisWeekRecyclerView = view.findViewById(R.id.thisWeekRecyclerView) as RecyclerView

        /* 매니저랑 어댑터 추가 */
        thisWeekRecyclerView.adapter = ThisWeekAdapter(memoList) // 어댑터 생성
        thisWeekRecyclerView.layoutManager = LinearLayoutManager(activity)
        /* ------------------------------ Adapter랑 Manager -------------------------------*/

        /* ------------------------------ 플로팅 액션 버튼 -------------------------------*/
        memoListDB = MemoDB.getInstance(context!!)

        val addRunnable = Runnable {
            val newMemo = Memo()
            newMemo.content = test
            memoListDB?.memoListDao()?.insert(newMemo)
        }

        view.fab.setOnClickListener { view ->
            val builder = AlertDialog.Builder(activity)
            val dialogView = layoutInflater.inflate(R.layout.activity_popup, null)

            /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    test = dialogEt.text.toString()
                    val addThread = Thread(addRunnable)
                    addThread.start()
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                }
                .show()
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
    }
}
