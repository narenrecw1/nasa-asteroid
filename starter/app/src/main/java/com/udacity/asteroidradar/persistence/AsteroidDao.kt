package com.udacity.asteroidradar.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.formattedForNeoWS
import com.udacity.asteroidradar.getDateAfterNumDays
import com.udacity.asteroidradar.getToday

/**
 * Data Access Object interface to access the Asteroid Data from the Room Data Base
 *
 * @author Narendra Darla
 */

@Dao
interface Asteroidao{


    //1.Get Asteroids
    //2.Get Asteroids by start and end date
    //3.Clear Asteroids before
    //4.Insert New

    @Query(
        """
        SELECT * FROM asteroids 
            WHERE Date(:startDate) <= Date(closeApproachDate) 
                AND Date(:endDate) >= Date(closeApproachDate) 
                ORDER BY Date(closeApproachDate)

               """
    )
    fun getAsteroids(startDate:String, endDate:String): LiveData<List<Asteroid>>


    /**
     * Deletes all the saved asteroids before the given date
     *
     * @param date the exclusive date
     */
    @Query("DELETE FROM asteroids WHERE Date(closeApproachDate) < Date(:date)")
    fun clearAsteroidsBefore(date:String)

    /**
     * Inserts a new [asteroid] into the table, replacing it if already existing
     *
     * @param asteroid The [Asteroid] object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroid(asteroid: Asteroid)



}
fun Asteroidao.getAsteroidsForCurrentWeek() = getAsteroids(getToday().formattedForNeoWS,
    getDateAfterNumDays(Constants.DEFAULT_END_DATE_DAYS).formattedForNeoWS)

fun Asteroidao.getAsteroidsForToday(): LiveData<List<Asteroid>>{
    val today = getToday().formattedForNeoWS
    return getAsteroids(today,today)
}
