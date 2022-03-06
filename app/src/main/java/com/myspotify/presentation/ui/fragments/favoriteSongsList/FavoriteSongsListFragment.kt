package com.myspotify.presentation.ui.fragments.favoriteSongsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.myspotify.databinding.FragmentFavoriteSongsListBinding
import com.myspotify.presentation.adapters.FavoriteSongsListAdapter
import com.myspotify.presentation.adapters.SwipeSongAdapter
import com.myspotify.presentation.dialogs.SongBottomSheetDialogFragment
import com.myspotify.utils.recyclerViewTools.MyItemTouchHelper
import com.myspotify.utils.recyclerViewTools.SnapHelperOneByOne
import com.myspotify.utils.Utils
import com.myspotify.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteSongsListFragment : Fragment() {

    private val favoriteSongsListViewModel: FavoriteSongsListViewModel by viewModels()
    private var binding: FragmentFavoriteSongsListBinding by autoCleared()
    private lateinit var songsRecyclerView: RecyclerView
    private lateinit var favoriteSongsListAdapter: FavoriteSongsListAdapter
    private lateinit var swipeRecyclerView: RecyclerView
    private lateinit var swipeSongAdapter: SwipeSongAdapter
    private var itemBottomSheetDialog: SongBottomSheetDialogFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        binding = FragmentFavoriteSongsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclersView()
        subscribeToObservers()
        listeners()
    }

    private fun initRecyclersView(){

        //AllSongs RecyclerView
        songsRecyclerView = binding.songsListRecyclerView
        songsRecyclerView.setHasFixedSize(true)
        val manager = LinearLayoutManager(context)
        songsRecyclerView.layoutManager = manager
        favoriteSongsListAdapter = FavoriteSongsListAdapter(context!!)
        val callback = MyItemTouchHelper(favoriteSongsListAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        favoriteSongsListAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(songsRecyclerView)
        songsRecyclerView.adapter = favoriteSongsListAdapter

        //SwipeSong RecyclerView
        swipeRecyclerView = binding.swipeSongRecyclerView
        swipeRecyclerView.setHasFixedSize(true)
        val swipeManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        swipeRecyclerView.layoutManager = swipeManager
        swipeSongAdapter = SwipeSongAdapter(context!!)
        swipeRecyclerView.adapter = swipeSongAdapter
        val linearSnapHelper: LinearSnapHelper = SnapHelperOneByOne()
        linearSnapHelper.attachToRecyclerView(swipeRecyclerView)
    }

    private fun subscribeToObservers() {
        favoriteSongsListViewModel.getSongsListFromDB().observe(viewLifecycleOwner, { data ->
            favoriteSongsListAdapter.notifyNewList(data)
            swipeSongAdapter.notifyNewList(data)
        })
    }

    private fun listeners() {
        favoriteSongsListAdapter.setOnClickListener { songId ->
            openSongBottomSheetDialogFragment(songId)

        }

        swipeSongAdapter.setOnClickListener { songId ->

        }
    }

    private fun openSongBottomSheetDialogFragment(songId: Int) {
        val songDB = favoriteSongsListViewModel.getSongById(songId)
        itemBottomSheetDialog = SongBottomSheetDialogFragment.newInstance(songDB!!)
        itemBottomSheetDialog!!.setOnClickListener(object:
            SongBottomSheetDialogFragment.OnActionClickListener{
            override fun onClick() {
                favoriteSongsListViewModel.updateSong(songDB)
                itemBottomSheetDialog!!.dismiss()
            }

            override fun onSharedClick() {
               Utils.shareSongUrl(requireContext() ,songDB.songUrl)
            }

        })

        itemBottomSheetDialog!!.show(requireActivity().supportFragmentManager,"SongBottomSheetDialogFragment")
    }

    override fun onPause() {
        super.onPause()
        itemBottomSheetDialog?.dismiss()

        if(favoriteSongsListAdapter.isSomePositionChanged)
            favoriteSongsListViewModel.updateItemsPositionOnList(favoriteSongsListAdapter.getFavoriteSongsList())
    }
}