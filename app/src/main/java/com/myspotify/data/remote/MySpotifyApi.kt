package com.myspotify.data.remote

import com.myspotify.data.remote.models.Song
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MySpotifyApi {

    // https://api.deezer.com/track/3135556
    @GET("track/{id}")
    suspend fun getRecentTrack(@Path("id") id: Int): Call<Song>

    // https://api.deezer.com/playlist/1362526495/tracks?index=0&limit=10
    @GET("playlist/{id}/tracks?index=0")
    suspend fun getRecentAlbums(@Path("id") id: Int, @Query("limit") limit: Int): Call<Song>

    @GET("playlist/{id}/tracks?index=0")
    suspend fun getPlaylist(@Path("id") id: Int, @Query("limit") limit: Int): Response<Song>
}