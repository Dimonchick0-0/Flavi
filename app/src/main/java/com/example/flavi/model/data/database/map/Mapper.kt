package com.example.flavi.model.data.database.map

import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.MoviesDbModelList
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.domain.entity.FilterMovieCard
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.MovieDetail
import com.example.flavi.model.domain.entity.User

fun UserDbModel.toEntity() = User(userId, name, password, email)

fun MovieCard.toMoviesDbModel(userId: String) = MoviesDbModel(
    userMovieId = userId,
    filmId = filmId,
    nameRu = nameRu,
    nameEn = nameEn,
    posterUrlPreview = posterUrlPreview,
    year = year,
    rating = rating,
    genres = genres,
    countries = countries,
    isFavorite = isFavorite
)

fun MoviesDbModel.toMoviesCardEntity() = MovieCard(
    filmId = filmId,
    nameRu = nameRu,
    nameEn = nameEn,
    posterUrlPreview = posterUrlPreview,
    year = year,
    rating = rating,
    genres = genres,
    countries = countries,
    isFavorite = isFavorite

)

fun FilterMovieCard.toMovieCardEntity() = MovieCard(
    filmId = kinopoiskId,
    nameRu = nameRu,
    nameEn = nameOriginal,
    year = year,
    posterUrlPreview = posterUrlPreview,
    rating = ratingImdb.toString(),
    genres = genres,
    countries = countries,
    isFavorite = isFavorite
)

fun MovieDetail.toMovieCardEntity() = MovieCard(
    filmId = kinopoiskId,
    nameRu = nameRu,
    nameEn = nameOriginal,
    year = year.toString(),
    posterUrlPreview = posterUrl,
    rating = ratingImdb.toString(),
    genres = genres,
    countries = countries,
    isFavorite = isFavorite
)