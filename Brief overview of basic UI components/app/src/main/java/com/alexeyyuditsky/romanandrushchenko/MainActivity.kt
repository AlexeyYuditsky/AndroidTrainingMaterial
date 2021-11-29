package com.alexeyyuditsky.romanandrushchenko

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.romanandrushchenko.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.button.setOnClickListener {
            onGetRandomImagePressed()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            onGetRandomImagePressed()
        }

    }

    private fun onGetRandomImagePressed(): Boolean {
        binding.progressBar.visibility = View.VISIBLE
        val id = binding.radioGroup.checkedRadioButtonId
        val keyword = findViewById<RadioButton>(id).text.toString()
        val encodeKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600?$encodeKeyword")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.imageView)

        return false
    }

}