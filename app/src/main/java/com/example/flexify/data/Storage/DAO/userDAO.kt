package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.User

@Dao
interface userDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user:User)

    @Query("Select * from User")
    suspend fun getUser():List<User>

    @Query("SELECT * FROM User WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}