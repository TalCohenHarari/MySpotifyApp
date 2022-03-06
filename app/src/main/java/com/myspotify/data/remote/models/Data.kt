package com.myspotify.data.remote.models

import com.google.gson.annotations.SerializedName

data class Data(
    val album: Album,
    val artist: Artist,
    val duration: Int,
    val explicit_content_cover: Int,
    val explicit_content_lyrics: Int,
    val explicit_lyrics: Boolean,
    val id: Int,
    val link: String,
    val md5_image: String,
    @SerializedName("preview")
    val previewSong: String,
    val rank: Int,
    val readable: Boolean,
    val time_add: Int,
    val title: String,
    val title_short: String,
    val title_version: String,
    val type: String
)