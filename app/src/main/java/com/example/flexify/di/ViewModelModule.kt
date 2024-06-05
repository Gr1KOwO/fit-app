package com.example.flexify.di


import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flexify.ui.Dishes.DishDetailsViewModel
import com.example.flexify.ui.Dishes.DishesViewModel
import com.example.flexify.ui.auth.signin.SignInViewModel
import com.example.flexify.ui.auth.signup.SignUpViewModel
import com.example.flexify.ui.info.Exercise.ExerciseDetailsViewModel
import com.example.flexify.ui.info.Exercise.ExercisesViewModel
import com.example.flexify.ui.info.Food.FoodListViewModel
import com.example.flexify.ui.info.Food.FoodTypeViewModel
import com.example.flexify.ui.main_menu.MainMenuViewModel
import com.example.flexify.ui.main_menu.screens.ConsumedViewModel
import com.example.flexify.ui.main_menu.screens.StatisticsViewModel
import com.example.flexify.ui.main_menu.screens.TrainingViewModel
import com.example.flexify.ui.main_menu.screens.WorkoutViewModel
import com.example.flexify.ui.profile.ProfileViewModel
import com.example.flexify.ui.splash.LoadDataViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun catalogViewModel(catalogViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun signInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadDataViewModel::class)
    abstract fun loadDataViewModel(loadDataViewModel: LoadDataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun profileViewModel(profileViewModel:ProfileViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainMenuViewModel::class)
    abstract fun mainViewModel(mainViewModel:MainMenuViewModel):ViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    abstract fun statisticsViewModel(statisticsViewModel:StatisticsViewModel):ViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    @Binds
    @IntoMap
    @ViewModelKey(ConsumedViewModel::class)
    abstract fun consumedViewModel(consumedViewModel: ConsumedViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesViewModel::class)
    abstract fun exercisesViewModel(exercisesViewModel: ExercisesViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseDetailsViewModel::class)
    abstract fun exerciseDetailsViewModel(exerciseDetailsViewModel:ExerciseDetailsViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FoodTypeViewModel::class)
    abstract fun foodTypeViewModel(foodTypeViewModel:FoodTypeViewModel):ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(FoodListViewModel::class)
    abstract fun foodListViewModel(foodListViewModel: FoodListViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DishesViewModel::class)
    abstract fun dishesViewModel(dishesViewModel: DishesViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    abstract fun trainingViewModel(trainingViewModel: TrainingViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutViewModel::class)
    abstract fun workoutViewModel(workoutViewModel: WorkoutViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DishDetailsViewModel::class)
    abstract fun dishDetailsViewModel(dishDetailsViewModel:DishDetailsViewModel):ViewModel
}