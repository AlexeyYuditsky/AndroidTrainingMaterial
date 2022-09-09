package com.alexeyyuditsky.test

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dynamicallyReceiver = DynamicallyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.sendStaticallyButton.setOnClickListener { sendMessageStatically() }

        binding.sendDynamicallyButton.setOnClickListener { sendMessageDynamically() }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(dynamicallyReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(dynamicallyReceiver)
    }

    private fun sendMessageStatically() {
        val intent = Intent().apply {
            action = StaticallyReceiver.MY_ACTION
            putExtra(StaticallyReceiver.KEY_BROADCAST_MESSAGE, StaticallyReceiver.ALARM_MESSAGE)
            addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        }
        sendBroadcast(intent)
    }

    private fun sendMessageDynamically() {
        DynamicallyReceiver()
    }

}