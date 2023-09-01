package com.foxxx.messageboardapp.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream


object ImageManager {
    private const val MAX_IMAGE_SIZE = 1000
    private const val WIDTH = 0
    private const val HEIGHT = 1


    fun getImageSize(uri: Uri, activity: Activity): List<Int> {
        val inStream = activity.contentResolver.openInputStream(uri)
        val fTemp = File(activity.cacheDir, "tempImage.tmp")
        if (inStream != null) {
            fTemp.copyInStreamToFile(inStream)
        }

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(fTemp.path, options)

        return if (imageRotation(fTemp) == 90) {
            listOf(options.outHeight, options.outWidth)

        } else {
            listOf(options.outWidth, options.outHeight)
        }
    }

    private fun File.copyInStreamToFile(inputStream: InputStream) {
        this.outputStream().use { out ->
            inputStream.copyTo(out)
        }

    }

    private fun imageRotation(imageFile: File): Int {
        val rotation: Int
        val exif = ExifInterface(imageFile.absolutePath)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        rotation = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> {
                0
            }
        }
        return rotation
    }

    fun chooseScaleType(image: ImageView, bitmap: Bitmap) {
        if (bitmap.width > bitmap.height) {
            image.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            image.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }

    suspend fun imageResize(uries: List<Uri>, activity: Activity): List<Bitmap> = withContext(Dispatchers.IO) {
        val tempList = ArrayList<List<Int>>()
        val bitmapList = ArrayList<Bitmap>()
        for(n in uries.indices) {

            val size = getImageSize(uries[n], activity)
            val imageRatio = size[WIDTH].toFloat() / size[HEIGHT].toFloat()

            if(imageRatio > 1) {
                if(size[WIDTH] > MAX_IMAGE_SIZE) {
                    tempList.add(listOf(MAX_IMAGE_SIZE, MAX_IMAGE_SIZE / imageRatio.toInt()))
                } else {
                    tempList.add(listOf(size[WIDTH], size[HEIGHT]))
                }
            } else {
                if(size[HEIGHT] > MAX_IMAGE_SIZE) {
                    tempList.add(listOf( MAX_IMAGE_SIZE * imageRatio.toInt(), MAX_IMAGE_SIZE,))
                } else {
                    tempList.add(listOf(size[WIDTH], size[HEIGHT]))
                }
            }

        }

        for (i in uries.indices) {
            kotlin.runCatching {
                bitmapList.add(Picasso.get().load(uries[i]).resize(tempList[i][WIDTH], tempList[i][HEIGHT]).get())

            }
        }
        return@withContext bitmapList
    }

}