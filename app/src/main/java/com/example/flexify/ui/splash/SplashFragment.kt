package com.example.flexify.ui.splash
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flexify.R
import com.example.flexify.data.repository.PreferenceStorage
import com.example.flexify.databinding.FragmentSplashBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var animTop: Animation
    private lateinit var animBottom: Animation
    private lateinit var fadeIn: Animation
    private var isInternetDialogShown = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        LoadDataViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        animTop = AnimationUtils.loadAnimation(context, R.anim.from_top)
        animBottom = AnimationUtils.loadAnimation(context, R.anim.from_bottom)
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)


        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (!isLoading) {
                navigateToNextScreen()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })

        if (!isInternetAvailable()) {
            if (checkUserToken() != true) {
                showWifiEnableDialog()
                return binding.root
            }
        }
        startAnimationsAndLoadData()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        if (isInternetDialogShown) {
            if (isInternetAvailable()) {
                isInternetDialogShown = false
                startAnimationsAndLoadData()
            } else {
                showWifiEnableDialog()
            }
        }
    }

    private fun startAnimationsAndLoadData() {
        binding.title.animation = animTop
        binding.subtitle.animation = animBottom
        animTop.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.yTextView.animation = fadeIn

                if (isDayModeEnabled()) {
                    applyTextGradient()
                } else {
                    binding.yTextView.setTextColor(
                        ContextCompat.getColor(requireContext(), android.R.color.white)
                    )
                }
                binding.yTextView.visibility = View.VISIBLE

                viewModel.loadData()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        binding.title.startAnimation(animTop)
        binding.subtitle.startAnimation(animBottom)
    }

    private fun navigateToNextScreen() {
        if (!onBoardingDone()) {
            val action = SplashFragmentDirections.actionSplashFragmentToViewPagerFragment()
            findNavController().navigate(action)
        } else if (checkUserToken() == true) {
            val action = SplashFragmentDirections.actionSplashFragmentToMainMenuFragment2()
            findNavController().navigate(action)
        } else {
            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            findNavController().navigate(action)
        }
    }

    private fun applyTextGradient() {
        val paint = binding.yTextView.paint
        val width = paint.measureText(binding.yTextView.text.toString())
        val textShader: Shader = LinearGradient(
            0f, 0f, width, binding.yTextView.textSize, intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.newColorTitle_Y_secondPArt),
                ContextCompat.getColor(requireContext(), R.color.backgroundNightFirstPart),
            ), null, Shader.TileMode.CLAMP
        )
        binding.yTextView.paint.shader = textShader
    }

    private fun isDayModeEnabled(): Boolean {
        val mode = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        return mode == Configuration.UI_MODE_NIGHT_NO
    }

    private fun onBoardingDone(): Boolean {
        val sharedPreferences =
            requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("done", false)
    }

    private fun checkUserToken(): Boolean? {
        return context?.let { PreferenceStorage(it).userToken.isNotEmpty() }
    }

    private fun showWifiEnableDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Отсутствует подключение к интернету")
            .setMessage("Для продолжения работы приложения необходимо включить Wi-Fi или мобильные данные.")
            .setPositiveButton("Настройки") { dialog, _ ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                dialog.dismiss()
                isInternetDialogShown = true
            }
            .setNegativeButton("Выход") { dialog, _ ->
                requireActivity().finish()
            }
            .setCancelable(false)
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}