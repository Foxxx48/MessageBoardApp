package com.foxxx.messageboardapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foxxx.messageboardapp.databinding.FragmentListImagesBinding


class ListImagesFragment : Fragment() {
//    private val binding by lazy {
//        FragmentListImagesBinding.inflate(layoutInflater)
//
//    }


    private var _binding: FragmentListImagesBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentListImagesBinding is null")


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        binding.tvParam1.text = arguments?.getString(ARG_PARAM1)
        binding.tvParam2.text = arguments?.getString(ARG_PARAM2)
    }

    companion object {

        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListImagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}