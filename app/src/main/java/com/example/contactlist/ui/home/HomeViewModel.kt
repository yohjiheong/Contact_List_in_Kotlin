package com.example.contactlist.ui.home

import androidx.lifecycle.*
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val contactRepository: ContactRepository): ViewModel() {
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts

    val emptyScreen: MutableLiveData<Boolean> = MutableLiveData()


    init {
        getContacts()
    }


    private fun getContacts() {
        val response = contactRepository.getContacts()
        _contacts.value = response
        emptyScreen.value = _contacts.value.isNullOrEmpty()
    }

    fun refresh() {
        getContacts()
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository) as T
            }

            throw IllegalArgumentException("Invalid ViewModel")
        }
    }

}