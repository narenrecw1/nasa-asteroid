package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.data.PictureOfDay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Backend API interface for the Planetary API for NASA
 */
interface Planetary {

    @GET("planetary/apod")
    suspend fun pictureOfTheDay(@Query("date")date:String ,
                                @Query("hd") hd:Boolean = false): Response<PictureOfDay>
}