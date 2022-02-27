package com.myspotify.presentation.ui.fragments.favoriteSongsList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myspotify.databinding.FragmentFavoriteSongsListBinding
import com.myspotify.presentation.adapters.FavoriteSongsListAdapter
import com.myspotify.presentation.adapters.SwipeSongAdapter
import com.myspotify.utils.MyItemTouchHelper
import com.myspotify.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteSongsListFragment : Fragment() {

    private val favoriteSongsListViewModel: FavoriteSongsListViewModel by viewModels()
    private var binding: FragmentFavoriteSongsListBinding by autoCleared()
    private lateinit var mContext: Context
    private lateinit var songsRecyclerView: RecyclerView
    private lateinit var favoriteSongsListAdapter: FavoriteSongsListAdapter
    private lateinit var swipeRecyclerView: RecyclerView
    private lateinit var swipeSongAdapter: SwipeSongAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        binding = FragmentFavoriteSongsListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        initRecyclersView()
        listeners()
    }

    private fun initRecyclersView(){

        //AllSongs RecyclerView
        songsRecyclerView = binding.songsListRecyclerView
        songsRecyclerView.setHasFixedSize(true)
        val manager = LinearLayoutManager(mContext)
        songsRecyclerView.layoutManager = manager
        favoriteSongsListAdapter = FavoriteSongsListAdapter(mContext)
        val callback = MyItemTouchHelper(favoriteSongsListAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        favoriteSongsListAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(songsRecyclerView)
        songsRecyclerView.adapter = favoriteSongsListAdapter

        //SwipeSong RecyclerView
        swipeRecyclerView = binding.swipeSongRecyclerView
        swipeRecyclerView.setHasFixedSize(true)
        val swipeManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        swipeRecyclerView.layoutManager = swipeManager
        swipeSongAdapter = SwipeSongAdapter(mContext)
        swipeRecyclerView.adapter = swipeSongAdapter
    }

    private fun listeners() {

        favoriteSongsListViewModel.getSongsListFromDB().observe(viewLifecycleOwner, { data ->
            favoriteSongsListAdapter.favoriteSongsList.clear()
            favoriteSongsListAdapter.favoriteSongsList.addAll(data)
            favoriteSongsListAdapter.notifyDataSetChanged()
            swipeSongAdapter.songsList.clear()
            swipeSongAdapter.songsList.addAll(data)
            swipeSongAdapter.notifyDataSetChanged()
        })


        favoriteSongsListAdapter.setOnClickListener { songId ->

        }

        swipeSongAdapter.setOnClickListener { songId ->

        }
    }

    override fun onPause() {
        super.onPause()
        if(favoriteSongsListAdapter.isSomePositionChanged)
            favoriteSongsListViewModel.updateItemsPositionOnList(favoriteSongsListAdapter.favoriteSongsList)
    }
}