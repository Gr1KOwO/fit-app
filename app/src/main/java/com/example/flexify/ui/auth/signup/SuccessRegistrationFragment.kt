package com.example.flexify.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flexify.databinding.FragmentSuccessRegistrationBinding

class SuccessRegistrationFragment:Fragment() {
    private lateinit var binding:FragmentSuccessRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessRegistrationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToSignIn.setOnClickListener{
            val action = SuccessRegistrationFragmentDirections.actionSuccessRegistrationFragmentToSignInFragment()
            findNavController().navigate(action)
        }
    }
}