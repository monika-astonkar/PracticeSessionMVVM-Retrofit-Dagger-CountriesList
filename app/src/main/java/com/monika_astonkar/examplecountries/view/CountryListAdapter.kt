package com.monika_astonkar.examplecountries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.monika_astonkar.examplecountries.R
import com.monika_astonkar.examplecountries.model.Country
import com.monika_astonkar.examplecountries.util.getProgressDrawable
import com.monika_astonkar.examplecountries.util.loadImage

/**
 * Created by Monika_Astonkar on 02,October,2021
 */
class CountryListAdapter(var countries: ArrayList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_country, parent, false)
    )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
       return countries.size
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val countryName = view.findViewById<TextView>(R.id.countryName)
        private val imageView = view.findViewById<ImageView>(R.id.imageView)
        private val countryCapital = view.findViewById<TextView>(R.id.capital)
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country){
            countryName?.text = country.countryName
            countryCapital?.text = country.capital
            imageView.loadImage(country.flag, progressDrawable)
        }
    }
}