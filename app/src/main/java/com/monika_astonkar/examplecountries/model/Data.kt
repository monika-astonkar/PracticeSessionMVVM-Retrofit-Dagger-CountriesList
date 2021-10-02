package com.monika_astonkar.examplecountries.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Monika_Astonkar on 02,October,2021
 */
data class Country(

    @SerializedName("name")
    var countryName: String?,

    @SerializedName("capital")
    var capital: String?,

    @SerializedName("flagPNG")
    val flag: String?
)