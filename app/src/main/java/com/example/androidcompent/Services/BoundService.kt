package com.example.androidcompent.Services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.util.Timer
import java.util.TimerTask

class BoundService : Service() {
    private val localBinder: IBinder = MyBinder()
    private val tag="BoundService"
    var counter =MutableLiveData<Int>(0)
    var timer:Timer?=Timer()
    override fun onBind(intent: Intent): IBinder {
        Log.d(tag,"client has been connected ")
        timer?.schedule(object :TimerTask(){
            override fun run() {
                counter.postValue(counter.value!!+1)
            }
        },0,1000)
        return localBinder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(tag,"Rebind")
        timer=Timer()
        timer?.schedule(object :TimerTask(){
            override fun run() {
                counter.postValue(counter.value!!+1)
            }
        },0,1000)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(tag,"there no clients")
        timer?.cancel()
        counter.postValue(0)
        timer=null
        return true
    }

    inner class MyBinder:Binder(){
        fun getService(): BoundService {
            return this@BoundService
        }
    }
}