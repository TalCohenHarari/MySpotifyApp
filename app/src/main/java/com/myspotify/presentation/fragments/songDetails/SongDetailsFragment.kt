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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = SongDetailsFragmentArgs.fromBundle(requireArguments()).position
        if(position != -1) {
            val songDB = songDetailsViewModel.getSongById(position)
                songDB?.apply {
                    Glide.with(requireContext()).load(this.artistImageUrl).error(R.drawable.bg_music_default).into(binding.songImageImgV)
                    binding.songNameTv.text = this.songName
                    binding.artistNameTv.text = this.artistName
                }
        }
    }
}