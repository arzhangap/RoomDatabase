package com.example.roomdatabase.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {

    @Insert
    suspend fun insert(person: Person)

    @Query("SELECT * FROM person_table")
    fun getAllPerson() : List<Person>
}