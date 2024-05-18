package com.example.flexify.ui.info.Exercise

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.flexify.databinding.FragmentExerciseDetailsBinding
import com.google.android.material.chip.Chip
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ExerciseDetailsFragment:Fragment() {
    private lateinit var binding: FragmentExerciseDetailsBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        ExerciseDetailsViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exerciseId = ExerciseDetailsFragmentArgs.fromBundle(requireArguments()).exerciseId
        viewModel.loadExerciseDetails(exerciseId)

        viewModel.exerciseWithType.observe(viewLifecycleOwner) { exerciseWithType ->
            binding.exerciseName.text = exerciseWithType.exercise.name
            binding.exerciseDescription.text = exerciseWithType.exercise.description
            binding.exerciseInstruction.text = exerciseWithType.exercise.instruction.replace(";","\n")
            binding.exerciseMedicalContraindications.text =
                exerciseWithType.exercise.medicalContraindications.replace(";","\n")


            exerciseWithType.exercisesType.forEach { type ->
                val chip = Chip(context)
                chip.text = type.name
                binding.chipGroup.addView(chip)
            }


            exerciseWithType.exercise.urlGif?.let { url ->
                Glide.with(this).load(url).into(binding.exerciseGif)
            }
        }
    }
}