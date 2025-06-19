package com.example.flavi.data.database.map

import com.example.flavi.data.database.entitydb.UserDbModel
import com.example.flavi.domain.entity.User

fun User.toDbModel() = UserDbModel(id, name, email, password)