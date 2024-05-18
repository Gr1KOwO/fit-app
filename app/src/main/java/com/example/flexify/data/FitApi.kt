package com.example.flexify.data


import com.example.flexify.data.ResponseModel.BaseResponse
import com.example.flexify.data.ResponseModel.ResponseLogin
import com.example.flexify.data.ResponseModel.ResponseResult
import com.example.flexify.data.ResponseModel.ResponseSignIn
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.dbModel.ExerciseToType
import com.example.flexify.data.dbModel.ExerciseType
import com.example.flexify.data.dbModel.FitnessLevel
import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.dbModel.FoodType
import com.example.flexify.data.dbModel.Statistics
import com.example.flexify.data.requestmodel.RequestLogin
import com.example.flexify.data.requestmodel.RequestSignIn
import com.example.flexify.data.requestmodel.UserUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.Query

interface FitApi {
    @POST("signup")
    suspend fun signup(
        @Body requestLogin: RequestLogin,
    ):BaseResponse<ResponseLogin>

    @POST("signin")
    suspend fun signin(
        @Body requestSignIn: RequestSignIn,
    ):ResponseSignIn

    @PATCH("update-user")
    suspend fun updateUser(
        @Body response: UserUpdateRequest
    )

    @GET("food-types")
    suspend fun getFoodType():List<FoodType>
    @GET("food-list")
    suspend fun getFood():List<Food>
    @GET("dishes")
    suspend fun getDishes():List<Dish>
    @GET("exercises-types")
    suspend fun getExercisesTypes():List<ExerciseType>
    @GET("fitness-level")
    suspend fun getFitnessLevel():List<FitnessLevel>
    @GET("exercises")
    suspend fun getExercises():List<Exercise>
    @GET("exercise-to-type")
    suspend fun getExerciseToType():List<ExerciseToType>
    @GET("statistics-by-user")
    suspend fun getStatistics(@Query("userId")userId:Int):List<Statistics>
}