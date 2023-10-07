package com.example.roomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.Database.PersonDatabase
import com.example.roomdatabase.model.Person
import com.example.roomdatabase.repository.PersonRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application)  {

    private var personRepo: PersonRepo
    init {
        val personDao = PersonDatabase.getDatabase(context = application).personDao
        personRepo = PersonRepo(personDao)
    }
    fun readAllData() : Flow<List<Person>> = personRepo.readAllData

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            personRepo.deleteAll()
        }
    }

    fun searchDatabase(query: String) : Flow<List<Person>> {
        val searchQuery = "%$query%"
        return personRepo.searchDatabase(searchQuery)
    }

}