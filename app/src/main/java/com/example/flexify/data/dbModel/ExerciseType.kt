package com.example.flexify.data.dbModel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ExerciseType(
    @PrimaryKey
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
): Parcelable
