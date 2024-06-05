package com.example.flexify.ui.Dishes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flexify.databinding.FragmentExerciseDetailsBinding
import com.example.flexify.databinding.FragmentListTypeExBinding
import com.example.flexify.ui.info.Exercise.ExercisesFragmentDirections
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DishesFragmentList: Fragment() {
    private lateinit var binding: FragmentListTypeExBinding
    private lateinit var dishesAdapter: DishesAdapter


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
        binding = FragmentListTypeExBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dishesAdapter = DishesAdapter { dish->
            val action = DishesFragmentListDirections.actionDishesFragmentToDishesFragmentDetails(dish.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = dishesAdapter
        }

        viewModel.dishes.observe(viewLifecycleOwner) { dish ->
            dishesAdapter.submitList(dish)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchExercises(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchExercises(it) }
                return false
            }
        })
    }
}