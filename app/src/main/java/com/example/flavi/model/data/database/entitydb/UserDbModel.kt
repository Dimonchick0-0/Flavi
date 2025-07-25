package com.example.flavi.model.data.database.entitydb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.entity.MoviesCards

@Entity
data class UserDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNKNOWN_ID,
    val name: String = UNKNOWN_NAME,
    val email: String = UNKNOWN_EMAIL,
    val password: String = UNKNOWN_PASSWORD
) {
    companion object {
        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_NAME = ""
        private const val UNKNOWN_PASSWORD = ""
        private const val UNKNOWN_EMAIL = ""
    }
}