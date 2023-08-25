package com.foxxx.messageboardapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object CityHelper {
    @SuppressLint("SuspiciousIndentation")
    fun getAllCountries(context: Context): ArrayList<String> {
        val tempArray = ArrayList<String>()

        try {
            val inputStream = context.assets.open("countriesToCities.json")
            val size = inputStream.available()
            val bytesArray = ByteArray(size)
            inputStream.read(bytesArray)
            val jsonFile = String(bytesArray)
            val jsonObject = JSONObject(jsonFile)
            val countriesNames = jsonObject.names()
            if (countriesNames != null) {
                for (n in 0 until countriesNames.length()) {
                    tempArray.add(countriesNames.getString(n))
                }
            }
        } catch (e: IOException) {
            Log.d("CityHelper", "IOException: ${e.message}")
        } catch (j: JSONException) {
            Log.d("CityHelper", "JSONException: ${j.message}")
        }

        return tempArray
    }
}