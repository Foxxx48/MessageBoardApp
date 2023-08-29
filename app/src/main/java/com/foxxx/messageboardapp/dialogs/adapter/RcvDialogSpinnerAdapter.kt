package com.foxxx.messageboardapp.dialogs.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.databinding.SpinerDialogItemBinding

class RcvDialogSpinnerAdapter: RecyclerView.Adapter<SpViewHolder>() {
    private var onItemClickListener : OnItemClickListener? = null

    private val listItems = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val binding =
            SpinerDialogItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.spiner_dialog_item, parent, false))
        return SpViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return listItems.size
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder: ${listItems[position]}")
        val item = listItems[position]
        with(holder) {
            binding.dialogItemTextView.text = item
            binding.dialogItemTextView.setOnClickListener {
                onItemClickListener?.onClick(item)
            }

        }
    }

    fun updateAdapter(list: List<String>) {
        listItems.clear()
        listItems.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(item: String)
    }

    fun onItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}