package com.myspotify.presentation.ui.fragments.artistsList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myspotify.databinding.FragmentArtistsListBinding
import com.myspotify.presentation.adapters.ArtistsListAdapter
import com.myspotify.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtistsListFragment : Fragment() {

    private val songsListViewModel: ArtistsListViewModel by viewModels()
    private var binding: FragmentArtistsListBinding by autoCleared()
    private lateinit var mContext: Context
    private lateinit var artistsRecyclerView: RecyclerView
    private lateinit var artistsListAdapter: ArtistsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArtistsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        initRecyclerView()
        listeners()
    }

    private fun initRecyclerView(){

        artistsRecyclerView = binding.artistsListRecyclerView
        artistsRecyclerView.setHasFixedSize(true)
        val manager = GridLayoutManager(mContext, 2)
        artistsRecyclerView.layoutManager = manager
        artistsListAdapter = ArtistsListAdapter(mContext)
        artistsRecyclerView.adapter = artistsListAdapter
    }

    private fun listeners() {

        songsListViewModel.getSongsListFromDB().observe(viewLifecycleOwner, { data ->
            artistsListAdapter.songsList.clear()
            artistsListAdapter.songsList.addAll(data)
            artistsListAdapter.notifyDataSetChanged()
        })

        artistsListAdapter.setOnClickListener { artistId ->
            val action = ArtistsListFragmentDirections.actionNavArtistsListFragmentToNavArtistSongsListFragment(artistId)
            findNavController().navigate(action)
        }

        binding.iconFavoriteImgV.setOnClickListener {
            val action = ArtistsListFragmentDirections.actionNavArtistsListFragmentToNavFavoriteSongsListFragment()
            findNavController().navigate(action)
        }
    }
}