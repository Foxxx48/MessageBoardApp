package com.foxxx.messageboardapp.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxxx.messageboardapp.EditAdsActivity
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.databinding.FragmentListImagesBinding
import com.foxxx.messageboardapp.fragments.selectimageadapter.SelectImageRvAdapter
import com.foxxx.messageboardapp.utils.ImageManager
import com.foxxx.messageboardapp.utils.ImagePicker
import com.foxxx.messageboardapp.utils.ItemTouchMoveCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ListImagesFragment(
    private val fragmentCloseInterface: FragmentCloseInterface,
    private val newList : ArrayList<Uri>?
) : Fragment() {

    val adapter = SelectImageRvAdapter()
    val dragCallback = ItemTouchMoveCallBack(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    private var job : Job? = null

    private var _binding: FragmentListImagesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentListImagesBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListImagesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        touchHelper.attachToRecyclerView(binding.rcViewSelectImage)
        binding.rcViewSelectImage.layoutManager = LinearLayoutManager(activity)
        binding.rcViewSelectImage.adapter = adapter
        if (newList != null){
            job = CoroutineScope(Dispatchers.Main).launch {
                val bitmapList = ImageManager.imageResize(newList, requireActivity())
                adapter.updateAdapter(bitmapList, true)
            }
        }
    }
    fun updateAdapterFromEdit(bitmapList : List<Bitmap>){
        adapter.updateAdapter(bitmapList, true)
    }

    private fun setUpToolbar(){
        binding.tb.inflateMenu(R.menu.menu_choose_image)
        val deleteItem = binding.tb.menu.findItem(R.id.id_delete_image)
        val addImageItem = binding.tb.menu.findItem(R.id.id_add_image)
        if(adapter.mainArray.size > 2) addImageItem?.isVisible = false

        binding.tb.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            Log.d("MyLog", "Home")
        }

        deleteItem.setOnMenuItemClickListener {
            adapter.updateAdapter(ArrayList(), true)
            Log.d("MyLog", "Delete Item")
            true
        }

        addImageItem.setOnMenuItemClickListener {
            val imageCount = ImagePicker.MAX_IMAGE_COUNT - adapter.mainArray.size
            ImagePicker.pixLauncher(activity as EditAdsActivity, imageCount)
            Log.d("MyLog", "Add Item")
            true
        }
    }
    fun updateAdapter(newList: ArrayList<Uri>){
        job = CoroutineScope(Dispatchers.Main).launch {
            val bitmapList = ImageManager.imageResize(newList, requireActivity())
            adapter.updateAdapter(bitmapList, false)
        }
    }
    fun setSingleImage(uri : Uri, position : Int){
        val pBar = binding.rcViewSelectImage[position].findViewById<ProgressBar>(R.id.pBar)
        job = CoroutineScope(Dispatchers.Main).launch {
            pBar.visibility = View.VISIBLE
            val bitmapList = ImageManager.imageResize(arrayListOf(uri), requireActivity())
            pBar.visibility = View.GONE
            adapter.mainArray[position] = bitmapList[0]
            adapter.notifyItemChanged(position)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onDetach() {
        super.onDetach()
        fragmentCloseInterface.onFragmentClose(adapter.mainArray)
        job?.cancel()
    }
}