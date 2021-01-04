package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.Repository
import kotlinx.coroutines.launch

class MainViewModel (repository:Repository,
                     pictureOfTheDayEmptyCD:String,
                     pictureOfTheDayCDFormat:String): ViewModel() {

        init{
            viewModelScope?.launch {
                repository.refreshData()
            }
        }
        private val asteroidFilter = MutableLiveData(AsteroidFilter.CURRENT_WEEK)
        val asteroids = Transformations.switchMap(asteroidFilter){
            when(it!!){
                AsteroidFilter.CURRENT_WEEK -> {
                    println("MainViewModel"+repository.asteroidsThisWeek.value?.size)
                    repository.asteroidsThisWeek
                }
                AsteroidFilter.ONLY_DAY -> repository.asteroidsToday
            }
        }
        val imageOfTheDayUrl = Transformations.map(repository.pictureOfDay){
            //it?.url
            "https://res.cloudinary.com/demo/image/upload/l_cloudinary_icon_white,w_300,g_north_east,o_60/w_500/sample.jpg"
        }

        fun setAsteroidFilter(filter:AsteroidFilter){
            asteroidFilter.postValue(filter)
        }
        val imageOfTheDayDescription = Transformations.map(repository.pictureOfDay){ picture->
            picture?.title?.let{ pictureOfTheDayCDFormat.format(it)}?:pictureOfTheDayEmptyCD

        }
}

enum class AsteroidFilter{
    CURRENT_WEEK,
    ONLY_DAY
}