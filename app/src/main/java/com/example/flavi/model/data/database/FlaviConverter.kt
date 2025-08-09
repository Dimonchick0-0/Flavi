package com.example.flavi.model.data.database

import androidx.room.TypeConverter
import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import com.google.gson.Gson

class FlaviConverter {

    @TypeConverter
    fun fromListGenresDTOToString(list: List<GenresDTO>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListGenresDTO(string: String): List<GenresDTO> {
        return Gson().fromJson(string, Array<GenresDTO>::class.java).toList()
    }

    @TypeConverter
    fun fromListCountriesDTOToString(list: List<CountriesDTO>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListCountriesDTO(string: String): List<CountriesDTO> {
        return Gson().fromJson(string, Array<CountriesDTO>::class.java).toList()
    }

}