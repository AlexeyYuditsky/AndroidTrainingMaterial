package com.alexeyyuditsky.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val toastMessage = when (intent?.action) {
            Intent.ACTION_POWER_CONNECTED -> "Power connected!"
            Intent.ACTION_POWER_DISCONNECTED -> "Power disconnected!"
            else -> "unknown intent action"
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

}
