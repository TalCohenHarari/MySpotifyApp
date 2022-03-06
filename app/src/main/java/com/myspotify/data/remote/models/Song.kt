package com.myspotify.data.remote.models

data class Song(
    val checksum: String,
    val data: List<Data>,
    val next: String,
    val total: Int
)