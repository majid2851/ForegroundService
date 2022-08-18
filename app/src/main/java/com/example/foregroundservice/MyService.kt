package com.example.foregroundservice

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat


class  MyService() : Service() {

    val ACTION_STOP_SERVICE = "STOP"

    val channelId = "ChannelId1"
    private var mInstance: MyService? = null
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotificationChannel()

//        ActionCable.createConsumer(uri)
//        ActionCable.createConsumer("")


        if (ACTION_STOP_SERVICE == intent.action) {
            Log.d("mag2851", "called to cancel service")
            stopSelf()
        }
        val stopSelf = Intent(this, MyService::class.java)

        stopSelf.action = ACTION_STOP_SERVICE

        val pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_MUTABLE or
                PendingIntent.FLAG_CANCEL_CURRENT)
        val intent1 = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_MUTABLE
        or 0)
        var notification: Notification? = null



        notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("title")
            .setContentText("text")
            .setSmallIcon(R.drawable.ic_action_name)
            .addAction(R.drawable.ic_close,"Close", pStopSelf)
            .setSound(null)
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
        MainActivity.tv.setText("غیرفعال")
        MainActivity.tv.setTextColor(Color.RED)
        super.onDestroy()
    }


    override fun onCreate() {
        mInstance = this
    }


}


