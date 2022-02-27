package com.myspotify.presentation.ui.fragments.songDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.repository.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongDetailsViewModel @Inject constructor(private val songsRepository : SongsRepository): ViewModel() {

    lateinit var  songsList : LiveData<List<SongDB>>

    init {
        viewModelScope.launch {
            songsList = songsRepository.getSongsListFromDB()
        }
    }

    fun getSongById(id:Int): SongDB? {
        for (song in songsList.value!!){
            if(song.id == id) {
                return song
            }
        }
        return null
    }
}