package com.myspotify

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.myspotify.utils.Constants.NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        //Define channel 1
        val channelOnGettingNewSongsFromServer = NotificationChannel(
            NOTIFICATION_CHANNEL_ID, // The channel id
            "New Songs", // What the user will see on app settings as the notify title
            NotificationManager.IMPORTANCE_HIGH // Level of notification
        )
        // We can't change the color or sound etc since we create the channel. We do can change only the channel name and description.
        channelOnGettingNewSongsFromServer.description = "Notifying each time that getting new songs from the server" // What the user will see on app settings as the notify description


        //Creating the channels
        val manager: NotificationManager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channelOnGettingNewSongsFromServer)
    }
}