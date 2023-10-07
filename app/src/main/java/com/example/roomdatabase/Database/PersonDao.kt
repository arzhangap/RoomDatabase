package com.example.roomdatabase.Database

import androidx.room.*
import com.example.roomdatabase.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert
    suspend fun insert(person: Person)

    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("DELETE FROM person_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM person_table")
    fun getPersons() : Flow<List<Person>>

    @Query("SELECT * FROM person_table WHERE name LIKE :searchQuery OR age LIKE :searchQuery")
    fun search(searchQuery: String) : Flow<List<Person>>
}