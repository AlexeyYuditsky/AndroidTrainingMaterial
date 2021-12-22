package com.alexeyyuditsky.test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.model.Options

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.openBoxButton.setOnClickListener { onOpenBoxPressed() }
        binding.optionsButton.setOnClickListener { onOptionsPressed() }
        binding.aboutButton.setOnClickListener { onAboutButtonPressed() }
        binding.exitButton.setOnClickListener { onExitPressed() }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPTIONS_REQUEST_CODE && resultCode == RESULT_OK) {
            options = data?.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS)
                ?: throw IllegalArgumentException("Cannot get updated data from options activity")
        }
    }

    private fun onOpenBoxPressed() {
        val intent = Intent(this, BoxSelectionActivity::class.java)
        startActivity(intent.putExtra(BoxSelectionActivity.EXTRA_OPTIONS, options))
    }


    private fun onOptionsPressed() {
        val intent = Intent(this, OptionsActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
        ActivityCompat.startActivityForResult(this, intent, OPTIONS_REQUEST_CODE, null)
    }

    private fun onAboutButtonPressed() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun onExitPressed() {
        finish()
    }

    companion object {
        private const val KEY_OPTIONS = "options"
        private const val OPTIONS_REQUEST_CODE = 1
    }

}