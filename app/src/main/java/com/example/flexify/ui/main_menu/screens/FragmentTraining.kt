package com.example.flexify.ui.main_menu.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.flexify.R
import com.example.flexify.data.dbModel.ExerciseInWorkoutEntity
import com.example.flexify.data.dbModel.ExerciseType
import com.example.flexify.data.dbModel.ExerciseWithType
import com.example.flexify.data.dbModel.FitnessLevel
import com.example.flexify.databinding.TrainingFragmentBinding
import com.google.android.material.chip.Chip
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FragmentTraining: Fragment() {
    private lateinit var binding: TrainingFragmentBinding
    private val selectedTypes = mutableListOf<ExerciseType>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        TrainingViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TrainingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fitnessLevels.observe(viewLifecycleOwner, Observer { levels ->
            setupFitnessLevelDropdown(levels)
        })

        viewModel.exerciseTypes.observe(viewLifecycleOwner, Observer { types ->
            setupExerciseTypeChips(types)
        })

        viewModel.generatedWorkout.observe(viewLifecycleOwner, Observer { workout ->
            navigateToWorkoutScreen(workout)
        })

        binding.getTraining.setOnClickListener {
            val selectedLevel = binding.autoComplete.text.toString()
            val level = viewModel.fitnessLevels.value?.find { it.name == selectedLevel }
            if (level != null) {
                viewModel.generateWorkout(level, selectedTypes)
            }
        }

        viewModel.loadFitnessLevels()
        viewModel.loadExerciseTypes()
    }

    private fun setupFitnessLevelDropdown(levels: List<FitnessLevel>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, levels.map { it.name })
        binding.autoComplete.setAdapter(adapter)
    }

    private fun setupExerciseTypeChips(types: List<ExerciseType>) {
        binding.chipGroup.removeAllViews()
        types.forEach { type ->
            val chip = LayoutInflater.from(requireContext()).inflate(R.layout.chip_item, binding.chipGroup, false) as Chip
            chip.text = type.name
            chip.isChecked = selectedTypes.contains(type)
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                onChipCheckedChanged(buttonView, isChecked, type)
            }
            binding.chipGroup.addView(chip)
        }
    }

    private fun onChipCheckedChanged(buttonView: CompoundButton, isChecked: Boolean, type: ExerciseType) {
        if (isChecked) {
            selectedTypes.add(type)
        } else {
            selectedTypes.remove(type)
        }
    }
    private fun navigateToWorkoutScreen(workout: List<ExerciseInWorkoutEntity>) {
        val navController = findNavController()
        val action = FragmentTrainingDirections.actionFragmentTrainingToWorkoutFragment(workout.toTypedArray())
        navController.navigate(action)
    }
}