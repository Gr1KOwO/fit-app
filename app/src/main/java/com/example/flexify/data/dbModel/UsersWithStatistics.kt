package com.example.flexify.data.dbModel

import androidx.room.Embedded
import androidx.room.Relation


data class UsersWithStatistics(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val statistics: List<Statistics>
)
