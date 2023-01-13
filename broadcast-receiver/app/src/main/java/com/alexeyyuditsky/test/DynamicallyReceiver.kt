package com.alexeyyuditsky.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

class DynamicallyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val formatter: Format = SimpleDateFormat("hh:mm:ss a", Locale.ROOT)
        val msgStr = "Текущее время: ${formatter.format(Date())}"
        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show()
    }

}