/*
 * SPDX-FileCopyrightText: 2020, microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.ui

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.SwitchPreference
import com.google.android.gms.R
import org.microg.gms.checkin.CheckinPreferences
import org.microg.gms.gcm.GcmDatabase
import org.microg.gms.gcm.GcmPrefs
import org.microg.gms.play.PlayPreferences
import org.microg.gms.ui.settings.SettingsProvider
import org.microg.gms.ui.settings.getAllSettingsProviders
import org.microg.tools.ui.ResourceSettingsFragment

class SettingsFragment : ResourceSettingsFragment() {
    private val createdPreferences = mutableListOf<Preference>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)

        val pm = requireActivity().packageManager

        findPreference<Preference>(PREF_ACCOUNTS)!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            findNavController().navigate(requireContext(), R.id.accountManagerFragment)
            true
        }
        findPreference<Preference>(PREF_CHECKIN)!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            findNavController().navigate(requireContext(), R.id.openCheckinSettings)
            true
        }
        findPreference<Preference>(PREF_GCM)!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            findNavController().navigate(requireContext(), R.id.openGcmSettings)
            true
        }
        findPreference<Preference>(PREF_ABOUT)!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            findNavController().navigate(requireContext(), R.id.openAbout)
            true
        }
        findPreference<Preference>(PREF_PLAY)!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            findNavController().navigate(requireContext(), R.id.openPlaySettings)
            true
        }
        findPreference<SwitchPreference>(PREF_CAST_HIDE_LAUNCHER_ICON)?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                pm.setComponentEnabledSetting(
                    ComponentName.createRelative(requireActivity(), "org.microg.gms.ui.SettingsActivityLauncher"),
                    when (newValue) {
                        true -> PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                        else -> PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    },
                    PackageManager.DONT_KILL_APP
                )
                true
            }

        }
        findPreference<Preference>(PREF_ABOUT)!!.summary = getString(org.microg.tools.ui.R.string.about_version_str, AboutFragment.getSelfVersion(context))

        for (entry in getAllSettingsProviders(requireContext()).flatMap { it.getEntriesStatic(requireContext()) }) {
            entry.createPreference()
        }
    }

    private fun SettingsProvider.Companion.Entry.createPreference(): Preference? {
        val preference = Preference(requireContext()).fillFromEntry(this)
        try {
            if (findPreference<PreferenceCategory>(when (group) {
                    SettingsProvider.Companion.Group.HEADER -> "prefcat_header"
                    SettingsProvider.Companion.Group.GOOGLE -> "prefcat_google_services"
                    SettingsProvider.Companion.Group.OTHER -> "prefcat_other_services"
                    SettingsProvider.Companion.Group.FOOTER -> "prefcat_footer"
                })?.addPreference(preference) == true) {
                createdPreferences.add(preference)
                return preference
            } else {
                Log.w(TAG, "Preference not added $key")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Failed adding preference $key", e)
        }
        return null
    }

    private fun Preference.fillFromEntry(entry: SettingsProvider.Companion.Entry): Preference {
        key = entry.key
        title = entry.title
        summary = entry.summary
        icon = entry.icon
        isPersistent = false
        isVisible = true
        setOnPreferenceClickListener {
            findNavController().navigate(requireContext(), entry.navigationId)
            true
        }
        return this
    }

    override fun onResume() {
        super.onResume()
        val context = requireContext()
        if (GcmPrefs.get(requireContext()).isEnabled) {
            val database = GcmDatabase(context)
            val regCount = database.registrationList.size
            database.close()
            findPreference<Preference>(PREF_GCM)!!.summary = context.getString(org.microg.gms.base.core.R.string.service_status_enabled_short) + " - " + context.resources.getQuantityString(R.plurals.gcm_registered_apps_counter, regCount, regCount)
        } else {
            findPreference<Preference>(PREF_GCM)!!.setSummary(org.microg.gms.base.core.R.string.service_status_disabled_short)
        }

        findPreference<Preference>(PREF_CHECKIN)!!.setSummary(if (CheckinPreferences.isEnabled(requireContext())) org.microg.gms.base.core.R.string.service_status_enabled_short else org.microg.gms.base.core.R.string.service_status_disabled_short)
        findPreference<Preference>(PREF_PLAY)!!.setSummary(if (PlayPreferences.isLicensingEnabled(requireContext())) R.string.pref_play_summary_licensing_on else R.string.pref_play_summary_licensing_off)

        lifecycleScope.launchWhenResumed {
            val entries = getAllSettingsProviders(requireContext()).flatMap { it.getEntriesDynamic(requireContext()) }
            for (preference in createdPreferences) {
                if (!entries.any { it.key == preference.key }) preference.isVisible = false
            }
            for (entry in entries) {
                val preference = createdPreferences.find { it.key == entry.key }
                if (preference != null) preference.fillFromEntry(entry)
                else entry.createPreference()
            }
        }
    }

    companion object {
        const val PREF_ABOUT = "pref_about"
        const val PREF_GCM = "pref_gcm"
        const val PREF_CHECKIN = "pref_checkin"
        const val PREF_ACCOUNTS = "pref_accounts"
        const val PREF_CAST_HIDE_LAUNCHER_ICON = "pref_hide_launcher_icon"
        const val PREF_PLAY = "pref_play"
    }

    init {
        preferencesResource = R.xml.preferences_start
    }
}