package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * An Object holding the singleton instances of APIs used.
 *
 * @author Narendra Darla(R)
 */
object Backend {

    /**
     * The Near-Earth-Object Watcher Service from NASA
     */
    val neoWS : NeoWS by lazy { retrofit.create(NeoWS::class.java) }

    /**
     * The Planetary API from NASA. Provides the image of the day
     */
    val planetary :Planetary by lazy{ retrofit.create(Planetary::class.java)}


    private val retrofit by lazy{

        val loggingLevel = if(BuildConfig.DEBUG) HttpLoggingInterceptor
            .Level.BODY else HttpLoggingInterceptor.Level.NONE
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = loggingLevel })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(NasaAuthInterceptor()).build()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}