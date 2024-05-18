package com.example.flexify.ui.main_menu.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flexify.databinding.FragmentTrainingLayoutGroupBinding

class FragmentTrainingLayoutGroup: Fragment() {
    private lateinit var binding:FragmentTrainingLayoutGroupBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingLayoutGroupBinding.inflate(inflater, container, false)
        return binding.root
    }
}