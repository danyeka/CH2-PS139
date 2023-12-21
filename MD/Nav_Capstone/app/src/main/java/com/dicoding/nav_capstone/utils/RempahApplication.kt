package com.dicoding.nav_capstone.utils

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.dicoding.nav_capstone.R
import java.util.Locale

class RempahApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_follow_system)
        )?.apply {
            val mode = DarkMode.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }
    }
}