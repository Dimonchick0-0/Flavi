package com.example.flavi.model.data.database.map

import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.data.datasource.RatingDTO
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.User

fun UserDbModel.toEntity() = User(userId, name, password, email)

fun MovieCard.toMoviesDbModel(userId: String) = MoviesDbModel(
    userMovieId = userId,
    movieId = id,
    name = name,
    alternativeName = alternativeName,
    poster = poster.url,
    year = year,
    rating = rating.imdb,
    genres = genres,
    countries = countries,
    isFavorite = isFavorite
)

fun MoviesDbModel.toMoviesCardEntity() = MovieCard(
    id = movieId,
    name = name,
    alternativeName = alternativeName,
    poster = PosterDTO(url = poster),
    year = year,
    rating = RatingDTO(imdb = rating),
    genres = genres,
    countries = countries,
    isFavorite = isFavorite
)