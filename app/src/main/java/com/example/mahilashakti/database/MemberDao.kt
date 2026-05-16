package com.example.mahilashakti.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemberDao {

    @Insert
    suspend fun insertMember(
        member: MemberEntity
    )


    @Query(
        "SELECT * FROM members"
    )
    suspend fun getAllMembers():
            List<MemberEntity>

    @Query(
        "SELECT * FROM members WHERE memberName = :name LIMIT 1"
    )
    suspend fun getMemberByName(
        name: String
    ): MemberEntity?

    @Delete
    suspend fun deleteMember(
        member: MemberEntity
    )
}