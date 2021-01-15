package com.example.anyang_linker.fragments.home.memo

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getAll(): List<Todo>

    @Insert
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("DELETE FROM Todo WHERE title = :title")
    fun deleteBytitle(title: String)

    @Query("UPDATE Todo SET ischecked = :ischecked WHERE title = :title")
    fun updateByischecked(ischecked: Boolean, title: String)
}