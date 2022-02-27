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
import com.myspotify.utils.Constants
import com.myspotify.utils.Constants.MAX_LENGTH_ARTIST_NAME
import com.myspotify.utils.Constants.MAX_LENGTH_SONG_NAME
import com.myspotify.utils.Utils
import java.util.*

class ArtistsListAdapter(private val mContext: Context): RecyclerView.Adapter<ArtistsListAdapter.SongsListViewHolder>() {

    //Params
    var songsList = ArrayList<SongDB>()
    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_artists_list,parent,  false)
        return SongsListViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.artistNameTv.text = Utils.getStringWithValidLength(songsList[position].artistName,Constants.MAX_LENGTH_ARTIST_NAME_FOR_CARD_VIEW)

        Glide.with(mContext)
            .load(songsList[position].artistImageUrl)
            .placeholder(R.drawable.bg_music_default)
            .centerCrop()
            .into(holder.imageImgV)

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                listener.onClick(songsList[position].artistId)
            }
        }
    }

    override fun getItemCount(): Int { return songsList.size }

    //Listener
    fun interface OnItemClickListener { fun onClick(artistId: Int) }
    fun setOnClickListener(listener: OnItemClickListener) { this.listener = listener }

    //ViewHolder
    class SongsListViewHolder(itemView: View, var listener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        val artistNameTv: TextView = itemView.findViewById(R.id.artists_list_row_artist_name_tv)
        val playlistDurationTv: TextView = itemView.findViewById(R.id.artists_list_row_playlist_duration_tv)
        val imageImgV: ImageView = itemView.findViewById(R.id.artists_list_row_image_imgV)
    }


}