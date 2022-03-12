package com.myspotify.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongDB(
    @PrimaryKey
    var id: Int,
    var artistId: Int,
    var artistName :String,
    var artistImageUrl: String,
    var songName :String,
    var songUrl :String,
    var isDeleted: Boolean,
    var isFavorite: Boolean,
    var lastUpdate: Long,
    var positionOnList: Int)
