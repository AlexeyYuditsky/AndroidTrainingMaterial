package com.alexeyyuditsky.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivitySecondBinding

class SecondLifecycleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() savedState=$savedInstanceState")

        binding = ActivitySecondBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.left_out_close_activity, R.anim.right_in_close_activity)
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState()")
    }

    companion object {
        @JvmField
        val TAG: String = SecondLifecycleActivity::class.java.simpleName
    }
}