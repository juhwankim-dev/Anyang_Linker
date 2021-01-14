package com.example.anyang_linker.fragments.home.memo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getAll(): List<Memo>

    /* import android.arch.persistence.room.OnConflictStrategy.REPLACE */
    @Insert(onConflict = REPLACE)
    fun insert(cat: Memo)

    @Query("DELETE from memo")
    fun deleteAll()
}