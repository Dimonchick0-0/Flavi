package com.example.flavi.model.data.datasource.rental

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentalMovie(
    @SerialName("type") val type: RentalType,
    @SerialName("subType") val subType: RentalSubType?,
    @SerialName("date") val date: String,
    @SerialName("country") val country: RentalMoviesCountry?,
    @SerialName("companies") val companies: List<RentalCompaniesMovie>?
)

enum class RentalType {
    PREMIERE,
    ALL,
    WORLD_PREMIER,
    COUNTRY_SPECIFIC
}

enum class RentalSubType {
    DVD,
    BLURAY
}
