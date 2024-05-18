package com.example.flexify.ui.boarding.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.flexify.R
import com.example.flexify.databinding.FinalFrameBinding
import com.example.flexify.databinding.FragmentFirstFrameBinding

class FinalFrame:Fragment() {
    private lateinit var binding: FinalFrameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FinalFrameBinding.inflate(inflater,container, false)
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_signInFragment)
            onBoardingDone()
        }
        return binding.root
    }

    private fun onBoardingDone(){
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("done",true)
        editor.apply()
    }
}