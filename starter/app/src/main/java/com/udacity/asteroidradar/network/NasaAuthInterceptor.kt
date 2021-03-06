package com.udacity.asteroidradar.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An Interceptor used to add 'api_key' parameter to the network requests automatically to the network requests
 *
 * @author Narendra Darla(R)
 */
class NasaAuthInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newUrl = chain.request().url
                        .newBuilder()
                        .addQueryParameter("api_key", "")
                        .build()
        val newRequest = chain.request()
                       .newBuilder()
                       .url(newUrl)
                       .build()
        return chain.proceed(newRequest)
    }

}