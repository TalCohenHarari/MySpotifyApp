package com.myspotify.presentation.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myspotify.R
import com.myspotify.data.local.entity.SongDB
import com.myspotify.databinding.BottomSheetDialogFragmentSongBinding
import com.myspotify.utils.autoCleared

class SongBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var binding: BottomSheetDialogFragmentSongBinding by autoCleared()
    private lateinit var listener: OnActionClickListener
    private lateinit var songDB: SongDB

    companion object{
        fun newInstance(songDB: SongDB): SongBottomSheetDialogFragment {
            val dialogFragment = SongBottomSheetDialogFragment()
            dialogFragment.arguments = Bundle()
            dialogFragment.songDB = songDB
            return dialogFragment
        }
    }

    interface OnActionClickListener {
        fun onClick()
        fun onSharedClick()
    }
    fun setOnClickListener(listener: OnActionClickListener) { this.listener = listener }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetDialogFragmentSongBinding.inflate(inflater, container, false)
        binding.root.context.setTheme(R.style.BottomSheetDialogTheme)
        Glide.with(this).load(songDB.artistImageUrl).into(binding.imageImgV)
        binding.songNameTv.text = songDB.songName
        binding.artistNameTv.text = songDB.artistName
        if(songDB.isFavorite){
            binding.title.text = getString(R.string.remove_from_favorite)
            binding.favoriteIconImgV.setImageResource(R.drawable.ic_favorite_green)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteContainerView.setOnClickListener { listener.onClick() }
        binding.shareContainerView.setOnClickListener { listener.onSharedClick() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

}