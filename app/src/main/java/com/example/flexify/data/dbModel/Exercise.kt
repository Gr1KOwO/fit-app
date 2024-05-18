package com.example.flexify.data.dbModel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Exercise(
    @PrimaryKey
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("description")val description: String,
    @SerializedName("instruction")val instruction:String,
    @SerializedName("urlGif")val urlGif:String?,
    @SerializedName("medicalContraindications")val medicalContraindications:String,
): Parcelable
