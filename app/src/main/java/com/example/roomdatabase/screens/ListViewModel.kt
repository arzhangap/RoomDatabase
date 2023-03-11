package com.example.roomdatabase.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roomdatabase.Database.Person
import com.example.roomdatabase.Database.PersonDatabase
import com.example.roomdatabase.Database.PersonRepo
import kotlinx.coroutines.flow.Flow

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private var personRepo: PersonRepo
    init {
        val personDao = PersonDatabase.getDatabase(context = application).personDao
        personRepo = PersonRepo(personDao)
    }
    fun readAllData() : Flow<List<Person>> = personRepo.readAllData
}