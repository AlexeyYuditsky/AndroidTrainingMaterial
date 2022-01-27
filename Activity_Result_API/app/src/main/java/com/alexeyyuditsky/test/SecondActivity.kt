package com.alexeyyuditsky.test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.alexeyyuditsky.test.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private val resultIntent: Intent
        get() = Intent().putExtra(EXTRA_OUTPUT_MESSAGE, binding.valueEditText.text.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.saveButton.setOnClickListener { onSavePressed() }
        binding.cancelButton.setOnClickListener { onBackPressed() }

        binding.valueEditText.setText(intent.getStringExtra(EXTRA_INPUT_MESSAGE))
    }

    private fun onSavePressed() {
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED, resultIntent)
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    data class Output(
        val message: String,
        val confirmed: Boolean
    )

    class Contract : ActivityResultContract<String, Output>() {

        override fun createIntent(context: Context, input: String?): Intent {
            return Intent(context, SecondActivity::class.java).putExtra(EXTRA_INPUT_MESSAGE, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Output? {
            if (intent == null) return null
            val message = intent.getStringExtra(EXTRA_OUTPUT_MESSAGE) ?: return null

            val confirmed = resultCode == RESULT_OK
            return Output(message, confirmed)
        }

    }

    companion object {
        private const val EXTRA_INPUT_MESSAGE = "EXTRA_MESSAGE"
        private const val EXTRA_OUTPUT_MESSAGE = "EXTRA_MESSAGE"
    }
}