/*
 * SPDX-FileCopyrightText: 2020, microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.ui

import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.microg.gms.gcm.GcmDatabase

class PushNotificationAllAppsFragment : PreferenceFragmentCompat() {
    private lateinit var database: GcmDatabase
    private lateinit var registered: PreferenceCategory
    private lateinit var unregistered: PreferenceCategory
    private lateinit var registeredNone: Preference
    private lateinit var unregisteredNone: Preference
    private lateinit var progress: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        database = GcmDatabase(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(MaterialColors.getColor(view, android.R.attr.colorBackground))
    }

    override fun onResume() {
        super.onResume()
        updateContent()
    }

    override fun onPause() {
        super.onPause()
        database.close()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_push_notifications_all_apps)
        registered = preferenceScreen.findPreference("prefcat_push_apps_registered") ?: registered
        unregistered = preferenceScreen.findPreference("prefcat_push_apps_unregistered") ?: unregistered
        registeredNone = preferenceScreen.findPreference("pref_push_apps_registered_none") ?: registeredNone
        unregisteredNone = preferenceScreen.findPreference("pref_push_apps_unregistered_none") ?: unregisteredNone
        progress = preferenceScreen.findPreference("pref_push_apps_all_progress") ?: progress
    }

    private fun updateContent() {
        val context = requireContext()
        lifecycleScope.launchWhenResumed {
            val apps = withContext(Dispatchers.IO) {
                val res = database.appList.map { app ->
                    val pref = AppIconPreference(context)
                    pref.packageName = app.packageName
                    pref.summary = when {
                        app.lastMessageTimestamp > 0 -> getString(R.string.gcm_last_message_at, DateUtils.getRelativeTimeSpanString(app.lastMessageTimestamp))
                        else -> null
                    }
                    pref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                        findNavController().navigate(requireContext(), R.id.openGcmAppDetailsFromAll, bundleOf(
                                "package" to app.packageName
                        ))
                        true
                    }
                    pref.key = "pref_push_app_" + app.packageName
                    pref to (database.getRegistrationsByApp(app.packageName))
                }.sortedBy {
                    it.first.title.toString().toLowerCase()
                }.mapIndexed { idx, pair ->
                    pair.first.order = idx
                    pair
                }
                database.close()
                res
            }
            registered.removeAll()
            registered.isVisible = true
            unregistered.removeAll()
            unregistered.isVisible = true

            var hadRegistered = false
            var hadUnregistered = false

            for (pair in apps) {
                if (pair.second.isEmpty()) {
                    unregistered.addPreference(pair.first)
                    hadUnregistered = true
                } else {
                    registered.addPreference(pair.first)
                    hadRegistered = true
                }
            }

            registeredNone.isVisible = !hadRegistered
            unregisteredNone.isVisible = !hadUnregistered
            if (!hadRegistered) registered.addPreference(registeredNone)
            if (!hadUnregistered) unregistered.addPreference(unregisteredNone)
            progress.isVisible = false
        }
    }
}
