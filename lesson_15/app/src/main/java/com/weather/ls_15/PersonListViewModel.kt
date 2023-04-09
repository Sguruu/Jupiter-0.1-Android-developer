package com.weather.ls_15

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonListViewModel : ViewModel() {

    private val repository = PersonRepository()

    private val _personLiveData = MutableLiveData<List<Person>>(repository.createListPerson(3))

    // для получения значения из вне и для подписки
    val personLiveData: LiveData<List<Person>>
        get() = _personLiveData

    fun addPerson() {
        // orEmpty будет возвращать пустой список, если список будет равен null
        updateLiveData(repository.addPerson(_personLiveData.value.orEmpty()))
    }

    fun deletePerson(position: Int) {
        updateLiveData(repository.deletePerson(_personLiveData.value.orEmpty(), position))
    }

    private fun updateLiveData(newValue: List<Person>) {
        // после вызова postValue все подписчики автоматически получат список новых персон
        _personLiveData.value = newValue
    }
}
