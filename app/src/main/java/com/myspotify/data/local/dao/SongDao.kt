package com.myspotify.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myspotify.data.local.entity.SongDB

@Dao
interface SongDao {
    @Query("select * from SongDB order by positionOnList")
    fun getAll(): LiveData<List<SongDB>>

    @Query("select * from SongDB where isFavorite = 1 order by positionOnList")
    fun getAllFavoriteSongs(): LiveData<List<SongDB>>

    @Query("select * from SongDB group by artistId")
    fun getAllArtists(): LiveData<List<SongDB>>

    @Query("select * from SongDB where artistId = :artistId")
    fun getAllArtistSongs(artistId: Int): LiveData<List<SongDB>>

    @Query("select * from SongDB where id = :songId")
    fun getById(songId: Int): SongDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: SongDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( songs: List<SongDB>)

    @Delete
    fun delete(song: SongDB)
}