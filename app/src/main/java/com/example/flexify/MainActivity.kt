package com.example.flexify

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.flexify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        setupWithNavController(binding.bottomNav,navController)
        setContentView(binding.root)


        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            navController.popBackStack()
            when (item.itemId) {
                R.id.mainMenuFragment -> {
                    navController.navigate(item.itemId)
                    true
                }
                R.id.dishesFragment -> {
                    navController.navigate(item.itemId)
                    true
                }
                R.id.fragmentProfile->{
                    navController.navigate(item.itemId)
                    true
                }
                R.id.fragmentInfo->{
                    navController.navigate(item.itemId)
                    true
                }
                else -> false
            }
        }
    }
}