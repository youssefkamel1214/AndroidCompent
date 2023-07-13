package com.example.androidcompent

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidcompent.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var  connectionReciver:ConnectionReciver
    val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        connectionReciver= ConnectionReciver()
        IntentFilter("android.net.conn.CONNECTIVITY_CHANGE").also {
            registerReceiver(connectionReciver,it)
        }
       binding.startservice.setOnClickListener {
           startservice()
       }
       binding.stopservice.setOnClickListener {
           stopservice()
       }
    }

    private fun stopservice() {
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startservice() {
        val intent = Intent(this, MyService::class.java)
        intent.putExtra("timer",50*1000)
        ContextCompat.startForegroundService(this,intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectionReciver)
    }
}