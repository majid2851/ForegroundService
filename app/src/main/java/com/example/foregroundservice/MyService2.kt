package com.example.foregroundservice

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder


class MyService2 : Service() {
    val channelId = "ChannelId1"
    private var mInstance: MyService2? = null
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val intent1 = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0)
        var notification: Notification? = null

        notification = Notification.Builder(this, channelId)
            .setContentTitle("title")
            .setContentText("text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent).build()
        startForeground(1, notification)
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notif = NotificationChannel(channelId, "foregroundNotification", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notif)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        mInstance = null
        super.onDestroy()
    }

    fun isServiceCreated(): Boolean {
        return try {
            // If instance was not cleared but the service was destroyed an Exception will be thrown
            mInstance != null && mInstance!!.ping()
        } catch (e: NullPointerException) {
            // destroyed/not-started
            false
        }
    }

    private fun ping(): Boolean {
        return true
    }

    override fun onCreate() {
        mInstance = this
    }

}