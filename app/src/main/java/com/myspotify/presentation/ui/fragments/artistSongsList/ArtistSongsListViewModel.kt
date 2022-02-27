package com.myspotify.presentation.ui.fragments.artistSongsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.repository.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistSongsListViewModel @Inject constructor(private val songsRepository : SongsRepository) : ViewModel() {

    lateinit var artistSongsList: LiveData<List<SongDB>>
    var artistId: Int = -1

    fun getArtistSongsListFromDB(): LiveData<List<SongDB>> {
        artistSongsList = songsRepository.getArtistSongsListFromDB(artistId)
        return artistSongsList
    }

    fun getSongById(songId: Int): SongDB? {
        for (songDB in artistSongsList.value!!){
            if(songDB.id == songId){
                return songDB
            }
        }
        return null
    }

    fun updateSong(songDB: SongDB) {
        viewModelScope.launch {
           songDB.isFavorite = !songDB.isFavorite
           songsRepository.updateSong(songDB)
        }
    }
}