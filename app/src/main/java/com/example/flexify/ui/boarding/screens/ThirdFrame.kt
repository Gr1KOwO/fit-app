package com.example.flexify.ui.boarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flexify.R
import com.example.flexify.databinding.FragmentFirstFrameBinding
import com.example.flexify.databinding.FragmentThirdFrameBinding

class ThirdFrame:Fragment() {
    private lateinit var binding: FragmentThirdFrameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdFrameBinding.inflate(inflater,container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.next.setOnClickListener {
            if (viewPager?.currentItem != 3) {
                viewPager?.currentItem = 3
            }
        }

        return binding.root
    }
}