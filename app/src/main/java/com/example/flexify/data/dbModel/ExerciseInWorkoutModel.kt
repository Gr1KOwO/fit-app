package com.example.flexify.data.dbModel

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseInWorkoutEntity (
    @SerializedName("id")val id: Int,
    @SerializedName("exercise")val exercise: ExerciseWithType,
    @SerializedName("sets")val sets: Int,
    @SerializedName("reps")val reps: Int,
    @SerializedName("weight")val weight: Int?,
    @SerializedName("met")val met:Float, // МЕТ для расчёта потраченных калорий за упражнение
    @SerializedName("isCompleted") var isCompleted: Boolean,
    @SerializedName("isSkipped") var isSkipped: Boolean
):Parcelable