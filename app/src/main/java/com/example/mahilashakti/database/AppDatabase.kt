package com.example.mahilashakti.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TransactionEntity::class,MemberEntity::class],
    version = 3
)

abstract class AppDatabase :

    RoomDatabase() {

    abstract fun transactionDao():
            TransactionDao

    abstract fun memberDao():
            MemberDao

    companion object {

        @Volatile
        private var INSTANCE:
                AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {


            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "finance_database"
                    ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}