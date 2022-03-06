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

class ArtistsListAdapter(private val mContext: Context): RecyclerView.Adapter<ArtistsListAdapter.SongsListViewHolder>() {

    //Params
    var songsList = ArrayList<SongDB>()
    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_artists_list,parent,  false)
        return SongsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.artistNameTv.text = songsList[position].artistName

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
    class SongsListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val artistNameTv: TextView = itemView.findViewById(R.id.artists_list_row_artist_name_tv)
        val playlistDurationTv: TextView = itemView.findViewById(R.id.artists_list_row_playlist_duration_tv)
        val imageImgV: ImageView = itemView.findViewById(R.id.artists_list_row_image_imgV)
    }
}