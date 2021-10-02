package com.monika_astonkar.examplecountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monika_astonkar.examplecountries.model.CountriesService
import com.monika_astonkar.examplecountries.model.Country
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Monika_Astonkar on 02,October,2021
 */
class CountriesListViewModel : ViewModel() {

    //Note:- LiveData Variables => It is a variable that anyone can subscribe to and can access the data in real time
    //When this variable updates, then all the subscribers to that variable will get notified.
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val countriesService = CountriesService()
    //Disposable variable basically sense this view model is using RxJava to get information
    //from the service. When the view model is closed, we need to close or clear that connection.
    private val disposable = CompositeDisposable()

    //We are using this public function as refresh() which will be called from anywhere in project
    // And we don't want to expose the inside functionality thats why we are calling another private function from that
    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries(){
        loading.value = true
        disposable.add( //Attaching disposable to service
            //Note: We don't want our information fetching to be done on main thread of application.
            //The service call the backend and point through the internet and waits for the response.
            //If we do that on main thread of application then it will block User interface.
            //(ANR)
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread()) //We are subscribing observable on new thread
                .observeOn(AndroidSchedulers.mainThread()) //But the result of that processing should get on main thread
                    //that thread/UI user is seeing
                    //In short, we are subscribing and doing processes on a seperate thread,
                    //but getting all the information on main thread.
                .subscribeWith(object: DisposableSingleObserver<List<Country>>(){
                   // This is disposable single observer which determines what we gonna do when we get the
                    //required information from backend server.
                    override fun onSuccess(value: List<Country>?) {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        countryLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

//    private fun fetchCountries(){
//        val mockData = listOf(
//            Country("CountryA"),
//            Country("CountryB"),
//            Country("CountryC"),
//            Country("CountryD"),
//            Country("CountryE"),
//            Country("CountryF"),
//            Country("CountryH"),
//            Country("CountryI"),
//            Country("CountryJ")
//        )
//
//        //This specifies I don't have any error while loading data and
//        //notify that subscribers/update variables
//        countryLoadError.value = false
//        loading.value = false
//        countries.value = mockData
//    }

}


//Note:- What is Retrofit?
//Retrofit is a REST client for an android which simply means that it han


/*Important retrofit not0es:-
//Retrofit takes care to not use network unnecessarily. If it already has that information and it is
//up to date, then it will not call the back end and point to retrieve information.
//=> If the information expires then it will call the endpoint again to retrieve fresh new information.
//=> If you keep UI as it is for 10-15 mins and don't refresh it and after 10-20 mins try to refresh,
then retrofit will have network call.


*********************************************************************************************
//Note: Dependency Injection:-
Dependency Injection allows us to make the seperation between creating the object and using it.
Currently ViewModel was creating a service and using it.
But, now dependency injection will alter the game .
Now=> ViewModel <====@Inject Dagger2(Service)
Service will reside in Dagger2 and we will inject that to View model.
That way now, the list view doesn't know how to create the service, only knows how to use it.

=> Dependency Injection is having 3 concepts:-
1. @Inject Annotation=> which tells dagger which variable to inject
2. @Module Annotation=> which defines HOW to create the objects that we want to inject.
3. @Component Annotation=> which links these two together.
For these things to work, dagger needs to generate these classes that perform this injectiom.

=> Dagger separates between the creation API and the use of API.(*****)
***********************************************************************************************
*/

/* What is Unit Testing?
1. Automated => It is automated and can run independently of the application.
2. Only test business logic => Unit tests only test business logic and there should be no Android specific code to run in the test.
3. Thousands of Unit Tests => A typical large scale application has thousand of unit that each test
one small piece of the code.
4. Test success path and failure path
 */

