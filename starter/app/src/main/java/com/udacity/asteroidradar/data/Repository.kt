package com.udacity.asteroidradar.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.network.Backend
import com.udacity.asteroidradar.persistence.AsteroidDatabase
import com.udacity.asteroidradar.persistence.getAsteroidsForCurrentWeek
import com.udacity.asteroidradar.persistence.getAsteroidsForToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.util.*



/**
 * Repository access the planetary and NeoWS asteroid data
 *
 * @param context the context used to get the db instance
 *
 * @author Narendra Darla
 */
class Repository(context: Context) {
    private val database = AsteroidDatabase.getInstance(context)

    val asteroidsThisWeek : LiveData<List<Asteroid>>
        get() = database.asteroidDao().getAsteroidsForCurrentWeek()


    val asteroidsToday : LiveData<List<Asteroid>>
        get() = database.asteroidDao().getAsteroidsForToday()

    val pictureOfDay :LiveData<PictureOfDay?>
            get() = database.pictureOfDayDao().get()

    suspend fun refreshData() = withContext(Dispatchers.IO){
        listOf(
            async { database.asteroidDao().clearAsteroidsBefore(getToday().formattedForNeoWS)  } ,
            async{ refreshAsteroidData()},
            async { refreshPictureOfDayData()}
        ).awaitAll()
    }

    private suspend fun refreshPictureOfDayData() {
       try{
          val response = Backend.planetary.pictureOfTheDay(getToday().formatForPlanetaryAPI, false)
           if(!response.isSuccessful)
               return
          database.pictureOfDayDao().save(response.body()!!)
       }catch (e:Exception){
           e.printStackTrace()
       }
    }


    private suspend fun refreshAsteroidData() {
       try{
            val response = Backend.neoWS.feed(getToday().formattedForNeoWS, getDateAfterNumDays(Constants.DEFAULT_END_DATE_DAYS).formattedForNeoWS)
            if(!response.isSuccessful){
                return
            }
           val body = JSONObject(response.body()!!)
           Log.v("refreshAsteroidData","refreshAsteroidData"+body.toString())
           val parsed = parseAsteroidsJsonResult(body)
           parsed.forEach(database.asteroidDao()::insertAsteroid)
       }catch (e:Exception){
           e.printStackTrace()
       }
    }
}