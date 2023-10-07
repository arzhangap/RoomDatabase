package com.example.roomdatabase.repository

import com.example.roomdatabase.Database.PersonDao
import com.example.roomdatabase.model.Person
import kotlinx.coroutines.flow.Flow

class PersonRepo(private val personDao: PersonDao) {

    suspend fun update(person: Person) {
        personDao.update(person)
    }

    suspend fun insert(person: Person) {
        personDao.insert(person)
    }

    suspend fun delete(person: Person) {
        personDao.delete(person)
    }

    suspend fun deleteAll() {
        personDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String) : Flow<List<Person>> {
        return personDao.search(searchQuery)
    }

    val readAllData: Flow<List<Person>> = personDao.getPersons()
}