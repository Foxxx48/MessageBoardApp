package com.foxxx.messageboardapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foxxx.messageboardapp.databinding.FragmentListImagesBinding


class ListImagesFragment(
    private val fragmentCloseInterface: FragmentCloseInterface
) : Fragment() {

    private var _binding: FragmentListImagesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentListImagesBinding is null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListImagesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentCloseInterface.onFragmentClose()
    }
}