package com.example.flexify.ui.boarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flexify.R
import com.example.flexify.databinding.FragmentSecondFrameBinding

class SecondFrame:Fragment() {
    private lateinit var binding: FragmentSecondFrameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondFrameBinding.inflate(inflater,container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.next.setOnClickListener {
            if (viewPager?.currentItem != 2) {
                viewPager?.currentItem = 2
            }
        }

        return binding.root
    }
}