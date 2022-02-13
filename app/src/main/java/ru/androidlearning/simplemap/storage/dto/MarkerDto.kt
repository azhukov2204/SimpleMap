package ru.androidlearning.simplemap.storage.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class MarkerDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "title") val title: String
)
