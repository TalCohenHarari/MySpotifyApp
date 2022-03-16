package com.myspotify.presentation.fragments.songDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.myspotify.R
import com.myspotify.databinding.FragmentSongDetailsBinding
import com.myspotify.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongDetailsFragment : Fragment() {

    private val songDetailsViewModel: SongDetailsViewModel by viewModels()
    private var binding: FragmentSongDetailsBinding by autoCleared()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSongDetailsBinding.inflate(inflater, container, false)
        subscribeToObservers()
        return binding.root
    }

    private fun subscribeToObservers() {
        songDetailsViewModel.getSongsList().observe(viewLifecycleOwner){
            getSongDetails()
        }
    }

    private fun getSongDetails(){
        val songId = SongDetailsFragmentArgs.fromBundle(requireArguments()).songId
        if(songId != -1) {
            val songDB = songDetailsViewModel.getSongById(songId)
            songDB?.let {
                Glide.with(requireContext())
                    .load(it.artistImageUrl)
                    .error(R.drawable.bg_music_default)
                    .into(binding.songImageImgV)
                binding.songNameTv.text = it.songName
                binding.artistNameTv.text = it.artistName
            }
        }
    }
}