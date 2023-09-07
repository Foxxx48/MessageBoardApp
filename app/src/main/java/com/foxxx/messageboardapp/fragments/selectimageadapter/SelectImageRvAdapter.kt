package com.foxxx.messageboardapp.fragments.selectimageadapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.databinding.SelectImageFragmentItemBinding
import com.foxxx.messageboardapp.utils.ItemTouchAdapter

class SelectImageRvAdapter : RecyclerView.Adapter<ImageHolder>(), ItemTouchAdapter {
    val mainArray = ArrayList<Bitmap>()

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
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        with(holder.binding) {
            imEditImage.setOnClickListener {

            }
            imDelete.setOnClickListener {

            }

            tvTitle.text =
                holder.itemView.context.resources.getStringArray(R.array.title_image_array)[position]
            imageContent.setImageBitmap(mainArray[position])
        }
    }

    fun updateAdapter(newList: List<Bitmap>, needClear: Boolean) {
        if (needClear) mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onMove(startPos: Int, targetPos: Int) {
        val targetItem = mainArray[targetPos]
        mainArray[targetPos] = mainArray[startPos]
        mainArray[startPos] = targetItem
        notifyItemMoved(startPos, targetPos)
    }

    override fun onClear() {
        notifyDataSetChanged()
    }

}