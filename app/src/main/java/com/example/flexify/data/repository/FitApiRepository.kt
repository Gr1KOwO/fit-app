package com.example.flexify.data.repository

import android.util.Log
import com.example.flexify.data.FitApi
import com.example.flexify.data.ResponseModel.BaseResponse
import com.example.flexify.data.ResponseModel.ResponseLogin
import com.example.flexify.data.ResponseModel.ResponseResult
import com.example.flexify.data.ResponseModel.ResponseSignIn
import com.example.flexify.data.Storage.fitDB
import com.example.flexify.data.dbModel.ConsumedFoodEntity
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.dbModel.ExerciseToType
import com.example.flexify.data.dbModel.ExerciseType
import com.example.flexify.data.dbModel.ExerciseWithType
import com.example.flexify.data.dbModel.FitnessLevel
import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.dbModel.FoodType
import com.example.flexify.data.dbModel.Statistics
import com.example.flexify.data.dbModel.User
import com.example.flexify.data.dbModel.UsersWithStatistics

import com.example.flexify.data.domain.SignUp
import com.example.flexify.data.domain.toRequest
import com.example.flexify.data.requestmodel.RequestSignIn
import com.example.flexify.data.requestmodel.UserUpdateRequest
import java.time.LocalDate
import javax.inject.Inject

class FitApiRepository@Inject constructor(
    private val fitApi: FitApi,
    private val fitDB: fitDB
) {
    //Create user
    suspend fun createUser(signUp: SignUp):ResponseLogin {
        return fitApi.signup(signUp.toRequest()).data
    }

    //Auth user
    suspend fun signInUser(email: String, password: String):ResponseSignIn{
        return fitApi.signin(RequestSignIn(email,password))
    }
    suspend fun addUserInDB(user: User){
        fitDB.usersDao().addUser(user)
    }

    suspend fun getFoodType():List<FoodType>{
        return fitDB.foodTypeDao().getFoodType()
    }
    //getFoodType and save locale
    suspend fun getFoodTypeApi():List<FoodType>{
        return fitApi.getFoodType()
    }
    suspend fun insertFoodTypeList(list:List<FoodType>){
        fitDB.foodTypeDao().insertFoodType(list)
    }

    //UpdateUser data
    suspend fun updateUser(user: UserUpdateRequest){
        return fitApi.updateUser(user)
    }
    suspend fun updateLocaleUser(user:User){
        fitDB.usersDao().updateUser(user)
    }

    //Get user
    suspend fun getUser():User? {
        return fitDB.usersDao().getUser().map { it }.singleOrNull()
    }
    //signOut from accaount
    suspend fun deleteUser(user: User){
        fitDB.usersDao().deleteUser(user)
    }

    suspend fun getUsersWithStatistics(): List<UsersWithStatistics> {
        return fitDB.userWithStatisticsDAO().getUsersWithStatistics()
    }

    //Get Food and save this
    suspend fun getFoodApi():List<Food>{
        return fitApi.getFood()
    }
    suspend fun getFood():List<Food>{
        return fitDB.foodDao().getFoods()
    }
    suspend fun saveFood(list: List<Food>)
    {
        fitDB.foodDao().addFood(list)
    }
    //get and save Dishes
    suspend fun getDishesApi():List<Dish>{
        return fitApi.getDishes()
    }
    suspend fun getDishes():List<Dish>{
        return fitDB.dishDao().getDishes()
    }
    suspend fun saveDishes(list: List<Dish>)
    {
        fitDB.dishDao().insertDishes(list)
    }

    //ExercisesType
    suspend fun getExercisesTypesApi():List<ExerciseType>{
        return fitApi.getExercisesTypes()
    }
    suspend fun getExercisesTypes():List<ExerciseType>{
        return fitDB.exerciseTypeDao().getExerciseType()
    }
    suspend fun saveExercisesTypes(list: List<ExerciseType>){
        fitDB.exerciseTypeDao().insertExerciseType(list)
    }

    suspend fun getExercisesWithTypes(): List<ExerciseWithType>{
        return fitDB.exerciseWithTypeDao().getExerciseWithType()
    }


    suspend fun getUserWithStatistics(): List<UsersWithStatistics> {
        return fitDB.userWithStatisticsDAO().getUsersWithStatistics()
    }

    suspend fun updateStatistic(statistic: Statistics){
        fitDB.statisticsDao().updateStatistics(statistic)
    }

    suspend fun addStatistic(statistic: Statistics) {
        fitDB.statisticsDao().addStatistic(statistic)
    }

    suspend fun getStatisticsApi(userId:Int):List<Statistics>{
        val  statistics = fitApi.getStatistics(userId)
        Log.d("FitApiRepository", "Fetched statistics from server: $statistics")
        return statistics
    }

    suspend fun saveStatistics(list:List<Statistics>){
        Log.d("FitApiRepository", "Saving statistics to local DB: $list")
        fitDB.statisticsDao().saveStatistics(list)
        Log.d("FitApiRepository", "Statistics saved to local DB")
    }

    //Exercises
    suspend fun getExercises():List<Exercise>{
        return fitDB.exerciseDao().getExercise()
    }
    suspend fun saveExercises(list:List<Exercise>){
        fitDB.exerciseDao().insertExercise(list)
    }
    suspend fun getExercisesApi():List<Exercise>{
        return fitApi.getExercises()
    }

    //FitnessLevel
    suspend fun getFitnessLevel():List<FitnessLevel>{
        return fitDB.fitnessLevelDao().getLevelFitness()
    }
    suspend fun getFitnessLevelApi():List<FitnessLevel>{
        return fitApi.getFitnessLevel()
    }
    suspend fun saveFitnessLevel(list:List<FitnessLevel>){
        fitDB.fitnessLevelDao().savedata(list)
    }


    suspend fun insertConsumedFoodEntity(consumedFoodEntities: List<ConsumedFoodEntity>) {
        fitDB.consumedFoodEntityDao().insertConsumedFoodEntity(consumedFoodEntities)
    }

    suspend fun getConsumedFoodEntityByDate(userId: Int, date: LocalDate): List<ConsumedFoodEntity> {
        return fitDB.consumedFoodEntityDao().getConsumedFoodEntityByDate(userId, date)
    }

    suspend fun updateConsumedFoodEntity(consumedFoodEntity: ConsumedFoodEntity) {
        fitDB.consumedFoodEntityDao().updateConsumedFoodEntity(consumedFoodEntity)
    }

    suspend fun deleteConsumedFoodEntity(consumedFoodEntity: ConsumedFoodEntity) {
        fitDB.consumedFoodEntityDao().deleteConsumedFoodEntity(consumedFoodEntity)
    }

    suspend fun getExercisetotype():List<ExerciseToType>{
        return fitDB.exerciseToTypeDAO().getExerciseToType()
    }

    suspend fun getExercisetotypeApi():List<ExerciseToType>{
        return fitApi.getExerciseToType()
    }
    suspend fun saveExercisesToType(list:List<ExerciseToType>){
        fitDB.exerciseToTypeDAO().insertExerciseToType(list)
    }
}