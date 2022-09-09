package com.alexeyyuditsky.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class StaticallyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(KEY_BROADCAST_MESSAGE) ?: "null"
        Toast.makeText(context, "Обнаружено сообщение: $message", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val KEY_BROADCAST_MESSAGE = "KEY_BROADCAST_MESSAGE"
        const val MY_ACTION = "com.alexeyyuditsky.test.action.MY_ACTION"
        const val ALARM_MESSAGE = "Срочно пришлите кота!"
    }

}