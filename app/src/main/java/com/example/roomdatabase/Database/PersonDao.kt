package com.example.roomdatabase.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert
    suspend fun insert(person: Person)

    @Query("SELECT * FROM person_table")
    fun getPersons() : Flow<List<Person>>
}