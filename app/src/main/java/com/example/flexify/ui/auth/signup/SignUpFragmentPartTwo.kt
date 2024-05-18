package com.example.flexify.ui.auth.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flexify.R
import com.example.flexify.data.ResponseModel.ResponseStates
import com.example.flexify.data.domain.SignUp
import com.example.flexify.databinding.FragmentSignupPartTwoBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpFragmentPartTwo:Fragment() {
    private lateinit var binding:FragmentSignupPartTwoBinding
    private val args: SignUpFragmentPartTwoArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        SignUpViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onResume() {
        super.onResume()
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,gender)
        binding.autoComplete.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupPartTwoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.heightET.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                hideKeyboard()
                if(checkFields()){navigateToSignIn()}
                else{
                    exception()
                }
                true
            } else {
                false
            }
        }


        binding.btnFinishReg.setOnClickListener{
            if(checkFields()){
                navigateToSignIn()
            }
            else{
                exception()
            }
        }
    }

    private fun checkFields():Boolean{
        return !binding.autoComplete.text.isNullOrEmpty() && binding.autoComplete.text.toString() != "Выберите пол" &&
                !binding.weightET.text.isNullOrEmpty() && !binding.heightET.text.isNullOrEmpty() && !binding.ageET.text.isNullOrEmpty()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun navigateToSignIn()
    {
        val signUp = SignUp(
            email = args.email,
            password = args.password,
            fullName = args.fullName,
            age = binding.ageET.text.toString().toInt(),
            weight = binding.weightET.text.toString().toFloat(),
            height = binding.heightET.text.toString().toFloat(),
            sex = binding.autoComplete.text.toString()
        )
        viewModel.createUser(signUp)

        viewModel.signupLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is ResponseStates.Success->{
                    val action = SignUpFragmentPartTwoDirections.actionSignUpFragmentPartTwoToSuccessRegistrationFragment()
                    findNavController().navigate(action)
                    setDefault()
                }
                is ResponseStates.Failure->{
                    result.e.message?.let {
                        exception(it)
                    }
                    setDefault()
                }
                is ResponseStates.Loading ->{
                    setStateLoading()
                }
            }
        }
    }

    private fun exception()
    {
        Snackbar.make(
            binding.root,
            getString(R.string.not_all_fields_are_filled_in),
            Snackbar.LENGTH_SHORT
        ).show()
    }
    private fun exception(exc:String)
    {
        Log.i("ACHTUNG",exc)
        Snackbar.make(
            binding.root,
            exc,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setStateLoading(){
        binding.btnFinishReg.text=""
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setDefault(){
        binding.btnFinishReg.text = context?.resources?.getString(R.string.finishReg)
        binding.progressBar.visibility = View.GONE
    }
}