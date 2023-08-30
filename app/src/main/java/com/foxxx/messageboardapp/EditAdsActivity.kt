package com.foxxx.messageboardapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foxxx.messageboardapp.databinding.ActivityEditAdsBinding
import com.foxxx.messageboardapp.dialogs.DialogSpinner
import com.foxxx.messageboardapp.utils.CityHelper

class EditAdsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditAdsBinding.inflate(layoutInflater)
    }

    private val dialog = DialogSpinner()
    private var listCountries = arrayListOf<String>()
    private var listCities = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvCountrySearch.setOnClickListener {
            listCountries = CityHelper.getAllCountries(this)
            dialog.showSpinnerDialog(
                context = this,
                list = listCountries,
                itemTextView = binding.tvCountrySearch
            )
            if (binding.tvCitySearch.text.toString() != getString(R.string.select_city)) {
                binding.tvCitySearch.text = getString(R.string.select_city)
            }
        }


        binding.tvCitySearch.setOnClickListener {
            val selectedCountry = binding.tvCountrySearch.text.toString()
            if (selectedCountry != getString(R.string.select_country)) {
                listCities = CityHelper.getAllCitiesFromCountry(
                    this,
                    selectedCountry
                )
                dialog.showSpinnerDialog(
                    context = this,
                    list = listCities,
                    itemTextView = binding.tvCitySearch
                )
            } else {
                Toast.makeText(this, getString(R.string.no_country_selected), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    companion object {
        fun newIntentEditAdsActivity(context: Context) =
            Intent(context, EditAdsActivity::class.java)
    }
}