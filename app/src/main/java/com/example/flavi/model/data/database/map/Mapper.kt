package com.example.flavi.model.data.database.map

import com.example.flavi.model.data.database.entitydb.HistorySearchDb
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.datasource.actors.ActorDTO
import com.example.flavi.model.data.datasource.actors.ActorSearchDTO
import com.example.flavi.model.data.datasource.awards.AwardsDTO
import com.example.flavi.model.data.datasource.countries.CountriesDTO
import com.example.flavi.model.data.datasource.countries.CountriesDTOKinopoisk
import com.example.flavi.model.data.datasource.genres.GenresDTO
import com.example.flavi.model.data.datasource.genres.GenresDTOKinopoisk
import com.example.flavi.model.data.datasource.images.PosterDTO
import com.example.flavi.model.domain.entity.HistorySearch
import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.entity.kinopoiskDev.MovieCardKinopoisk
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Awards
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SearchActor

fun UserDbModel.toEntity() = User(userId, name, password, email)

fun List<HistorySearchDb>.toEntity(): List<HistorySearch> {
    return map { HistorySearch(userId = it.userId, title = it.title, id = it.id) }
}

fun List<PosterDTO>.toListPosterEntity(): List<Poster> {
    return map {
        Poster(
            previewUrl = it.previewUrl
        )
    }
}

fun List<AwardsDTO>.toListEntityAwards(): List<Awards> {
    return map {
        Awards(
            name = it.name,
            nominationName = it.nominationName,
            year = it.year,
            persons = it.persons.toListPersons()
        )
    }
}

private fun List<ActorSearchDTO?>.toListPersons(): List<SearchActor> {
    return map {
        SearchActor(
            kinopoiskId = it?.kinopoiskId!!,
            nameRu = it.nameRu,
            nameEn = it.nameEn,
            posterUrl = it.posterUrl
        )
    }
}

fun PosterDTO.toPosterEntity(): Poster {
    return Poster(
        previewUrl = previewUrl
    )
}

fun ActorSearchDTO.toActorEntity(): SearchActor {
    return SearchActor(
        kinopoiskId = kinopoiskId,
        nameRu = nameRu,
        nameEn = nameEn,
        posterUrl = posterUrl
    )
}

fun ActorDTO.toActor(): Actor {
    return Actor(
        staffId = this.staffId,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        description = this.description,
        posterUrl = this.posterUrl,
        professionText = this.professionText,
        professionKey = this.professionKey
    )
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