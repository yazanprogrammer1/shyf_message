package com.example.shyf_message.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shyf_message.ui.contacts.ContactsViewModel

class ContactViewModelFactory(private val senderid: String, private val receiverid: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            return ContactsViewModel(senderid,receiverid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}