package com.foxxx.messageboardapp.utils

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import com.foxxx.messageboardapp.EditAdsActivity
import com.foxxx.messageboardapp.R
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object ImagePicker {

    const val MAX_IMAGE_COUNT = 3
    const val REQUEST_CODE_GET_IMAGES = 999
    const val REQUEST_CODE_GET_SINGLE_IMAGE = 998

    private fun getOptions(imageCount: Int): Options {
        val options = Options().apply {
            ratio = Ratio.RATIO_AUTO
            count = imageCount
            path = "/Pix/Camera"
            isFrontFacing = false
            mode = Mode.Picture
        }
        return options
    }

    fun pixLauncher(activity: EditAdsActivity, imageCount: Int) {
        activity.addPixToActivity(R.id.place_holder, getOptions(imageCount)) { result ->
            when (result.status) {


                PixEventCallback.Status.SUCCESS -> {
                    getMultiSelectImages(activity, result.data)
                    closePixFragment(activity)
                    Log.d("pixLauncher", "${result.status.name}")
                }

                PixEventCallback.Status.BACK_PRESSED -> {
                    Log.d("pixLauncher", "${result.status.name}")
                }

                else -> {
                    Log.d("pixLauncher", "${result.status.name}")
                }
            }
        }
    }

    private fun openChooseImageFragment(activity: EditAdsActivity) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.place_holder, activity.chooseImageFragment!!).commit()

    }

    fun closePixFragment(activity: EditAdsActivity) {
        val fragmentList = activity.supportFragmentManager.fragments
        fragmentList.forEach {
            if (it.isVisible) {
                activity.supportFragmentManager.beginTransaction().remove(it).commit()
            }
        }
    }

    fun getMultiSelectImages(activity: EditAdsActivity, uris: List<Uri>) {
        if (uris.size > 1 && activity.chooseImageFragment == null) {
            activity.openChooseItemFragment(uris as ArrayList<Uri>)

        } else if (uris.size == 1 && activity.chooseImageFragment == null) {

            CoroutineScope(Dispatchers.Main).launch {
                activity.binding.pBarLoading.visibility = View.VISIBLE
                val bitmapArray =
                    ImageManager.imageResize(uris as ArrayList<Uri>, activity) as ArrayList<Bitmap>
                activity.binding.pBarLoading.visibility = View.GONE
                activity.imageAdapter.update(bitmapArray)
                closePixFragment(activity)
            }
        }
    }

    fun addImages(activity: EditAdsActivity, imageCounter: Int) {
        activity.addPixToActivity(R.id.place_holder, getOptions(imageCounter)) { result ->
            when (result.status) {
                PixEventCallback.Status.SUCCESS -> {
                    openChooseImageFragment(activity)
                    activity.chooseImageFragment?.updateAdapter(
                        result.data as ArrayList<Uri>
                    )
                    Log.d("addImages", "${result.status.name}")
                }

                PixEventCallback.Status.BACK_PRESSED -> {
                    Log.d("addImages", "${result.status.name}")
                }

                else -> {
                    Log.d("addImages", "${result.status.name}")
                }
            }
        }
    }

    fun getSingleImage(activity: EditAdsActivity) {
        activity.addPixToActivity(R.id.place_holder, getOptions(1)) { result ->
            when (result.status) {
                PixEventCallback.Status.SUCCESS -> {
                    openChooseImageFragment(activity)
                    singleImage(activity, result.data[0])
                    Log.d("getSingleImage", "${result.status.name}")
                }

                PixEventCallback.Status.BACK_PRESSED -> {
                    Log.d("getSingleImage", "${result.status.name}")
                }

                else -> {
                    Log.d("getSingleImage", "${result.status.name}")
                }
            }
        }
    }

    fun singleImage(activity: EditAdsActivity, uri: Uri) {
        activity.chooseImageFragment?.setSingleImage(uri, activity.editImagePos)
    }
}