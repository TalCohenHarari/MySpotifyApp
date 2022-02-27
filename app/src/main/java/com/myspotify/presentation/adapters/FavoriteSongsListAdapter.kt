package com.myspotify.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myspotify.R
import com.myspotify.data.local.entity.SongDB
import com.myspotify.utils.ItemTouchHelperAdapter
import java.util.*
import android.view.GestureDetector
import android.view.MotionEvent
import com.myspotify.utils.Constants
import com.myspotify.utils.Utils


class FavoriteSongsListAdapter(private val mContext: Context): RecyclerView.Adapter<FavoriteSongsListAdapter.SongsListViewHolder>(), ItemTouchHelperAdapter {

    //Params
    var favoriteSongsList = ArrayList<SongDB>()
    var isSomePositionChanged = false

    //Click move and swipe items
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var listener: OnItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_songs_list_2,parent,  false)
        return SongsListViewHolder(view,listener,itemTouchHelper,favoriteSongsList)
    }

    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.songNameTv.text = Utils.getStringWithValidLength(favoriteSongsList[position].songName,Constants.MAX_LENGTH_SONG_NAME)
        holder.artistNameTv.text = Utils.getStringWithValidLength(favoriteSongsList[position].artistName,Constants.MAX_LENGTH_ARTIST_NAME)
        Glide.with(mContext)
            .load(favoriteSongsList[position].artistImageUrl)
            .placeholder(R.drawable.bg_music_default)
            .centerCrop()
            .into(holder.imageImgV)
    }

    override fun getItemCount(): Int { return favoriteSongsList.size }

    //Listener
    fun interface OnItemListener { fun onClick(songId: Int) }
    fun setOnClickListener(listener: OnItemListener) { this.listener = listener }

    //ViewHolder
    class SongsListViewHolder(itemView: View, var listener: OnItemListener, private val itemTouchHelper: ItemTouchHelper, val songsList: ArrayList<SongDB>):RecyclerView.ViewHolder(itemView) ,View.OnTouchListener, GestureDetector.OnGestureListener{
        val songNameTv: TextView = itemView.findViewById(R.id.songs_list_row_song_name_tv)
        val artistNameTv: TextView = itemView.findViewById(R.id.songs_list_row_artist_name_tv)
        val imageImgV: ImageView = itemView.findViewById(R.id.songs_list_row_image_imgV)
        val shadowView: View = itemView.findViewById(R.id.songs_list_row_shadow_view)
        val optionsImgV: ImageView = itemView.findViewById(R.id.songs_list_row_options_imgV)

        //Move and Swipe items
        private val mGestureDetector: GestureDetector = GestureDetector(itemView.context, this)
        init {
            itemView.setOnTouchListener(this)
        }
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            mGestureDetector.onTouchEvent(event)
            return true
        }
        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }
        override fun onShowPress(e: MotionEvent?) {}
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            listener.onClick(songsList[adapterPosition].id)
            return true
        }
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float, ): Boolean {
            return true
        }
        override fun onLongPress(e: MotionEvent?) {
            itemTouchHelper.startDrag(this)
        }
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float, ): Boolean {
            return false
        }
    }

    //Move and Swipe items
    fun setTouchHelper(itemTouchHelper: ItemTouchHelper){
        this.itemTouchHelper = itemTouchHelper
    }
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        var songDBFrom = favoriteSongsList[fromPosition]
        favoriteSongsList.remove(songDBFrom)
        favoriteSongsList.add(toPosition,songDBFrom)
        isSomePositionChanged = true

        for (i in 0 until favoriteSongsList.size) {
            favoriteSongsList[i].positionOnList = i
        }

        notifyItemMoved(fromPosition,toPosition)
    }
    override fun onItemSwiped(position: Int) {
        favoriteSongsList.removeAt(position)
        notifyItemRemoved(position)
    }
}