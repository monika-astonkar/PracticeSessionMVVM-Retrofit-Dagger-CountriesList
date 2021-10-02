package com.monika_astonkar.examplecountries.model

import com.monika_astonkar.examplecountries.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Monika_Astonkar on 02,October,2021
 */
class CountriesService {

    @Inject
    lateinit var api:CountriesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }

}