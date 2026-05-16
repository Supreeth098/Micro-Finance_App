package com.example.mahilashakti.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")

data class MemberEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val memberName: String
)