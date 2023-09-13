package com.example.shyf_message.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shyf_message.ui.accountdetails.AccountDetailViewModel

class AccountDetailViewModelFactory(private val senderid: String, private val receiverid: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountDetailViewModel::class.java)) {
            return AccountDetailViewModel(senderid,receiverid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}