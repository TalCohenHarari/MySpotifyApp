package com.myspotify.presentation.fragments.artistsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myspotify.data.local.entity.SongDB
import com.myspotify.data.repository.SongsRepository
import com.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsListViewModel @Inject constructor(private val songsRepository : SongsRepository) : ViewModel() {

    private val artistsList: LiveData<List<SongDB>> = songsRepository.getArtistListFromDB()
    private val  toast: MutableLiveData<String> = MutableLiveData<String>()

    init {
        viewModelScope.launch{
            when(val result = songsRepository.getSongsListFromServer()){
                is Resource.Error -> { toast.value = result.message ?:  "Failed" }
                else -> toast.value =  "Success"
            }
        }
    }

    fun getSongsListFromDB(): LiveData<List<SongDB>> {
        return artistsList
    }

    fun getMessage(): MutableLiveData<String>{
        return toast
    }

}