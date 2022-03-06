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
import java.util.*
import com.myspotify.utils.Utils


class ArtistsSongsListAdapter(private val mContext: Context): RecyclerView.Adapter<ArtistsSongsListAdapter.SongsListViewHolder>() {

    //Params
    var artistSongsList = ArrayList<SongDB>()
    private lateinit var listener: OnItemOptionsClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_songs_list_2,parent,  false)
        return SongsListViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.songNameTv.text = artistSongsList[position].songName
        holder.artistNameTv.text = artistSongsList[position].artistName
        if(artistSongsList[position].isFavorite){
            holder.favoriteIconImgV.visibility = View.VISIBLE
        }else{
            holder.favoriteIconImgV.visibility = View.INVISIBLE
        }
        Glide.with(mContext)
            .load(artistSongsList[position].artistImageUrl)
            .error(R.drawable.bg_music_default)
            .centerCrop()
            .into(holder.imageImgV)

        holder.optionsImgV.setOnClickListener {
            listener.onClick(artistSongsList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return artistSongsList.size
    }

    //Listener
    fun interface OnItemOptionsClickListener { fun onClick(songId: Int) }
    fun setOnClickListener(listener: OnItemOptionsClickListener) { this.listener = listener }

    //ViewHolder
    class SongsListViewHolder(itemView: View, var listener: OnItemOptionsClickListener):RecyclerView.ViewHolder(itemView){
        val songNameTv: TextView = itemView.findViewById(R.id.songs_list_row_song_name_tv)
        val artistNameTv: TextView = itemView.findViewById(R.id.songs_list_row_artist_name_tv)
        val imageImgV: ImageView = itemView.findViewById(R.id.songs_list_row_image_imgV)
        val shadowView: View = itemView.findViewById(R.id.songs_list_row_shadow_view)
        val optionsImgV: ImageView = itemView.findViewById(R.id.songs_list_row_options_imgV)
        val favoriteIconImgV: ImageView = itemView.findViewById(R.id.songs_list_row_favorite_imgV)
    }
}