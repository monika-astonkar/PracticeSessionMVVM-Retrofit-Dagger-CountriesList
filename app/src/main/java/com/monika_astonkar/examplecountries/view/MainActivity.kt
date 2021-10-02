package com.monika_astonkar.examplecountries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.monika_astonkar.examplecountries.R
import com.monika_astonkar.examplecountries.viewmodel.CountriesListViewModel
import com.monika_astonkar.examplecountries.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Monika_Astonkar on 02,October,2021
 */
class MainActivity : AppCompatActivity() {

    //"lateinit" keyword suggests it's a promise from developer that it will be initialized before
    //it is assigned/used in code.(i.e. variable will be instantiated before it's use)
    private lateinit var viewModel: CountriesListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Typical way of Android life-cycle instantiating view model
        viewModel = ViewModelProvider(this).get(CountriesListViewModel::class.java)
        viewModel.refresh()

        val countriesListRecyclerView = findViewById<RecyclerView>(R.id.countriesListRecyclerView)
        countriesListRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, { countries->
            countries?.let {
                countriesListRecyclerView.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, { isError->
            isError?.let {
                val textError = findViewById<TextView>(R.id.textListError)
                isError.let { textError.visibility = if (it) View.VISIBLE else View.GONE }

            }
        })

        viewModel.loading.observe(this, { isLoading->
            isLoading?.let {
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                progressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    binding.textListError.visibility = View.GONE
                    binding.countriesListRecyclerView.visibility = View.GONE
                }

            }
        })
    }
}