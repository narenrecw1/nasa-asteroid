package com.udacity.asteroidradar.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay

/**
 * The Apps Database
 *
 * @author Narendra Darla(R)
 */
@Database(entities = [Asteroid::class,PictureOfDay::class],version= 1)
abstract class AsteroidDatabase:RoomDatabase() {

    abstract fun asteroidDao():Asteroidao

    abstract fun pictureOfDayDao():PictureOfDayDao

    companion object{
        @Volatile
        private lateinit var INSTANCE : AsteroidDatabase

        fun getInstance(context: Context): AsteroidDatabase = synchronized(this){
            if(!::INSTANCE.isInitialized){

                INSTANCE = Room.databaseBuilder(context,AsteroidDatabase::class.java,"app_database").
                fallbackToDestructiveMigration().build()
            }
            return INSTANCE
        }
    }

}