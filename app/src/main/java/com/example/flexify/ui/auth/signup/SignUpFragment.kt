package com.example.flexify.ui.auth.signup

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flexify.R
import com.example.flexify.databinding.FragmentSignupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment: Fragment() {

    private lateinit var binding:FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpEmailLayout.editText?.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                binding.signUpEmailLayout.error = null
            }
        }

        binding.passwordText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                hideKeyboard()
                navigateToSignUpPartTwo()
                true
            } else {
                false
            }
        }
        applyClickableAndColorSpan(binding.txtHaveAcc,binding.txtHaveAcc.text.toString(),"Авторизуйтесь",
            R.color.purple)
        binding.registerButton.setOnClickListener {
            navigateToSignUpPartTwo()
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
                findNavController().popBackStack()
            }
        }

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(colorResId)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun checkingForCompletedFields():Boolean
    {
        return !binding.passwordText.text.isNullOrEmpty() && !binding.signUpEmailText.text.isNullOrEmpty() &&
                !binding.signupNameText.text.isNullOrEmpty() && !binding.signupLastNameText.text.isNullOrEmpty()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun navigateToSignUpPartTwo()
    {
        val isEmailValid = isEmailValid(binding.signUpEmailText.text.toString())
        if(!isEmailValid){
            binding.signUpEmailLayout.error = getString(R.string.error_input)
            return
        } else {
            binding.signUpEmailLayout.error = null
        }
        if(!checkingForCompletedFields())
        {
            Snackbar.make(
                binding.root,
                getString(R.string.not_all_fields_are_filled_in),
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }

        val fullName = "${binding.signupNameText.text} ${binding.signupLastNameText.text}"
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignUpFragmentPartTwo(
            fullName,
            binding.signUpEmailText.text.toString(),
            binding.passwordText.text.toString()
        )
        findNavController().navigate(action)
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }
}