package com.foxxx.messageboardapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.Locale

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

    fun showCities(context: Context, country: String): List<String> {
        val tempArray = ArrayList<String>()
        val jsonObject = getJSONObject(context, "countriesToCities.json")
        val cities = jsonObject[country] as JSONArray
        if (cities != null) {
            for (n in 0 until cities.length()) {
                tempArray.add(cities.getString(n))
            }
        }
        Log.d("CityHelper", "cities: $cities")
        return tempArray
    }

    private fun getJSONObject(context: Context, fileName: String): JSONObject {
        var jsonFile = "Empty"
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val bytesArray = ByteArray(size)
            inputStream.read(bytesArray)
            jsonFile = String(bytesArray)

        } catch (e: IOException) {
            Log.d("CityHelper", "IOException: ${e.message}")
        } catch (j: JSONException) {
            Log.d("CityHelper", "JSONException: ${j.message}")
        }
        return JSONObject(jsonFile)
    }

    fun filterListData(list: List<String>, searchText: String?): ArrayList<String> {
        val tempList = arrayListOf<String>()
        tempList.clear()
        if (searchText == null) {
            tempList.add("No result")
            return tempList
        }
        for (searched: String in list) {
            if (searched.lowercase(Locale.ROOT).startsWith(searchText.lowercase(Locale.ROOT))) {
                tempList.add(searched)
            }
        }
        if (tempList.size == 0) tempList.add("No Result")
        return tempList
    }
}