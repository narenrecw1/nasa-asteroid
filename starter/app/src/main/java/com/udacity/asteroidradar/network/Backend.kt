package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * An Object holding the singleton instances of APIs used.
 *
 * @author Narendra Darla
 */
object Backend {

    val neoWS : NeoWS by lazy { retrofit.create(NeoWS::class.java) }
    val planetary :Planetary by lazy{ retrofit.create(Planetary::class.java)}


    private val retrofit by lazy{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NasaAuthInterceptor()).build()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder().client(okHttpClient).baseUrl(Constants.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }
}