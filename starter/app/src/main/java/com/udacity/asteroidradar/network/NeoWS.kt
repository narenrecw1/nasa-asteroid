package com.udacity.asteroidradar.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Backend API interface for rhe Near-Earth-Object Watcher Service from NASA
 *
 * @author Narendra Darla(R)
 */
interface NeoWS {

    @GET("neo/rest/v1/feed")
    suspend fun feed(@Query("start_date") startDate:String,
                     @Query("end_date") endDate:String
    ): Response<String>
}