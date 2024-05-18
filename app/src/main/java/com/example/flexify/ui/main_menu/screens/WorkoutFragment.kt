package com.example.flexify.ui.main_menu.screens

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.flexify.R
import com.example.flexify.databinding.FragmentWorkoutBinding
import dagger.android.support.AndroidSupportInjection
import java.util.Locale
import javax.inject.Inject

class WorkoutFragment : Fragment() {


    private lateinit var binding: FragmentWorkoutBinding
    private val args: WorkoutFragmentArgs by navArgs()

    private var countDownTimer: CountDownTimer? = null
    private var restCountDownTimer: CountDownTimer? = null
    private var totalTimeCountDownTimer: CountDownTimer? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val workoutViewModel by createViewModelLazy(
        WorkoutViewModel::class,
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
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workoutViewModel.setWorkout(args.workout.toList())

        binding.startButton.setOnClickListener {
            binding.startButton.visibility = View.GONE
            startWorkout()
        }

        binding.completeButton.setOnClickListener {
            completeExercise()
        }

        binding.skipButton.setOnClickListener {
            skipExercise()
        }

        binding.addRestTimeButton.setOnClickListener {
            addRestTime()
        }

        binding.skipRestTimeButton.setOnClickListener {
            skipRestTime()
        }

        workoutViewModel.currentExerciseIndex.observe(viewLifecycleOwner) {
            updateUI()
        }

        workoutViewModel.totalElapsedTime.observe(viewLifecycleOwner) {
            updateElapsedTime()
        }

        workoutViewModel.workoutFinished.observe(viewLifecycleOwner) { finished ->
            if (finished) {
                // Perform any actions needed when workout is finished
                binding.timerTextView.text = getString(R.string.workout_complete)
            }
        }

        updateUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startWorkout() {
        workoutViewModel.setRestTime(10000) // 10 seconds rest time
        workoutViewModel.addElapsedTime(0)
        startTotalTimeTimer()
        updateUI()
        startExercise()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startExercise() {
        val currentExerciseIndex = workoutViewModel.currentExerciseIndex.value ?: 0
        if (currentExerciseIndex < workoutViewModel.workout.value?.size ?: 0) {
            val exercise = workoutViewModel.workout.value?.get(currentExerciseIndex)
            binding.completeButton.isEnabled = true
            binding.skipButton.isEnabled = true
            binding.addRestTimeButton.visibility = View.GONE
            binding.skipRestTimeButton.visibility = View.GONE
            countDownTimer?.cancel()
            restCountDownTimer?.cancel()
            updateUI()
        } else {
            finishWorkout()
        }
    }

    private fun completeExercise() {
        val currentExerciseIndex = workoutViewModel.currentExerciseIndex.value ?: 0
        if (currentExerciseIndex < workoutViewModel.workout.value?.size ?: 0) {
            workoutViewModel.workout.value?.get(currentExerciseIndex)?.isCompleted = true
            workoutViewModel.incrementExerciseIndex()
            restBeforeNextExercise()
        }
    }

    private fun skipExercise() {
        val currentExerciseIndex = workoutViewModel.currentExerciseIndex.value ?: 0
        if (currentExerciseIndex < workoutViewModel.workout.value?.size ?: 0) {
            workoutViewModel.workout.value?.get(currentExerciseIndex)?.isSkipped = true
            workoutViewModel.incrementExerciseIndex()
            restBeforeNextExercise()
        }
    }

    private fun restBeforeNextExercise() {
        binding.timerTextView.visibility = View.VISIBLE
        binding.completeButton.isEnabled = false
        binding.skipButton.isEnabled = false
        binding.addRestTimeButton.visibility = View.VISIBLE
        binding.skipRestTimeButton.visibility = View.VISIBLE
        countDownTimer?.cancel()
        restCountDownTimer?.cancel()
        val restTimeInMillis = workoutViewModel.currentRestTime.value ?: 10000L // 10 seconds rest between exercises
        restCountDownTimer = object : CountDownTimer(restTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                workoutViewModel.addElapsedTime(1000) // Increment total elapsed time by 1 second
                binding.timerTextView.text = getString(R.string.rest_time_format, millisUntilFinished / 1000)
                updateElapsedTime()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                startExercise()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI() {
        val currentExerciseIndex = workoutViewModel.currentExerciseIndex.value ?: 0
        if (currentExerciseIndex < (workoutViewModel.workout.value?.size ?: 0)) {
            val exercise = workoutViewModel.workout.value?.get(currentExerciseIndex)
            binding.exerciseTextView.text = exercise?.exercise?.exercise?.name
            binding.descriptionTextView.text = exercise?.exercise?.exercise?.description
            binding.setsRepsTextView.text = getString(R.string.sets_reps_format, exercise?.sets, exercise?.reps)
            binding.weightTextView.visibility = if (exercise?.weight != null) View.VISIBLE else View.GONE
            binding.weightTextView.text = exercise?.weight?.let { getString(R.string.weight_format, it) } ?: ""

            Glide.with(this)
                .load(exercise?.exercise?.exercise?.urlGif)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageView)
        } else {
            finishWorkout()
        }
    }

    private fun updateElapsedTime() {
        binding.totalTimeTextView.text = getString(R.string.elapsed_time_format, formatElapsedTime(workoutViewModel.totalElapsedTime.value ?: 0))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun finishWorkout() {
        binding.completeButton.isEnabled = false
        binding.skipButton.isEnabled = false
        binding.addRestTimeButton.isEnabled = false
        binding.skipRestTimeButton.isEnabled = false
        countDownTimer?.cancel()
        restCountDownTimer?.cancel()
        totalTimeCountDownTimer?.cancel()
        workoutViewModel.markWorkoutAsFinished()

        if(!workoutViewModel.workoutFinished.value!!){
            val action = WorkoutFragmentDirections.actionWorkoutFragmentToFragmentCongratulations()
            findNavController().navigate(action)
        }
    }

    private fun addRestTime() {
        restCountDownTimer?.cancel()
        val currentRestTimeInMillis = workoutViewModel.currentRestTime.value ?: 0
        workoutViewModel.addRestTime(10000) // Add 10 seconds
        val newRestTimeInMillis = currentRestTimeInMillis + 10000 // Add 10 seconds
        restCountDownTimer = object : CountDownTimer(newRestTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                workoutViewModel.addElapsedTime(1000) // Increment total elapsed time by 1 second
                binding.timerTextView.text = getString(R.string.rest_time_format, millisUntilFinished / 1000)
                updateElapsedTime()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                startExercise()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun skipRestTime() {
        restCountDownTimer?.cancel()
        binding.timerTextView.visibility = View.GONE
        startExercise()
    }

    private fun startTotalTimeTimer() {
        totalTimeCountDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                workoutViewModel.addElapsedTime(1000)
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun formatElapsedTime(elapsedTime: Long): String {
        val seconds = (elapsedTime / 1000) % 60
        val minutes = (elapsedTime / 1000) / 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        restCountDownTimer?.cancel()
        totalTimeCountDownTimer?.cancel()
    }
}
