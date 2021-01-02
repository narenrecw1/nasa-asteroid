package com.udacity.asteroidradar.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

/**
 * The App's Database
 * @author Narendra Darla
 */
@Database(entities=[Asteroid::class, PictureOfDay::class], version= 1)
abstract class AppDatabase() :RoomDatabase(){

    abstract fun asteroidDao(): AsteroidDao
    abstract fun pictureOfDayDao(): PictureOfDayDao

    companion object{
        @Volatile
        private lateinit var INSTANCE : AppDatabase
        fun getInstance(contex:Context):AppDatabase = synchronized(this){
            if(!::INSTANCE.isInitialized){
                INSTANCE = Room.databaseBuilder(contex, AppDatabase::class.java,
                    "app_database").fallbackToDestructiveMigration().build()
            }
            return INSTANCE
        }
    }
}