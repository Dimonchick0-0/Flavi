package com.example.flavi.model.data.database.map

import com.example.flavi.model.data.database.entitydb.HistorySearchDb
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.datasource.actors.ActorDTO
import com.example.flavi.model.data.datasource.actors.ActorSearchDTO
import com.example.flavi.model.data.datasource.actors.MoviePersonDTO
import com.example.flavi.model.data.datasource.actors.PersonDTO
import com.example.flavi.model.data.datasource.actors.ProfessionKeyDTO
import com.example.flavi.model.data.datasource.actors.SEXDTO
import com.example.flavi.model.data.datasource.awards.AwardsDTO
import com.example.flavi.model.data.datasource.countries.CountriesDTO
import com.example.flavi.model.data.datasource.countries.CountriesDTOKinopoisk
import com.example.flavi.model.data.datasource.filter.FilterMovieDTO
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
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Country
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.FilterMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Genre
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviePerson
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Person
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ProfessionKey
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.RelationsType
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ReviewList
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ReviewType
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SEX
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

private fun ProfessionKeyDTO.toProfessionKey(): ProfessionKey {
    return when(this) {
        ProfessionKeyDTO.DIRECTOR -> ProfessionKey.DIRECTOR
        ProfessionKeyDTO.WRITER -> ProfessionKey.WRITER
        ProfessionKeyDTO.ACTOR -> ProfessionKey.ACTOR
        ProfessionKeyDTO.HIMSELF -> ProfessionKey.HIMSELF
        ProfessionKeyDTO.PRODUCER -> ProfessionKey.PRODUCER
        ProfessionKeyDTO.HRONO_TITR_MALE -> ProfessionKey.HRONO_TITR_MALE
        ProfessionKeyDTO.HRONO_TITR_FEMALE -> ProfessionKey.HRONO_TITR_FEMALE
        ProfessionKeyDTO.HERSELF -> ProfessionKey.HERSELF
        ProfessionKeyDTO.NOT_SELECTED -> ProfessionKey.NOT_SELECTED
    }
}

private fun List<MoviePersonDTO>.toMoviePerson(): List<MoviePerson> {
    return map {
        MoviePerson(
            filmId = it.filmId,
            nameRu = it.nameRu,
            nameEn = it.nameEn,
            rating = it.rating,
            professionKey = it.professionKey.toProfessionKey()
        )
    }
}

private fun SEXDTO.toSex(): SEX {
    return when(this) {
        SEXDTO.MALE -> SEX.MALE
        SEXDTO.FEMALE -> SEX.FEMALE
    }
}

fun PersonDTO.toPerson(): Person {
    return Person(
        personId = personId,
        nameRu = nameRu,
        nameEn = nameEn,
        sex = sex.toSex(),
        posterUrl = posterUrl,
        birthday = birthday,
        death = death,
        age = age,
        birthplace = birthplace,
        deathplace = deathplace,
        profession = profession,
        films = films.toMoviePerson()
    )
}

private fun List<GenresDTOKinopoisk>.toGenreFilter(): List<Genre> {
    return map {
        Genre(genre = it.name)
    }
}

private fun List<CountriesDTOKinopoisk>.toCountryFilter(): List<Country> {
    return map {
        Country(country = it.name)
    }
}

fun MovieCardKinopoisk.toFilterMovieList(): FilterMovie {
    return FilterMovie(
        movieId = movieId,
        kinopoiskId = id,
        nameRu = name,
        nameOriginal = alternativeName,
        genres = genres.toGenreFilter(),
        countries = countries.toCountryFilter(),
        year = year,
        type = "",
        posterUrlPreview = poster.previewUrl,
        ratingImdb = rating.imdb
    )
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
        relationsType = relationType?.toRelationType()
    )
}

private fun List<GenresDTO>.toListFilterMovieGenre(): List<Genre> {
    return map {
        Genre(
            genre = it.genre
        )
    }
}

private fun List<CountriesDTO>.toListFilterCountriesMovie(): List<Country> {
    return map {
        Country(
            country = it.country
        )
    }
}

fun FilterMovieDTO.toFilterMovie(): FilterMovie {
    return FilterMovie(
        movieId = movieId,
        kinopoiskId = kinopoiskId,
        nameRu = nameRu,
        nameOriginal = nameOriginal,
        genres = genres.toListFilterMovieGenre(),
        countries = countries.toListFilterCountriesMovie(),
        year = year,
        type = type,
        posterUrlPreview = posterUrlPreview,
        ratingImdb = ratingImdb
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

fun FilterMovie.toMovieCardEntity() = MovieCard(
    id = movieId,
    filmId = kinopoiskId,
    nameRu = nameRu,
    nameEn = nameOriginal,
    year = year.toString(),
    posterUrlPreview = posterUrlPreview,
    rating = ratingImdb.toString(),
    genres = genres.toListGenresDTO(),
    countries = countries.toListCountrieDTO(),
)

private fun List<Genre>.toListGenresDTO(): List<GenresDTO> {
    return map { GenresDTO(genre = it.genre) }
}

private fun List<Country>.toListCountrieDTO(): List<CountriesDTO> {
    return map { CountriesDTO(it.country) }
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