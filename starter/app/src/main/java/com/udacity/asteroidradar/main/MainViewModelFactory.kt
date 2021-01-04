package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.data.Repository
import java.lang.IllegalArgumentException

class MainViewModelFactory(val repository:Repository, val pictureOfDayEmptyCD:String, val pictureOfDayCDFormat:String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass .isAssignableFrom(MainViewModel::class.java)){
            @Suppress("ENCHECKED_CAST")
            return MainViewModel(repository,pictureOfDayEmptyCD,pictureOfDayCDFormat) as T

        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}