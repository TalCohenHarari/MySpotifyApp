package com.myspotify.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.myspotify.R
import com.myspotify.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.myspotify.utils.Constants.NOTIFICATION_ID

object Utils {
    fun makeToast(context: Context, msg:String){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }

    fun makeSnackbar(view: View, msg:String){
        Snackbar.make(view,msg, Snackbar.LENGTH_SHORT).show()
    }

    fun makeSnackbarWithAction(context: Context, view: View, actionName: String, actionColor : Int, msg:String){
        val snackbar = Snackbar.make(view,msg, Snackbar.LENGTH_SHORT)
        snackbar.setAction(actionName, View.OnClickListener { snackbar.dismiss() })
            .setActionTextColor(ContextCompat.getColor(context, actionColor))
            .show()
    }

    fun pushNotification(context: Context, title: String, msg: String, iconRes: Int? = null){
        val build = NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(msg)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        //For custom notification with my own layout
//        val notificationLayout = RemoteViews( packageName, R.layout.notification_small)
//        build.setContent(notificationLayout)

        if(iconRes != null) build.setSmallIcon(iconRes) else build.setSmallIcon(R.drawable.ic_notification_yellow)

        NotificationManagerCompat.from(context).notify( NOTIFICATION_ID , build.build())
    }

    fun getStringWithValidLength(artistName: String, maxLength: Int): String{
        return if(artistName.length > maxLength) artistName.substring(0, maxLength) + "..."
        else artistName
    }
}