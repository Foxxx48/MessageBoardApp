package com.foxxx.messageboardapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foxxx.messageboardapp.databinding.ActivityEditAdsBinding
import com.foxxx.messageboardapp.dialogs.DialogSpinner
import com.foxxx.messageboardapp.utils.CityHelper

class EditAdsActivity : AppCompatActivity() {

     val binding by lazy {
        ActivityEditAdsBinding.inflate(layoutInflater)
    }

    private val dialog = DialogSpinner()
    private var listCountries = arrayListOf<String>()
    private var listCities = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()

        binding.tvCountrySearch.setOnClickListener {
            dialog.showSpinnerDialog(this, listCountries)

        }

//        dialog.showSpinnerDialog(this, listCountries)
        CityHelper.showCities(this, "Russia")
    }

    private fun init() {
        listCountries = CityHelper.getAllCountries(this)

    }


    companion object {
        fun newIntentEditAdsActivity(context: Context) =
            Intent(context, EditAdsActivity::class.java)
    }
}