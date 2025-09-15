package com.example.flavi.model.data.database.entitydb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("historySearch")
data class HistorySearchDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val userId: String = UNKNOWN_USER_ID
) {
    companion object {
//        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_USER_ID = ""
    }
}
