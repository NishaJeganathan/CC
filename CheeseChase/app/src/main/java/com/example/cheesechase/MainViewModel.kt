package com.example.cheesechase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    private val _maximum = MutableLiveData<Int>()
    val maximum: LiveData<Int> get() = _maximum

    fun setMaximum(value: Int) {
        _maximum.value = value
    }

}