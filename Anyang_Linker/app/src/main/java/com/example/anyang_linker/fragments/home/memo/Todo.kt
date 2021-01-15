package com.example.anyang_linker.fragments.home.memo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo (
    var ischecked: Boolean,
    var title: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}