package com.myspotify.presentation.fragments.songDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.repository.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongDetailsViewModel @Inject constructor(songsRepository : SongsRepository): ViewModel() {

     private var  songsList : LiveData<List<SongDB>> = songsRepository.getSongsListFromDB()

    fun getSongsList(): LiveData<List<SongDB>> {
        return songsList
    }

    fun getSongById(songId:Int): SongDB? {
        songsList.value!!.forEach { song ->
            if(song.id == songId) {
                return song
            }
        }
        return null
    }
}