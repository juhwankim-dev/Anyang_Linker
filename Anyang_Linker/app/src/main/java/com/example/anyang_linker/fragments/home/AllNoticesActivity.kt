package com.example.anyang_linker.fragments.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anyang_linker.R
import com.example.anyang_linker.SplashActivity.Companion.mainNotice
import kotlinx.android.synthetic.main.activity_all_notices.*
import kotlinx.android.synthetic.main.fragment_home.*

class AllNoticesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_notices)

        /* ------------------------------ Adapter랑 Manager -------------------------------*/
        /* 뭔지 모르겠지만 Fragment에 Adapter추가하려면 해야하는거 */
        /* val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val noticeRV = rootView.findViewById(R.id.noticeRecyclerView) as RecyclerView */

        /* 매니저랑 어댑터 추가 */
        noticeRecyclerView.adapter = MyNoticeAdapter() // 어댑터 생성
        noticeRecyclerView.layoutManager = LinearLayoutManager(this)
        /* ------------------------------ Adapter랑 Manager -------------------------------*/

        imgBtnBackToHome.setOnClickListener {
            finish()
        }
    }
}
