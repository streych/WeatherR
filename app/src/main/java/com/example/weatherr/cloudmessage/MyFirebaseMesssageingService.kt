package com.example.weatherr.cloudmessage

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weatherr.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

public const val CHANEL_ID = "CHANNEL_MAIN"

class MyFirebaseMesssageingService : FirebaseMessagingService() {

    private val notificationId = 10

    override fun onMessageReceived(remoteMesssage: RemoteMessage) {
        val title = remoteMesssage.notification?.title ?: "Title"
        val body = remoteMesssage.notification?.body ?: "Body"

        showNotification(title, body)
    }

    private fun showNotification(title: String, message: String) {

        val notifcationBuilder = NotificationCompat.Builder(applicationContext, CHANEL_ID).apply {
            setSmallIcon(R.drawable.ic_baseline_outlet_24)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        notificationManager.notify(notificationId, notifcationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "Channel name"
        val descriptionText = "Channel description"
        val impotance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANEL_ID, channelName, impotance).apply {
            description = descriptionText
        }

        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        Log.d("FIREBASE", token)
    }
}