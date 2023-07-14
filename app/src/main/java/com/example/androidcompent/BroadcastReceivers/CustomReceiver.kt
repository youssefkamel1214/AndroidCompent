package com.example.androidcompent.BroadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d("CustomReceiver","got here CustomReceiver")
        if(intent.hasExtra("custom"))
            Toast.makeText(context,intent.getStringExtra("custom"),Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context,"no intent data",Toast.LENGTH_SHORT).show()
    }
}