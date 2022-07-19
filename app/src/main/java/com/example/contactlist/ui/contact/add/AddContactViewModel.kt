package com.example.contactlist.ui.contact.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.contact.base.BaseContactViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddContactViewModel(private val repository: ContactRepository): BaseContactViewModel() {


    fun save() {
        if(name.value.isNullOrEmpty() && phone.value.isNullOrEmpty()) {
            //error
            viewModelScope.launch {
                _error.emit("Please enter name and value correctly")
            }
        } else {
            val contact = Contact(name = name.value ?: "", phone = phone.value ?: "")
            repository.addContact(contact)
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        }
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AddContactViewModel::class.java)){
                    return AddContactViewModel(repository) as T
            }

            throw IllegalArgumentException("ViewModel is not Valid")
        }
    }
}