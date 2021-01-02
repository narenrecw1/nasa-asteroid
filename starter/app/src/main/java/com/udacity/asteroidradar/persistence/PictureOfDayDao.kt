package com.udacity.asteroidradar.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.PictureOfDay

//@Dao
//interface PictureOfDayDao {
////    @Query("SELECT * FROM picture_of_the_day WHERE id = 0")
////    fun get():LiveData<PictureOfDay?>
//
//
//    @Insert(onConflict =  OnConflictStrategy.REPLACE)
//    fun save(pictureOfDay: PictureOfDay)
//}


@Dao
interface PictureOfDayDao {
    @Query("SELECT * FROM picture_of_the_day WHERE id = 0")
    fun get(): LiveData<PictureOfDay?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(pictureOfDay: PictureOfDay)
}