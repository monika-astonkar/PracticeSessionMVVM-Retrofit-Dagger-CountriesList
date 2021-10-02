package com.monika_astonkar.examplecountries.di

import com.monika_astonkar.examplecountries.model.CountriesService
import com.monika_astonkar.examplecountries.viewmodel.CountriesListViewModel
import dagger.Component

/**
 * Created by Monika_Astonkar on 02,October,2021
 */
@Component(modules = [ApiModule::class])
interface ApiComponent {

    //This function will help dagger to inject the right components from API module
    //into CountryService
    fun inject(service: CountriesService)

    fun inject(viewModel: CountriesListViewModel)
}
