package com.foxxx.messageboardapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foxxx.messageboardapp.databinding.ActivityEditAdsBinding
import com.foxxx.messageboardapp.dialogs.DialogSpinner
import com.foxxx.messageboardapp.fragments.FragmentCloseInterface
import com.foxxx.messageboardapp.fragments.ListImagesFragment
import com.foxxx.messageboardapp.utils.CityHelper
import com.foxxx.messageboardapp.utils.ImagePicker


class EditAdsActivity : AppCompatActivity(), FragmentCloseInterface {

    private val binding by lazy {
        ActivityEditAdsBinding.inflate(layoutInflater)
    }

    private val dialog = DialogSpinner()
    private var listCountries = arrayListOf<String>()
    private var listCities = arrayListOf<String>()
    private var isImagesPermissionGranted = false


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

        binding.imageButtonEdit.setOnClickListener {
//            if(imageAdapter.mainArray.size == 0){
//
//                ImagePicker.pixLauncher(this, 3)
//
//            } else {
//
//                openChooseItemFragment(null)
//                chooseImageFragment?.updateAdapterFromEdit(imageAdapter.mainArray)
//
//            }
        }
    }

    private fun openChooseItemFragment(newList: ArrayList<String>?){
        val fragment = ListImagesFragment(this)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.pixCameraContainer, fragment)
            .commit()
    }

    override fun onFragmentClose() {
        TODO("Not yet implemented")
    }


    companion object {
        fun newIntentEditAdsActivity(context: Context) =
            Intent(context, EditAdsActivity::class.java)
    }


}