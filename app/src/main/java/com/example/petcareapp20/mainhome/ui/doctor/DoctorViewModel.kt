package com.example.petcareapp20.mainhome.ui.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DoctorViewModel:ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text
}