package com.alexeyyuditsky.test

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.alexeyyuditsky.test.CustomReceiver.Companion.ACTION_CUSTOM_BROADCAST
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mReceiver = CustomReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registerPowerReceiver()
        registerLocaleReceiver()
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver)
        super.onDestroy()
    }

    private fun registerPowerReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(mReceiver, filter)
    }

    private fun registerLocaleReceiver() {
        binding.sendBroadcast.setOnClickListener {
            val customBroadcastIntent = Intent(ACTION_CUSTOM_BROADCAST)
            customBroadcastIntent.putExtra("random", Random.nextInt(1, 20))
            LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent)
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, IntentFilter(ACTION_CUSTOM_BROADCAST))
    }

}