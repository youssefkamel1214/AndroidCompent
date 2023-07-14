package com.example.androidcompent.Services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.androidcompent.R
import java.text.SimpleDateFormat
import java.util.Calendar

class TimerService : Service() {
    val tag = "TimerService"
    val channelid = "Timer"
    val descrption = "this channel used for creating timer"
    var counter: CountDownTimer? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(tag, "print i got here")
            val time = intent?.getIntExtra("timercount", 0)
            createNotificationchannel()
            counter = object : CountDownTimer(time!!.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.d(tag, millisUntilFinished.toString())
                    val mmss=SimpleDateFormat("mm:ss")
                    val cal=Calendar.getInstance()
                    cal.timeInMillis=millisUntilFinished
                    val notification =
                        NotificationCompat.Builder(this@TimerService, channelid).setContentTitle("Timer")
                            .setContentText(" time remining ${mmss.format(cal.time)}")
                            .setSmallIcon(R.drawable.baseline_app_blocking_24)
                            .build()
                    startForeground(2, notification)
                }

                override fun onFinish() {
                    stopSelf()
                }
            }
            counter?.start()
        }
        return START_NOT_STICKY
    }


    private fun createNotificationchannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelid, descrption, NotificationManager.IMPORTANCE_DEFAULT)
            val manger = getSystemService(NotificationManager::class.java)
            manger.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}