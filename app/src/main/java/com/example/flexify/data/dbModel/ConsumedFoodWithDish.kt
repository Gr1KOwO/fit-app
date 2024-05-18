package com.example.flexify.data.dbModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ConsumedFoodWithDish(
    @Embedded val consumedFood: ConsumedFoodEntity,
    @Relation(
        parentColumn = "dishId",
        entityColumn = "id"
    )
    val dishes: Dish?
)
