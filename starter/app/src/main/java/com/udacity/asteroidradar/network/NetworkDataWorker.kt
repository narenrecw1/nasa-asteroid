package com.udacity.asteroidradar.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.Repository

/**
 * A data worker class used to pre-fetch the data needed once a day
 * @param context Any parameters to pass to the base class.
 *
 * @author Narendra Darla(R)
 */
class NetworkDataWorker(private val context: Context, params:WorkerParameters):
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val repository = Repository(context)
        repository.refreshData()
        return Result.success()
    }
}