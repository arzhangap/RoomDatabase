package com.example.roomdatabase.viewmodel

import android.app.Application
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.model.Person
import com.example.roomdatabase.Database.PersonDatabase
import com.example.roomdatabase.R
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

}