package com.example.anyang_linker.fragments.home.memo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1)
abstract class MemoDB: RoomDatabase() {
    abstract fun memoListDao(): MemoDao

    companion object {
        private var INSTANCE: MemoDB? = null

        fun getInstance(context: Context): MemoDB? {
            if (INSTANCE == null) {
                synchronized(MemoDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MemoDB::class.java, "memo.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}