package com.monika_astonkar.examplecountries.di

import com.monika_astonkar.examplecountries.model.CountriesApi
import com.monika_astonkar.examplecountries.model.CountriesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Monika_Astonkar on 02,October,2021
 */
//di package name stands for Dependency Injection
@Module
class ApiModule {

    private val BASE_URL = "https://raw.githubusercontent.com"

    @Provides
    fun provideCountriesAPI(): CountriesApi {
        /*
      Note:- Here we are having Retrofit builder which basically creates the framework for retrofit,
      which will help to get the information from the backend.
      2. Then set Base Url for backend server
      3. As the data on backend server is present in JSON format. So for that we are
      using a Gson converter that will transform Json code to Kotlin code. ***IMP
      i.e It converts JSON data from backend to Objects/POJO's in our project.
      4. Adapter is basically a JavaAdapter which transforms our data i.e Country data class
      into an observable variable.. same as View model
      5. build()=> build retrofit system\
      6. Provide type of information that retrofit should return
   */
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

    @Provides
    fun provideCountriesService(): CountriesService {
        return CountriesService()
    }
}