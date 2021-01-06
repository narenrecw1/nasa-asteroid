package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.formattedForNeoWS
import com.udacity.asteroidradar.getDateAfterNumDays
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Helper function to parse the JSON response given by the NeoWS API into a list of our [Asteroid]
 * objects
 *
 * @param jsonResult The JSON response that came from the API.
 * @return A [List] of [Asteroid]s
 *
 * @author Narendra Darla(R)
 */
fun parseAsteroidsJsonResult(jsonResult: JSONObject): List<Asteroid> {
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")
    val asteroidList = mutableListOf<Asteroid>()
    val formattedDates =
        (0..Constants.DEFAULT_END_DATE_DAYS).map { getDateAfterNumDays(it).formattedForNeoWS }

    for (formattedDate in formattedDates) {
        try {
            val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

            for (i in 0 until dateAsteroidJsonArray.length()) {
                val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
                val id = asteroidJson.getLong("id")
                val codename = asteroidJson.getString("name")
                val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")

                val estimatedDiameter = asteroidJson
                    .getJSONObject("estimated_diameter")
                    .getJSONObject("kilometers")
                    .getDouble("estimated_diameter_max")

                val closeApproachData = asteroidJson
                    .getJSONArray("close_approach_data")
                    .getJSONObject(0)

                val relativeVelocity = closeApproachData
                    .getJSONObject("relative_velocity")
                    .getDouble("kilometers_per_second")

                val distanceFromEarth = closeApproachData
                    .getJSONObject("miss_distance")
                    .getDouble("astronomical")

                val isPotentiallyHazardous = asteroidJson
                    .getBoolean("is_potentially_hazardous_asteroid")

                val asteroid = Asteroid(
                    id, codename, formattedDate, absoluteMagnitude,
                    estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous
                )

                asteroidList.add(asteroid)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    return asteroidList
}