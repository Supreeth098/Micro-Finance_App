package com.example.mahilashakti.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")

data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val memberId: Int,

    val type: String,

    val amount: Int,

    val date: String
)