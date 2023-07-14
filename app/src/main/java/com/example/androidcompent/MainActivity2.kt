package com.example.androidcompent

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.androidcompent.Services.BoundService
import com.example.androidcompent.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    val binding:ActivityMain2Binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }
    var binded:Boolean=false
    var boundService: BoundService?=null
    val binserviceconn=object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binded=true
            boundService=(service as BoundService.MyBinder) .getService()
            boundService?.counter?.observe(this@MainActivity2) {
                binding.display.text = it.toString()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binded=false
            boundService?.apply {
                counter.removeObservers(this@MainActivity2)
                boundService=null
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bindser.setOnClickListener {
            if(!binded)
                startBinderService()
        }
        binding.unbind.setOnClickListener {
            if(binded)
                stopBoundedSerivce()
        }


    }

    private fun stopBoundedSerivce() {
        unbindService(binserviceconn)
        binded=false
    }

    private fun startBinderService() {
        val intent = Intent(this, BoundService::class.java)
        startService(intent)
        bindService(intent,binserviceconn, BIND_AUTO_CREATE)

    }

    override fun onDestroy() {
        super.onDestroy()
        if(binded)
         stopBoundedSerivce()
    }
}
