package com.techcognics.procuremasster.presentation

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun showAuctionTimerNotification(context: Context, auctionNumber: String, minutes: Long) {
    val channelId = "auction_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Auction Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_dialog_info)
        .setContentTitle("Auction Alert")
        .setContentText("Auction #$auctionNumber: Only $minutes minutes left!")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    NotificationManagerCompat.from(context).notify(auctionNumber.hashCode(), notificationBuilder.build())
}
