package com.example.capstoneproject.ui.feature.item.minumobat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.capstoneproject.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val channelId = "medication_channel_id"
        val channelName = "Medication Reminder"
        val notificationId = intent.getIntExtra("notificationId", 0)
        val drugName = intent.getStringExtra("drugName")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.medicine)
            .setContentTitle("Time to take your medication")
            .setContentText("It's time to take $drugName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}