package com.example.roomdatabase.screens

import android.app.Application
import android.text.Editable
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.Database.Person
import com.example.roomdatabase.Database.PersonDao
import com.example.roomdatabase.Database.PersonDatabase
import com.example.roomdatabase.Database.PersonRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRowViewModel(application: Application) : AndroidViewModel(application) {

    private var personRepo: PersonRepo

    init {
        val personDao = PersonDatabase.getDatabase(context = application).personDao
        personRepo = PersonRepo(personDao)
    }

    fun insert(person:Person) {
        val name = person.name
        val age = person.age
        if(checkInput(name,age)) {
            viewModelScope.launch(Dispatchers.IO) {
                personRepo.insert(person)
            }
        }
    }

    private fun checkInput(name: String, age: String) : Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(age))
    }
}