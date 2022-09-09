package com.alexeyyuditsky.test

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) {
        Log.d("MyLog", "Permission granted: $it")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.permissionButton.setOnClickListener { getPermissionReadCallLog() }

        binding.queryButton.setOnClickListener { sendQueryToReadCallDB() }
    }

    private fun getPermissionReadCallLog() {
        requestPermissionLauncher.launch(Manifest.permission.READ_CALL_LOG)
    }

    @SuppressLint("Range")
    private fun sendQueryToReadCallDB() {
        contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            CallLog.Calls.DEFAULT_SORT_ORDER
        )?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(CallLog.Calls.CACHED_NAME))
                val cacheNumber = it.getString(it.getColumnIndex(CallLog.Calls.CACHED_NUMBER_LABEL))
                val number = it.getString(it.getColumnIndex(CallLog.Calls.NUMBER))
                val dateTimeMillis = it.getLong(it.getColumnIndex(CallLog.Calls.DATE))
                val durationMillis = it.getLong(it.getColumnIndex(CallLog.Calls.DURATION))
                val callType = it.getInt(it.getColumnIndex(CallLog.Calls.TYPE))
                Log.d(
                    "MyLog", "name = $name\n" + "cacheNumber = $cacheNumber\n" +
                            "number = $number\n" + "dateTimeMillis = $dateTimeMillis\n" +
                            "durationMillis = $durationMillis\n" + "callType = $callType"
                )
            }
            it.close()
        }
    }

}