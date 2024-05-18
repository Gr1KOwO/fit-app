package com.example.flexify.ui.main_menu

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.flexify.R

import com.example.flexify.databinding.FragmentMainMenuBinding
import com.example.flexify.ui.main_menu.screens.DesktopFragment
import com.example.flexify.ui.main_menu.screens.FragmentConsumedFood
import com.example.flexify.ui.main_menu.screens.FragmentTraining
import com.example.flexify.ui.main_menu.screens.FragmentTrainingLayoutGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainMenuFragment:Fragment() {
    private lateinit var binding:FragmentMainMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            DesktopFragment(),
            FragmentTrainingLayoutGroup(),
            FragmentConsumedFood()
        )
        binding.view.adapter = ViewPagerAdapter(fragmentList,childFragmentManager,lifecycle)
        TabLayoutMediator(binding.tabLayout,binding.view){tab,position->
            when(position){
                0->{tab.text = "Рабочий стол" }
                1->{tab.text = "Тренировка"}
                2->{tab.text= "Потребленные калории"}
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation?.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation?.visibility = View.GONE
    }
}