package com.example.flavi.model.data.database.entitydb

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithMovies(
    @Embedded
    val userDbModel: UserDbModel,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userMovieId"
    )
    val movies: MoviesDbModel
)