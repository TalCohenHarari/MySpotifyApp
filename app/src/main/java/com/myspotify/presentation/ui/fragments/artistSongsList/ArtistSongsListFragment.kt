package com.myspotify.presentation.ui.fragments.artistSongsList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myspotify.R
import com.myspotify.databinding.FragmentArtistSongsListBinding
import com.myspotify.presentation.adapters.ArtistsSongsListAdapter
import com.myspotify.presentation.dialogs.SongBottomSheetDialogFragment
import com.myspotify.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtistSongsListFragment : Fragment() {

    private val artistSongsListViewModel: ArtistSongsListViewModel by viewModels()
    private var binding: FragmentArtistSongsListBinding by autoCleared()
    private lateinit var mContext: Context
    private lateinit var artistSongsRecyclerView: RecyclerView
    private lateinit var artistSongsListAdapter: ArtistsSongsListAdapter
    private var itemBottomSheetDialog: SongBottomSheetDialogFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArtistSongsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        initRecyclersView()
        listeners()
    }

    private fun initRecyclersView(){

        val artistId = ArtistSongsListFragmentArgs.fromBundle(requireArguments()).artistId
        artistSongsListViewModel.artistId = artistId

        //AllSongs RecyclerView
        artistSongsRecyclerView = binding.artistSongsListRecyclerView
        artistSongsRecyclerView.setHasFixedSize(true)
        val manager = LinearLayoutManager(mContext)
        artistSongsRecyclerView.layoutManager = manager
        artistSongsListAdapter = ArtistsSongsListAdapter(mContext)
        artistSongsRecyclerView.adapter = artistSongsListAdapter
    }

    private fun listeners() {
        artistSongsListViewModel.getArtistSongsListFromDB().observe(viewLifecycleOwner, { data ->
            artistSongsListAdapter.artistSongsList.clear()
            artistSongsListAdapter.artistSongsList.addAll(data)
            artistSongsListAdapter.notifyDataSetChanged()
            if(data.isNotEmpty()) {
                Glide.with(mContext).load(data[0].artistImageUrl).error(R.drawable.bg_music_default).into(binding.headerImageImgV)
                binding.titleTv.text = data[0].artistName
                binding.titleTvSmall.text = data[0].artistName
            }
        })

        artistSongsListAdapter.setOnClickListener { songId ->
            openSongBottomSheetDialogFragment(songId)
        }
    }
    private fun openSongBottomSheetDialogFragment(songId: Int) {
        val songDB = artistSongsListViewModel.getSongById(songId)
        itemBottomSheetDialog = SongBottomSheetDialogFragment.newInstance(songDB!!.isFavorite)
        itemBottomSheetDialog!!.setOnClickListener{
            artistSongsListViewModel.updateSong(songDB)
            itemBottomSheetDialog!!.dismiss()
        }
        itemBottomSheetDialog!!.show(requireActivity().supportFragmentManager,"SongBottomSheetDialogFragment")
    }

    override fun onPause() {
        super.onPause()
        itemBottomSheetDialog?.dismiss()
    }
}