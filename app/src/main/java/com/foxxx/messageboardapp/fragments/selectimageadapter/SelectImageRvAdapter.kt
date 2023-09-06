package com.foxxx.messageboardapp.fragments.selectimageadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.databinding.SelectImageFragmentItemBinding

class SelectImageRvAdapter : RecyclerView.Adapter<ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = SelectImageFragmentItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.select_image_fragment_item,
                parent,
                false
            )
        )
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        TODO("Not yet implemented")
    }

}