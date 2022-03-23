package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.screens.splash.SplashFragment
import com.alexeyyuditsky.test.screens.splash.SplashViewModel

/**
 * Entry point of the app.
 *
 * Splash activity contains only window background, all other initialization logic is placed to
 * [SplashFragment] and [SplashViewModel].
 */
class SplashActivity : AppCompatActivity(R.layout.activity_splash)