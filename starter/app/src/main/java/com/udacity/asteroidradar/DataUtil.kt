package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ROOT)
private val defaultTimeZone = TimeZone.getDefault()
private val nasaTimeZone = TimeZone.getTimeZone("US/Eastern")

fun getToday():Date = Calendar.getInstance().time

fun getDateAfterNumDays(days:Int):Date = with(Calendar.getInstance()){
    add(Calendar.DAY_OF_YEAR,days)
    return@with time
}

val Date.formattedForNeoWS :String
    get() = dateFormat.apply { timeZone = nasaTimeZone}.format(this)

val Date.formatForPlanetaryAPI :String
    get() = dateFormat.apply{ timeZone = nasaTimeZone}.format(this)