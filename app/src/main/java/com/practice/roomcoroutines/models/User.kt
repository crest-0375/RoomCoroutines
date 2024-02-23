package com.practice.roomcoroutines.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @ColumnInfo("user_name")
    val userName: String,
    @ColumnInfo("password_hash")
    val passwordHash: Int,
    val info: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)
