package com.example.flexify.ui.boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flexify.databinding.FragmentViewPagerBinding
import com.example.flexify.ui.boarding.screens.FinalFrame
import com.example.flexify.ui.boarding.screens.FirstFrame
import com.example.flexify.ui.boarding.screens.SecondFrame
import com.example.flexify.ui.boarding.screens.ThirdFrame


class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding
    private val firstFrame: FirstFrame by lazy { FirstFrame() }
    private val secondFrame: SecondFrame by lazy { SecondFrame() }
    private val thirdFrame: ThirdFrame by lazy { ThirdFrame() }
    private val finalFrame: FinalFrame by lazy { FinalFrame() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater,container,false)

        val fragmentList = arrayListOf(
            firstFrame,
            secondFrame,
            thirdFrame,
            finalFrame
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        return binding.root
    }
}