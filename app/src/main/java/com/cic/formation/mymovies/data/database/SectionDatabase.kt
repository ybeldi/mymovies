package com.cic.formation.mymovies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cic.formation.mymovies.data.api.json.Movies

@Database(
    entities = [Movies::class],
    version = 1,
    exportSchema = true
)

abstract class SectionDatabase : RoomDatabase() {
    abstract fun sectionDao(): MoviesDao
}