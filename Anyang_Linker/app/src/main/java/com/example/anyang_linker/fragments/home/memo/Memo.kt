package com.example.anyang_linker.fragments.home.memo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
class Memo(@PrimaryKey var id: Long?,
           @ColumnInfo(name = "ischecked") var ischecked: Boolean,
           @ColumnInfo(name = "content") var content: String?,
           @ColumnInfo(name = "date") var date: String
){
    constructor(): this(null,false, "기본 값","기본 값")
}