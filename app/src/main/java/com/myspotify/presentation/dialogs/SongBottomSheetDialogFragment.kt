package com.myspotify.presentation.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myspotify.R
import com.myspotify.databinding.BottomSheetDialogFragmentSongBinding
import com.myspotify.presentation.adapters.ArtistsSongsListAdapter
import com.myspotify.utils.autoCleared

class SongBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var binding: BottomSheetDialogFragmentSongBinding by autoCleared()
    private lateinit var listener: OnActionClickListener
    private var isFavorite = false

    companion object{
        fun newInstance(isFavorite:Boolean): SongBottomSheetDialogFragment {
            val dialogFragment = SongBottomSheetDialogFragment()
            dialogFragment.arguments = Bundle()
            dialogFragment.isFavorite = isFavorite
            return dialogFragment
        }
    }

    fun interface OnActionClickListener { fun onClick() }
    fun setOnClickListener(listener: OnActionClickListener) { this.listener = listener }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetDialogFragmentSongBinding.inflate(inflater, container, false)
        binding.root.context.setTheme(R.style.BottomSheetDialogTheme)
        if(isFavorite){
            binding.title.text = getString(R.string.remove_from_favorite)
            binding.description.text = getString(R.string.remove_this_song_from_your_favorite)
            binding.favoriteIconImgV.setImageResource(R.drawable.ic_favorite_green)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteContainerView.setOnClickListener { listener.onClick() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

}