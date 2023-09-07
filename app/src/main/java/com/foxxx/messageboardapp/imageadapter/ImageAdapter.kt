package com.foxxx.messageboardapp.imageadapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.databinding.ImageAdapterItemBinding

class ImageAdapter: RecyclerView.Adapter<ImageHolder>() {

    val mainArray = ArrayList<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding =
            ImageAdapterItemBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.spiner_dialog_item, parent, false)
            )
        return ImageHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val bitmap = mainArray[position]
        with(holder.binding) {
            imItem.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }



    fun update(newList : ArrayList<Bitmap>){
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}