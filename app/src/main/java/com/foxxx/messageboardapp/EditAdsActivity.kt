package com.foxxx.messageboardapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foxxx.messageboardapp.databinding.ActivityEditAdsBinding
import com.foxxx.messageboardapp.dialogs.DialogSpinner
import com.foxxx.messageboardapp.utils.CityHelper

class EditAdsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEditAdsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val listCountries = CityHelper.getAllCountries(this)
        val dialog = DialogSpinner()
        dialog.showSpinnerDialog(this, listCountries)


    }

    companion object {
        fun newIntentEditAdsActivity(context: Context) =
            Intent(context, EditAdsActivity::class.java)
    }
}