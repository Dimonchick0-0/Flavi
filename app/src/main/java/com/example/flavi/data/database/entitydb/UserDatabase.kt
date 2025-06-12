package com.example.flavi.data.database.entitydb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val password: String
)