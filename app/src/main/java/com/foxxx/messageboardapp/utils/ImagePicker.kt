package com.foxxx.messageboardapp.utils

import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.foxxx.messageboardapp.R
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio


object ImagePicker {

    const val MAX_IMAGE_COUNT = 3
    const val REQUEST_CODE_GET_IMAGES = 999
    const val REQUEST_CODE_GET_SINGLE_IMAGE = 998

    fun getOptions(imageCount: Int): Options {
        val options = Options().apply {
            ratio = Ratio.RATIO_AUTO                                   //Image/video capture ratio
            count = imageCount                                                   //Number of images to restrict selection count
            spanCount = 4                                               //Number for columns in grid
            path = "Pix/Camera"                                         //Custom Path For media Storage
            isFrontFacing = false                                       //Front Facing camera on start
            mode = Mode.Picture                                            //Option to select only pictures or videos or both
            flash = Flash.Auto                                          //Option to select flash type
            preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
        }
        return options
    }

    fun pixLauncher(activity: AppCompatActivity, imageCount: Int) {
        activity.addPixToActivity(R.id.pixCameraContainer, getOptions(imageCount)) {
            when (it.status) {

                PixEventCallback.Status.SUCCESS -> {
                    val fragmentList = activity.supportFragmentManager.fragments
                    fragmentList.forEach {
                        if(it.isVisible) {
                            activity.supportFragmentManager.beginTransaction().remove(it).commit()
                        }
                    }
                    Log.d("pixLauncher", "${it.status.name}")
                }
                 PixEventCallback.Status.BACK_PRESSED -> {
                     Log.d("pixLauncher", "${it.status.name}")
                 }
                else -> {
                    Log.d("pixLauncher", "${it.status.name}")
                }
            }
        }

    }
}