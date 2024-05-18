package com.example.flexify.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flexify.R
import com.example.flexify.data.ResponseModel.ResponseStates
import com.example.flexify.databinding.FragmentSigninBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SignInFragment:Fragment() {
    private lateinit var binding: FragmentSigninBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        SignInViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyClickableAndColorSpan(binding.txtCreateAcc,binding.txtCreateAcc.text.toString(),"Зарегистрируйесь",
            R.color.purple)

        binding.enterButton.setOnClickListener {
            if(checkingForCompletedFields()){
                navigateToMainApp()
            }
            else{
                exception()
            }
        }

        binding.passwordText.setOnEditorActionListener{_, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                hideKeyboard()
                if(checkingForCompletedFields()){
                    navigateToMainApp()
                }
                else{
                    exception()
                }
                true
            } else {
                false
            }
        }
    }
    private fun applyClickableAndColorSpan(
        textView: TextView,
        text: String,
        clickableText: String,
        colorResId: Int
    ) {
        val spannableString = SpannableString(text)
        val startIndex = text.indexOf(clickableText)
        val endIndex = startIndex + clickableText.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action)
            }
        }

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(colorResId)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun checkingForCompletedFields():Boolean
    {
        return !binding.passwordText.text.isNullOrEmpty() && !binding.emailText.text.isNullOrEmpty()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
    private fun navigateToMainApp(){
        viewModel.signIn(binding.emailText.text.toString(),binding.passwordText.text.toString())
        viewModel.signInLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is ResponseStates.Loading -> {
                    setStateLoading()
                }
                is ResponseStates.Success ->{
                    val action = SignInFragmentDirections.actionSignInFragmentToMainMenuFragment()
                    findNavController().navigate(action)
                }
                is ResponseStates.Failure ->{
                    exception(result.e.message.toString())
                    setDefault()
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
        binding.progressBar.visibility = View.VISIBLE
        binding.enterButton.text=""
    }

    private fun setDefault(){
        binding.enterButton.text = context?.resources?.getString(R.string.enter)
        binding.progressBar.visibility = View.GONE
    }
}