package com.example.roomdatabase.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.model.Person
import com.example.roomdatabase.Database.PersonDatabase
import com.example.roomdatabase.repository.PersonRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddRowViewModel(application: Application) : AndroidViewModel(application) {

    private var personRepo: PersonRepo

    private var _isInputValid = false
    val isInputValid get() = _isInputValid

    init {
        val personDao = PersonDatabase.getDatabase(context = application).personDao
        personRepo = PersonRepo(personDao)
    }
    fun insert(person: Person) {
        val name = person.name
        val age = person.age
        if(checkInput(name, age)) {
            _isInputValid = true
            viewModelScope.launch(Dispatchers.IO) {
                personRepo.insert(person)
            }
        }
    }

    fun checkInput(name: String, age: String) : Boolean{
        return !(TextUtils.isEmpty(name)) && !(TextUtils.isEmpty(age))
    }

    fun onInsertComplete() {
        _isInputValid = false
    }
}