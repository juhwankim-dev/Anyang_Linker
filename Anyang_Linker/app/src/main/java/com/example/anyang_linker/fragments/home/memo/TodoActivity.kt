package com.example.anyang_linker.fragments.home.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_todo.*

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        /*--------------- DB 불러오기 ----------------*/
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
            .build()
        /*--------------- DB 불러오기 ----------------*/

        /* 추가버튼 클릭하면 */
        btn_todoAdd.setOnClickListener {
            db.todoDao().insert(Todo(false, editText_todoAdd.text.toString()))
            setResult(1)
            finish()
        }

        btn_cancelMemo.setOnClickListener {
            finish()
        }

        /* 삭제버튼 클릭하면 */
/*        btn_todoDel.setOnClickListener {
            db.todoDao().deleteBytitle(editText_todoDel.text.toString())
            setResult(1)
            finish()
        }*/
    }
}
