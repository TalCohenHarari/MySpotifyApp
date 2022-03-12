package com.myspotify.data.repository

import androidx.lifecycle.LiveData
import com.myspotify.data.local.AppDatabase
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.remote.MySpotifyApi
import com.myspotify.data.remote.models.Data
import com.myspotify.data.remote.models.Song
import com.myspotify.utils.Constants
import com.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongsRepository @Inject constructor(private val api: MySpotifyApi, private val db: AppDatabase) {

    suspend fun getSongsListFromServer(): Resource<Song> {
       return  withContext(Dispatchers.IO){
            try {
                val response = api.getPlaylist(Constants.API_DATA_ID,Constants.API_DATA_LIMITATION)
                val result = response.body()
                if(response.isSuccessful && result!= null){
                    saveSongsListOnDB(convertDataFromServer(result.data))
                    Resource.Success(result)
                }else{
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error(e.message?:"An error occurred")
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

    private suspend fun saveSongsListOnDB(songsFromServer: List<SongDB>){
        // Because it is not our server that we can save the "position in the list" for the song,
        // we will make sure that we do not override the "position in the list" and "isFavorite" of this song if it already exists
        songsFromServer.forEach { song ->
            val songExist = db.songDao().getById(song.id)
            if( songExist != null){
                song.positionOnList = songExist.positionOnList
                song.isFavorite = songExist.isFavorite
            }
        }
        db.songDao().insertAll(songsFromServer)
    }

    private fun convertDataFromServer(resultFromServerList: List<Data>): List<SongDB> {
        val list = resultFromServerList.mapIndexed{i, song ->
            SongDB(id = song.id ,
                artistId = song.artist.id,
                artistName = song.artist.name,
                artistImageUrl = song.artist.picture_big,
                songName = song.title_short,
                songUrl = song.previewSong,
                isDeleted = false,
                isFavorite = false,
                lastUpdate = 0,
                positionOnList = i)
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