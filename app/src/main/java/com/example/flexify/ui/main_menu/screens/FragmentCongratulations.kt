package com.example.flexify.ui.main_menu.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flexify.R
import com.example.flexify.databinding.FragmentCongratulationsBinding

class FragmentCongratulations:Fragment() {
    private lateinit var binding:FragmentCongratulationsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratulationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.subCongratulation.text = resources.getStringArray(R.array.quotes).random()
        binding.backToMenu.setOnClickListener {
            val action = FragmentCongratulationsDirections.actionFragmentCongratulationsToFragmentTraining()
            findNavController().navigate(action)
        }
    }
}