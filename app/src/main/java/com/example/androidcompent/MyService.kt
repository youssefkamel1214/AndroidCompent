package com.example.androidcompent

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.Timer
import java.util.TimerTask


class MyService : Service() {
    val CHANNEL_ID = "ForegroundServiceChannel"
     var counter:CountDownTimer?=null
    override fun onDestroy() {
        super.onDestroy()
        counter?.cancel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent!!.getIntExtra("timer",0)
        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_MUTABLE
        )
        counter= object : CountDownTimer(input.toLong(),1000) {


            override fun onTick(millisUntilFinished: Long) {
                Log.d("MyService","remining time ${millisUntilFinished}")
                val notification: Notification = NotificationCompat.Builder(this@MyService, CHANNEL_ID)
                    .setContentTitle("Foreground Service")
                    .setContentText(millisUntilFinished.toString())
                    .setSmallIcon(R.drawable.baseline_app_blocking_24)
                    .setContentIntent(pendingIntent)
                    .build()
                startForeground(2, notification)
            }
            override fun onFinish() {
               stopSelf()
            }
        }
        counter?.start()
        val threadName = Thread.currentThread().name
        Log.d("MyService",threadName)
        //do some work then stop it
        //stopSelf();
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return  null
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

}