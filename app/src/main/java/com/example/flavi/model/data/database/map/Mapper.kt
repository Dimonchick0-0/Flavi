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
import com.example.flavi.model.data.datasource.reviews.ReviewDTO
import com.example.flavi.model.data.datasource.reviews.ReviewListDTO
import com.example.flavi.model.data.datasource.reviews.ReviewTypeDTO
import com.example.flavi.model.data.datasource.sequelsandprequels.MoviesSequelAndPrequelDTO
import com.example.flavi.model.data.datasource.sequelsandprequels.RelationsTypeDTO
import com.example.flavi.model.data.datasource.similars.SimilarMovieDTO
import com.example.flavi.model.domain.entity.HistorySearch
import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.entity.kinopoiskDev.MovieCardKinopoisk
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Awards
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.RelationsType
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ReviewList
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ReviewType
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SearchActor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie

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

private fun RelationsTypeDTO.toRelationType(): RelationsType {
    return when(this) {
        RelationsTypeDTO.PREQUEL -> RelationsType.PREQUEL
        RelationsTypeDTO.SEQUEL -> RelationsType.SEQUEL
    }
}

fun MoviesSequelAndPrequelDTO.toSequelsAndPrequels(): MoviesSequelAndPrequel {
    return MoviesSequelAndPrequel(
        filmId = filmId,
        nameRu = nameRu,
        nameEn = nameEn,
        posterUrlPreview = posterUrlPreview,
        relationsType = relationType.toRelationType()
    )
}

fun SimilarMovieDTO.toSimilar(): SimilarMovie {
    return SimilarMovie(
        filmId = filmId,
        nameRu = nameRu,
        nameEn = nameEn,
        posterUrlPreview = posterUrlPreview
    )
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

private fun ReviewTypeDTO.toReviewType(): ReviewType {
    return when (this) {
        ReviewTypeDTO.POSITIVE -> ReviewType.POSITIVE
        ReviewTypeDTO.NEGATIVE -> ReviewType.NEGATIVE
        ReviewTypeDTO.NEUTRAL -> ReviewType.NEUTRAL
        ReviewTypeDTO.NOT_SELECTED -> ReviewType.NOT_SELECTED
    }
}

private fun List<ReviewDTO>.toReviewListEntity(): List<Review> {
    return map {
        Review(
            type = it.type.toReviewType(),
            date = it.date,
            positiveRating = it.positiveRating,
            negativeRating = it.negativeRating,
            author = it.author,
            title = it.title,
            description = it.description
        )
    }
}

fun ReviewListDTO.toReviewList(): ReviewList {
    return ReviewList(
        total = total,
        totalPositiveReviews = totalPositiveReviews,
        totalNegativeReviews = totalNegativeReviews,
        totalNeutralReviews = totalNeutralReviews,
        items = items.toReviewListEntity()
    )
}

fun ReviewDTO.toReview(): Review {
    return Review(
        type = type.toReviewType(),
        date = date,
        positiveRating = positiveRating,
        negativeRating = negativeRating,
        author = author,
        title = title,
        description = description
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