package com.example.flavi.model.data.database.map

import com.example.flavi.model.data.database.entitydb.HistorySearchDb
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.CountriesDTOKinopoisk
import com.example.flavi.model.data.datasource.GenresDTO
import com.example.flavi.model.data.datasource.GenresDTOKinopoisk
import com.example.flavi.model.domain.entity.HistorySearch
import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.entity.kinopoiskDev.MovieCardKinopoisk
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail

fun UserDbModel.toEntity() = User(userId, name, password, email)

fun List<HistorySearchDb>.toEntity(): List<HistorySearch> {
    return map { HistorySearch(userId = it.userId, title = it.title, id = it.id) }
}

fun MovieCard.toMoviesDbModel(userId: String) = MoviesDbModel(
    id = id,
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
    id = id,
    userMovieId = userMovieId,
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

fun MovieDetail.toMovieCard() = MovieCard(
    id = id,
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

fun MovieCardKinopoisk.toMovieCardEntity() = MovieCard(
    id = movieId,
    filmId = id,
    nameRu = name,
    nameEn = alternativeName,
    year = year.toString(),
    posterUrlPreview = poster.previewUrl,
    rating = rating.imdb.toString(),
    genres = genres.toListGenresDTO(),
    countries = countries.toListCountrieDTO(),
    isFavorite = isFavorite
)

private fun List<GenresDTOKinopoisk>.toListGenresDTO(): List<GenresDTO> {
    return map { GenresDTO(genre = it.name) }
}

private fun List<CountriesDTOKinopoisk>.toListCountrieDTO(): List<CountriesDTO> {
    return map { CountriesDTO(it.name) }
}

fun MovieDetail.toMovieCardEntity() = MovieCard(
    id = id,
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