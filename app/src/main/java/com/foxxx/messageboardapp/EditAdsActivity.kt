package com.foxxx.messageboardapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foxxx.messageboardapp.databinding.ActivityEditAdsBinding
import com.foxxx.messageboardapp.dialogs.DialogSpinner
import com.foxxx.messageboardapp.fragments.FragmentCloseInterface
import com.foxxx.messageboardapp.fragments.ListImagesFragment
import com.foxxx.messageboardapp.imageadapter.ImageAdapter
import com.foxxx.messageboardapp.utils.CityHelper
import com.foxxx.messageboardapp.utils.ImagePicker


class EditAdsActivity : AppCompatActivity(), FragmentCloseInterface {

    private val binding by lazy {
        ActivityEditAdsBinding.inflate(layoutInflater)
    }
    private var chooseImageFragment: ListImagesFragment? = null
    private val dialog = DialogSpinner()
    private var listCountries = arrayListOf<String>()
    private var listCities = arrayListOf<String>()
    private var isImagesPermissionGranted = false
    private lateinit var imageAdapter: ImageAdapter
    var editImagePos = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()

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
            if(imageAdapter.mainArray.size == 0){

                ImagePicker.pixLauncher(this, 3)

            } else {

                openChooseItemFragment(null)
                chooseImageFragment?.updateAdapterFromEdit(imageAdapter.mainArray)

            }
        }
    }

    private fun init(){
        imageAdapter = ImageAdapter()
        binding.vpImages.adapter = imageAdapter
    }
    private fun openChooseItemFragment(newList: ArrayList<Uri>?){
        chooseImageFragment = ListImagesFragment(this, newList)
        binding.scrollViewMain.visibility = View.GONE
        val fragment = ListImagesFragment(this, newList)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.pixCameraContainer, fragment)
            .commit()
    }

    override fun onFragmentClose(list : ArrayList<Bitmap>) {
        binding.scrollViewMain.visibility = View.VISIBLE
        imageAdapter.update(list)
        chooseImageFragment = null
    }




    companion object {
        fun newIntentEditAdsActivity(context: Context) =
            Intent(context, EditAdsActivity::class.java)
    }


}