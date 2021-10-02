package com.monika_astonkar.examplecountries.model

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Monika_Astonkar on 02,October,2021
 */
//This interface will have functions that will be called in
//order to retrieve information from the backend.
interface CountriesApi {

    //This is a static file on github => Endpoint
    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>
    //Single is an observable that emits one variable and then closes

}