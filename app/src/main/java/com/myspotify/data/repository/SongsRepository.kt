package com.myspotify.data.repository

import androidx.lifecycle.LiveData
import com.myspotify.data.local.AppDatabase
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.remote.Data
import com.myspotify.data.remote.MySpotifyApi
import com.myspotify.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongsRepository @Inject constructor(private val api: MySpotifyApi, private val db: AppDatabase) {


    suspend fun getSongsListFromServer(){
        withContext(Dispatchers.IO){
            val response = api.getPlaylist(Constants.API_DATA_ID,Constants.API_DATA_LIMITATION)
            if(response.isSuccessful && response.body() != null){
                saveSongsListOnDB(convertDataFromServer(response.body()!!.data))
            }
        }
    }

    fun getSongsListFromDB(): LiveData<List<SongDB>> {
        return db.songDao().getAll()
    }

    fun getArtistListFromDB(): LiveData<List<SongDB>> {
        return db.songDao().getAllArtists()
    }

    fun getArtistSongsListFromDB(artistId: Int): LiveData<List<SongDB>> {
        return db.songDao().getAllArtistSongs(artistId)
    }

    fun getFavoriteSongsListFromDB(): LiveData<List<SongDB>> {
        return db.songDao().getAllFavoriteSongs()
    }

    private fun saveSongsListOnDB(songsFromServer: List<SongDB>){
        // Because it is not our server that we can save the "position in the list" for the song,
        // we will make sure that we do not override the "position in the list" and "isFavorite" of this song if it already exists
        for (s in songsFromServer){
            val songExist = db.songDao().getById(s.id)
            if( songExist != null){
                s.positionOnList = songExist.positionOnList
                s.isFavorite = songExist.isFavorite
            }
        }

        db.songDao().insertAll(songsFromServer)
    }

    private fun convertDataFromServer(resultFromServerList: List<Data>):MutableList<SongDB>{
        val list = ArrayList<SongDB>()
        for ( i in resultFromServerList.indices){
            val song = resultFromServerList[i]
            val songDB = SongDB(id = song.id ,
                artistId = song.artist.id,
                artistName = song.artist.name,
                artistImageUrl = song.artist.picture_big,
                songName = song.title_short,
                songUrl = song.previewSong,
                isDeleted = false,
                isFavorite = false,
                lastUpdate = 0,
                positionOnList = i)
            list.add(songDB)
        }
        return list
    }

    suspend fun updateItemsPositionOnList(list: List<SongDB>) {
        withContext(Dispatchers.IO){
            db.songDao().insertAll(list)
        }
    }

    suspend fun updateSong(songDB: SongDB){
        withContext(Dispatchers.IO){
            db.songDao().insert(songDB)
        }
    }
}