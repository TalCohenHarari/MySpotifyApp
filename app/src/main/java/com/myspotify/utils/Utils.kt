package com.myspotify.utils

import android.content.Context
import android.content.Intent
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
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
    }

    fun makeSnackbar(view: View, msg:String){
        Snackbar.make(view,msg, Snackbar.LENGTH_LONG).show()
    }

    fun makeSnackbarWithAction(context: Context, view: View, actionName: String, actionColor : Int, msg:String){
        val snackbar = Snackbar.make(view,msg, Snackbar.LENGTH_LONG)
        snackbar.setAction(actionName, View.OnClickListener { snackbar.dismiss() })
            .setActionTextColor(ContextCompat.getColor(context, actionColor))
            .show()
    }

    fun pushNotificationMessage(context: Context, title: String, text: String, iconRes: Int? = null){

        //For custom notification with our own layout
//        val smallLayout = RemoteViews( context.packageName, R.layout.notification_small)
//        smallLayout.setTextViewText(R.id.notification_small_title, title)
//        smallLayout.setTextViewText(R.id.notification_small_text, text)
//        val bigLayout = RemoteViews( context.packageName, R.layout.notification_big)

        val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setCustomContentView(smallLayout)
//            .setCustomBigContentView(bigLayout)
//            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        if(iconRes != null) {
            build.setSmallIcon(iconRes)
        }
        else {
            build.setSmallIcon(R.drawable.ic_notification_yellow)
        }

        NotificationManagerCompat.from(context).notify( NOTIFICATION_ID , build.build())
    }

    fun shareSongUrl(context: Context, url: String){
        val shareIntent = Intent.createChooser(
            Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
            }
            , null)

        context.startActivity(shareIntent)
    }
}