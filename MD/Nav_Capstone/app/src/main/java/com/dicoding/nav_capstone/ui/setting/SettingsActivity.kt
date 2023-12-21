package com.dicoding.nav_capstone.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.ActivitySettingBinding // Import the necessary binding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding // Initialize the binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater) // Inflate the binding
        setContentView(binding.root) // Set the content view to the root of the binding

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }

        binding.backButton.setOnClickListener {
            // Navigate back when the backButton is clicked
            onBackPressed()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val pref = findPreference<androidx.preference.ListPreference>(getString(R.string.pref_key_dark))
            pref?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    "off" -> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    "on" -> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    else -> updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}