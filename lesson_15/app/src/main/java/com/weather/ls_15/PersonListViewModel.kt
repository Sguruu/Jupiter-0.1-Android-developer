package com.weather.ls_15

import androidx.lifecycle.ViewModel

class PersonListViewModel : ViewModel() {

    private val repository = PersonRepository()
    private var listPersons: List<Person> = emptyList()

    fun createListPerson(count: Int) {
        listPersons = repository.createListPerson(count)
    }

    fun addPerson() {
        listPersons = repository.addPerson(listPersons)
    }

    fun deletePerson(position: Int) {
        listPersons = repository.deletePerson(listPersons, position)
    }

    fun getListPersons() = listPersons
}
