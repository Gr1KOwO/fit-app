package com.example.flexify.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flexify.R
import com.example.flexify.data.repository.PreferenceStorage
import com.example.flexify.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FragmentProfile:Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        ProfileViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    private lateinit var binding:FragmentProfileBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        // Получить данные пользователя при создании фрагмента
        viewModel.getUser()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            // Обновление UI с данными пользователя
            user?.let {
                binding.ProfileFullName.text = it.fullName
                binding.idHeightText.text = "${it.height}cm"
                binding.idWeightText.text = "${it.weight}kg"
                binding.idAgeText.text = "${it.age}yo"
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show()
        })

        binding.edit.setOnClickListener {
            if (areFieldsFilled()) {
                editUser()
            } else {
                Toast.makeText(context, "Заполните хотя бы одно поле для редактирования", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnExit.setOnClickListener {
            viewModel.signOut()
            context?.let { PreferenceStorage(it).userToken = ""}

            navigateToSignIn()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.mainMenuFragment)
        }
    }
    private fun areFieldsFilled(): Boolean {
        return binding.ProfileFullNameET.text!!.isNotEmpty() ||
                binding.ProfileHeightET.text!!.isNotEmpty() ||
                binding.ProfileWeightEt.text!!.isNotEmpty() ||
                binding.ProfileAgeEt.text!!.isNotEmpty()
    }

    private fun editUser() {
        val currentUser = viewModel.user.value
        val newFullName = binding.ProfileFullNameET.text.toString().ifEmpty { currentUser?.fullName }
        val newHeight = binding.ProfileHeightET.text.toString().ifEmpty { currentUser?.height.toString() }
        val newWeight = binding.ProfileWeightEt.text.toString().ifEmpty { currentUser?.weight.toString() }
        val newAge = binding.ProfileAgeEt.text.toString().ifEmpty { currentUser?.age.toString() }
        val newPassword = binding.newPasswordText.text.toString().ifEmpty { null }

        val updatedUser = newFullName?.let {
            currentUser?.copy(
                fullName = it,
                height = newHeight.toFloat(),
                weight = newWeight.toFloat(),
                age = newAge.toInt()
            )
        }

        updatedUser?.let {
            viewModel.updateUser(it,newPassword)
        }
    }


    private fun navigateToSignIn() {
        val action = FragmentProfileDirections.actionFragmentProfileToSignInFragment()
        findNavController().navigate(action)
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