package com.example.androidcompent.BroadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
// dowload
// cooncetoin check

class ConnectionReciver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d("ConnectionReciver","got here")
        Toast.makeText(context,"some thing happend",Toast.LENGTH_LONG).show()
    }
}