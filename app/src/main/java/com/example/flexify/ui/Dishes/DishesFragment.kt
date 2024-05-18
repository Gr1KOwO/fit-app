package com.example.flexify.ui.Dishes

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.flexify.R
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.databinding.DishesFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DishesFragment:Fragment() {
    private lateinit var binding:DishesFragmentBinding
    private var youTubePlayer: YouTubePlayer? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        DishesViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DishesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation?.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.generateRecipeButton.setOnClickListener {
            viewModel.getRandomDish()
        }

        binding.generateNewRecipeButton.setOnClickListener {
            resetPlayer()
            viewModel.getRandomDish()
        }

        viewModel.currentDish.observe(viewLifecycleOwner, Observer { dish ->
            if (dish != null) {
                showRecipe(dish)
            } else {
                showGenerateButton()
            }
        })
    }

    private fun showRecipe(dish: Dish) {
        binding.generateRecipeButton.visibility = View.GONE
        binding.recipeLayout.visibility = View.VISIBLE
        binding.recipeNameTextView.text = dish.name
        binding.recipeDescriptionTextView.text = dish.description
        binding.recipeTextView.text = dish.recipe
        binding.recipeCaloriesTextView.text = getString(R.string.calories_format, dish.calories)

        if (dish.videoLink.isNotEmpty()) {
            binding.videoCard.visibility = View.VISIBLE
            initializePlayer(dish.videoLink)
        } else {
            binding.videoCard.visibility = View.GONE
        }
    }

    private fun showGenerateButton() {
        binding.generateRecipeButton.visibility = View.VISIBLE
        binding.recipeLayout.visibility = View.GONE
    }

    private fun initializePlayer(videoUrl: String) {
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@DishesFragment.youTubePlayer = youTubePlayer
                val videoId = extractYouTubeVideoId(videoUrl)
                videoId?.let {
                    youTubePlayer.cueVideo(it, 0f)
                }
            }
        })
    }

    private fun resetPlayer() {
        youTubePlayer?.let {
            it.pause()
            it.seekTo(0f)
            it.cueVideo("", 0f)
        }
    }

    private fun extractYouTubeVideoId(url: String): String? {
        val regex = "v=([^&]+)".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.groupValues?.get(1)
    }
}