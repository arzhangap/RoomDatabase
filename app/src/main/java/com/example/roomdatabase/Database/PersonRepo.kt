package com.example.roomdatabase.Database

class PersonRepo(private val personDao: PersonDao) {

    suspend fun insert(person: Person) {
        personDao.insert(person)
    }

}