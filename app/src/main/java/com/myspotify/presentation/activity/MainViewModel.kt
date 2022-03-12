package com.myspotify.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.repository.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val songsRepository : SongsRepository): ViewModel() {

    lateinit var  songsList : LiveData<List<SongDB>>

    init {
        viewModelScope.launch {
            songsList = songsRepository.getSongsListFromDB()
            songsRepository.getSongsListFromServer()
        }
    }

    fun getSongsListFromDB(): LiveData<List<SongDB>> {
        return songsList
    }

}