package ru.androidlearning.simplemap.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ru.androidlearning.simplemap.storage.dto.MarkerDto

@Dao
interface MarkersDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveMarker(markerDto: MarkerDto): Long

    @Delete
    suspend fun deleteMarker(markerDto: MarkerDto)

    @Query("SELECT * FROM markers")
    suspend fun getAllMarkers(): List<MarkerDto>
}