package ru.androidlearning.simplemap.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.androidlearning.simplemap.storage.dao.MarkersDao
import ru.androidlearning.simplemap.storage.dto.MarkerDto

@Database(exportSchema = false, entities = [MarkerDto::class], version = 1)
abstract class MarkersDatabase : RoomDatabase() {
    abstract fun getMarkersDao(): MarkersDao
}