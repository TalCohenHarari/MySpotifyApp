package com.myspotify.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myspotify.R
import com.myspotify.data.local.entity.SongDB
import java.util.*

class SwipeSongAdapter(private val mContext: Context): RecyclerView.Adapter<SwipeSongAdapter.SongsListViewHolder>() {

    //Params
    private var songsList = ArrayList<SongDB>()
    private lateinit var listener: OnItemClickListener

    fun notifyNewList(data: List<SongDB>) {
        songsList.clear()
        songsList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_swipe_song,parent,  false)
        return SongsListViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.songNameTv.text = songsList[position].songName
        holder.artistNameTv.text = songsList[position].artistName
        Glide.with(mContext)
            .load(songsList[position].artistImageUrl)
            .placeholder(R.drawable.bg_music_default)
            .centerCrop()
            .into(holder.imageImgV)

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                listener.onClick(songsList[position].id)
            }
        }
    }

    override fun getItemCount(): Int { return songsList.size }

    //Listener
    fun interface OnItemClickListener { fun onClick(songId: Int) }
    fun setOnClickListener(listener: OnItemClickListener) { this.listener = listener }

    //ViewHolder
    class SongsListViewHolder(itemView: View, var listener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        val songNameTv: TextView = itemView.findViewById(R.id.swipe_song_row_song_name_tv)
        val artistNameTv: TextView = itemView.findViewById(R.id.swipe_song_row_artist_name_tv)
        val imageImgV: ImageView = itemView.findViewById(R.id.swipe_song_row_image_imgV)
        val playPauseImgV: ImageView = itemView.findViewById(R.id.swipe_song_row_play_pause_imgV)
    }
}