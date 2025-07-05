package com.example.flavi.model.data.database.entitydb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String
)