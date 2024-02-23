package com.practice.roomcoroutines.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.roomcoroutines.models.User

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user_table WHERE user_name = :userName")
    suspend fun getUser(userName: String): User

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUser(id:Long)
}