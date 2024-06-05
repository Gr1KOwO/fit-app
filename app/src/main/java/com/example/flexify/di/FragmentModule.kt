package com.example.flexify.di


import com.example.flexify.ui.Dishes.DishesFragment
import com.example.flexify.ui.Dishes.DishesFragmentList
import com.example.flexify.ui.auth.signin.SignInFragment
import com.example.flexify.ui.auth.signup.SignUpFragment
import com.example.flexify.ui.auth.signup.SignUpFragmentPartTwo
import com.example.flexify.ui.auth.signup.SuccessRegistrationFragment
import com.example.flexify.ui.boarding.screens.FinalFrame
import com.example.flexify.ui.boarding.screens.FirstFrame
import com.example.flexify.ui.boarding.screens.SecondFrame
import com.example.flexify.ui.boarding.screens.ThirdFrame
import com.example.flexify.ui.info.Exercise.ExerciseDetailsFragment
import com.example.flexify.ui.info.Exercise.ExercisesFragment
import com.example.flexify.ui.info.Food.FragmentFood
import com.example.flexify.ui.info.Food.FragmentTypeFood
import com.example.flexify.ui.main_menu.screens.FragmentConsumedFood
import com.example.flexify.ui.info.FragmentInfo
import com.example.flexify.ui.main_menu.MainMenuFragment
import com.example.flexify.ui.main_menu.screens.DesktopFragment
import com.example.flexify.ui.main_menu.screens.FragmentCongratulations
import com.example.flexify.ui.main_menu.screens.FragmentTraining
import com.example.flexify.ui.main_menu.screens.FragmentTrainingLayoutGroup
import com.example.flexify.ui.main_menu.screens.WorkoutFragment
import com.example.flexify.ui.profile.FragmentProfile
import com.example.flexify.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun signInFragmentFragment(): SignInFragment
    @ContributesAndroidInjector
    abstract fun finalFrame(): FinalFrame
    @ContributesAndroidInjector
    abstract  fun firstFrame(): FirstFrame
    @ContributesAndroidInjector
    abstract fun secondFrame(): SecondFrame
    @ContributesAndroidInjector
    abstract fun thirdFrame(): ThirdFrame
    @ContributesAndroidInjector
    abstract fun splashFragment(): SplashFragment
    @ContributesAndroidInjector
    abstract fun signUpFragment():SignUpFragment
    @ContributesAndroidInjector
    abstract fun signUpFragmentPartTwo():SignUpFragmentPartTwo
    @ContributesAndroidInjector
    abstract fun successRegistrationFragment():SuccessRegistrationFragment
    @ContributesAndroidInjector
    abstract fun mainMenuFragment(): MainMenuFragment
    @ContributesAndroidInjector
    abstract fun fragmentConsumedFood(): FragmentConsumedFood
    @ContributesAndroidInjector
    abstract fun fragmentProfile(): FragmentProfile
    @ContributesAndroidInjector
    abstract fun fragmentInfo(): FragmentInfo
    @ContributesAndroidInjector
    abstract fun desktopFragment():DesktopFragment
    @ContributesAndroidInjector
    abstract fun fragmentTraining():FragmentTraining
    @ContributesAndroidInjector
    abstract fun dishesFragment():DishesFragment
    @ContributesAndroidInjector
    abstract fun exercisesFragment(): ExercisesFragment
    @ContributesAndroidInjector
    abstract fun fragmentFood(): FragmentFood
    @ContributesAndroidInjector
    abstract fun fragmentTypeFood(): FragmentTypeFood
    @ContributesAndroidInjector
    abstract fun exerciseDetailsFragment (): ExerciseDetailsFragment
    @ContributesAndroidInjector
    abstract fun workoutFragment(): WorkoutFragment
    @ContributesAndroidInjector
    abstract fun fragmentTrainingLayoutGroup():FragmentTrainingLayoutGroup
    @ContributesAndroidInjector
    abstract fun fragmentCongratulations(): FragmentCongratulations
    @ContributesAndroidInjector
    abstract fun DishesFragmentList(): DishesFragmentList
}