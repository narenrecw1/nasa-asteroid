package com.udacity.asteroidradar.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An Interceptor used to add 'api_key' parameter to the network requests
 *
 * @author Narendra Darla
 */
class NasaAuthInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newUrl = chain.request().url().newBuilder().addQueryParameter("api_key", "YR8Qi7VSYFKCysrFPtoVEW0W7FWgLgq3DdWHCk3A").build()
        val newRequest = chain.request().newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }

}