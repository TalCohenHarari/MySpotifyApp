package com.myspotify.presentation.ui.fragments.favoriteSongsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.repository.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteSongsListViewModel @Inject constructor(private val songsRepository : SongsRepository) : ViewModel() {

    val favoriteSongsList: LiveData<List<SongDB>> = songsRepository.getFavoriteSongsListFromDB()

    fun getSongsListFromDB(): LiveData<List<SongDB>> {
        return favoriteSongsList
    }

    fun updateItemsPositionOnList(list: List<SongDB>){
        viewModelScope.launch {
            songsRepository.updateItemsPositionOnList(list)
        }
    }

    fun getSongById(songId: Int): SongDB? {

        favoriteSongsList.value?.forEach { songDB ->
            if(songDB.id == songId) return songDB
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