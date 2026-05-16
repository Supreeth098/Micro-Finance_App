package com.example.mahilashakti.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(
        transaction: TransactionEntity
    ): Long

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions():
            List<TransactionEntity>

    @Query(
        "SELECT * FROM transactions WHERE type = :type"
    )
    suspend fun getTransactionsByType(
        type: String
    ): List<TransactionEntity>

    @Query(
        "SELECT * FROM transactions WHERE memberId = :memberId"
    )
    suspend fun getTransactionsByMember(
        memberId: Int
    ): List<TransactionEntity>

    @Delete
    suspend fun deleteTransaction(
        transaction: TransactionEntity
    ): Int

    @Update
    suspend fun updateTransaction(
        transaction: TransactionEntity
    ): Int

    @Query(
        "DELETE FROM transactions WHERE memberId = :memberId"
    )
    suspend fun deleteTransactionsByMember(
        memberId: Int
    )
}