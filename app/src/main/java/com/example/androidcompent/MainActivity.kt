package com.example.androidcompent

import android.app.AlarmManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidcompent.BroadcastReceivers.ConnectionReciver
import com.example.androidcompent.BroadcastReceivers.CustomReceiver
import com.example.androidcompent.Services.MyService
import com.example.androidcompent.Services.TimerService
import com.example.androidcompent.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var customReceiver: CustomReceiver

    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        customReceiver = CustomReceiver()
        IntentFilter("android.net.conn.CONNECTIVITY_CHANGE").also {
            registerReceiver(customReceiver, it)
        }
        binding.startservice.setOnClickListener {
            val intent = Intent(this, TimerService::class.java)
            intent.putExtra("timercount", 15 * 1000)
            startForegroundService(intent)

        }
        binding.stopservice.setOnClickListener {
            val intent = Intent(this, TimerService::class.java)
            stopService(intent)
        }
        binding.button3.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(customReceiver)
    }
}


//       binding.startservice.setOnClickListener {
//           startForgroundService()
//       }
//       binding.stopservice.setOnClickListener {
//           stopForgroundservice()
//       }
//        binding.button3.setOnClickListener {
////            startActivity(Intent(this,MainActivity2::class.java))
//            val intent=Intent("customBroadcast")
//            intent.putExtra("custom","customReciver")
//            sendBroadcast(intent)
//        }
//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(customReceiver)
//    }
//    private fun stopForgroundservice() {
//        val intent = Intent(this, MyService::class.java)
//        stopService(intent)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun startForgroundService() {
//        val intent = Intent(this, MyService::class.java)
//        intent.putExtra("timer",5*1000)
//        ContextCompat.startForegroundService(this,intent)
//    }
