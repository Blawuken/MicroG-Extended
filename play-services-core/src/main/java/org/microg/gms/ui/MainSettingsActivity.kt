package org.microg.gms.ui

import android.annotation.support.v3.services.privacy
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle

import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceManager

import com.google.android.material.color.DynamicColors
import com.google.android.gms.R
import org.microg.tools.ui.BaseActivity
import org.microg.gms.ui.settings.SettingsProvider
import org.microg.gms.ui.settings.getAllSettingsProviders

class MainSettingsActivity : BaseActivity(), SharedPreferences.OnSharedPreferenceChangeListener, Preference.SummaryProvider<ListPreference> {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun getNavController(): NavController {
        return (supportFragmentManager.findFragmentById(R.id.navhost) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        getAllSettingsProviders(this).forEach { settingsProvider ->
            settingsProvider.preProcessSettingsIntent(intent)
        }
        setContentView(R.layout.settings_root_activity)
        getAllSettingsProviders(this).forEach { settingsProvider ->
            settingsProvider.extendNavigation(getNavController())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivityIfAvailable(this)
        }
        appBarConfiguration = AppBarConfiguration.Builder(getNavController().graph).build()
        NavigationUI.setupActionBarWithNavController(this, getNavController(), appBarConfiguration)
        privacy.show(this)
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val themePreference = "pref_theme"
        if (key == themePreference) {
            val themePreferenceValues = resources.getStringArray(R.array.pref_values_theme)
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("pref_theme", "MODE_NIGHT_FOLLOW_SYSTEM")
            when (pref) {
                themePreferenceValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                themePreferenceValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themePreferenceValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    override fun provideSummary(preference: ListPreference): CharSequence? {
        val key = preference.key
        if (key == "pref_theme") {
            return preference.entry
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(getNavController(), appBarConfiguration) || super.onSupportNavigateUp()
    }
}
