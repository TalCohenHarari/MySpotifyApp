package com.myspotify.presentation.ui.fragments.artistsList

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
class ArtistsListViewModel @Inject constructor(private val songsRepository : SongsRepository) : ViewModel() {

    val artistsList: LiveData<List<SongDB>> = songsRepository.getArtistListFromDB()

    init {
        viewModelScope.launch{
            songsRepository.getSongsListFromServer()
        }
    }

    fun getSongsListFromDB(): LiveData<List<SongDB>> {
        return artistsList
    }

}