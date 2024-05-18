package com.example.flexify.data.dbModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ConsumedFoodWithFood(
    @Embedded val consumedFood: ConsumedFoodEntity,
    @Relation(
        parentColumn = "foodId",
        entityColumn = "id"
    )
    val food: Food?
)
