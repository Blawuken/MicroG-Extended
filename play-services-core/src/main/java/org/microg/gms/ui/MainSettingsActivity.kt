package org.microg.gms.ui

import android.annotation.support.v3.services.privacy
import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import com.google.android.material.color.DynamicColors
import com.google.android.gms.R
import org.microg.gms.ui.settings.SettingsProvider
import org.microg.gms.ui.settings.getAllSettingsProviders

import com.quickersilver.themeengine.ThemeChooserDialogBuilder
import com.quickersilver.themeengine.ThemeEngine
import com.google.android.gms.databinding.SettingsRootActivityBinding

class MainSettingsActivity : AppCompatActivity() {
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
        ThemeEngine.applyToActivity(this)
        val binding = SettingsRootActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllSettingsProviders(this).forEach { settingsProvider ->
            settingsProvider.extendNavigation(getNavController())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivityIfAvailable(this)
        }
        appBarConfiguration = AppBarConfiguration.Builder(getNavController().graph).build()
        NavigationUI.setupActionBarWithNavController(this, getNavController(), appBarConfiguration)
        privacy.show(this)
        val themeEngine = ThemeEngine.getInstance(this)
        binding.changeTheme.setOnClickListener {
            ThemeChooserDialogBuilder(this)
                .setTitle(R.string.title_choose_theme)
                .setPositiveButton("OK") { _, theme ->
                    themeEngine.staticTheme = theme
                    recreate()
                }
                .setNegativeButton("Cancel")
                .setNeutralButton("Default") { _, _ ->
                    themeEngine.resetTheme()
                    recreate()
                }
                .setIcon(R.drawable.ic_round_brush)
                .create()
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(getNavController(), appBarConfiguration) || super.onSupportNavigateUp()
    }
}
