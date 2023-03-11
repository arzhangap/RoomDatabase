package com.example.roomdatabase.Database

import kotlinx.coroutines.flow.Flow

class PersonRepo(private val personDao: PersonDao) {

    suspend fun insert(person: Person) {
        personDao.insert(person)
    }

    val readAllData: Flow<List<Person>> = personDao.getPersons()
}