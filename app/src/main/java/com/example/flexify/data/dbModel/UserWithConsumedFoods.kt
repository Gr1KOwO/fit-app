package com.example.flexify.data.dbModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class UserWithConsumedFoods(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val consumedFoods: List<ConsumedFoodEntity>
)
