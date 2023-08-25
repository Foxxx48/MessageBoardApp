package com.foxxx.messageboardapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foxxx.messageboardapp.databinding.ActivityEditAdsBinding

class EditAdsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEditAdsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ads)
    }

    companion object {
        fun newIntentEditAdsActivity(context: Context)=
            Intent(context, EditAdsActivity::class.java)
    }
}