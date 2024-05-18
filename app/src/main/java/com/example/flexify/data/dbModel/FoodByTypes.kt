package com.example.flexify.data.dbModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class FoodByTypes(
    @Embedded val foodType: FoodType,
    @Relation(
        parentColumn = "id",
        entityColumn = "foodTypeId"
    )
    val foodList:List<Food>
)
