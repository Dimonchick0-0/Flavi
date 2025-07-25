package com.example.flavi.model.data.database.map

import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.User
import kotlinx.coroutines.flow.Flow

fun UserDbModel.toEntity() = User(id, name, password, email)
