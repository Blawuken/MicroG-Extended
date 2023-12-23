/*
 * SPDX-FileCopyrightText: 2023 microG Project Team
 * SPDX-License-Identifier: Apache-2.0
 */

package org.microg.gms.ui

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import org.microg.gms.base.core.R

abstract class AppPreference : Preference {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    init {
        isPersistent = false
    }

    private var packageNameField: String? = null
    private var appVersion: String? = null

    var applicationInfo: ApplicationInfo?
        get() = context.packageManager.getApplicationInfoIfExists(packageNameField)
        set(value) {
            if (value == null && packageNameField != null) {
                title = null
                icon = null
                appVersion = null
            } else if (value != null) {
                val pm = context.packageManager
                title = value.loadLabel(pm) ?: value.packageName
                icon = value.loadIcon(pm) ?: AppCompatResources.getDrawable(context, android.R.mipmap.sym_def_app_icon)

                appVersion = try {
                    pm.getPackageInfo(value.packageName, 0)?.versionName
                } catch (e: PackageManager.NameNotFoundException) {
                    null
                }
            }
            packageNameField = value?.packageName
        }

    var packageName: String?
        get() = packageNameField
        set(value) {
            if (value == null && packageNameField != null) {
                title = null
                icon = null
                appVersion = null
            } else if (value != null) {
                val pm = context.packageManager
                val applicationInfo = pm.getApplicationInfoIfExists(value)
                title = applicationInfo?.loadLabel(pm)?.toString() ?: value
                icon = applicationInfo?.loadIcon(pm) ?: AppCompatResources.getDrawable(context, android.R.mipmap.sym_def_app_icon)

                appVersion = try {
                    pm.getPackageInfo(value, 0)?.versionName
                } catch (e: PackageManager.NameNotFoundException) {
                    null
                }
            }
            packageNameField = value
        }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        val packageNameTextView: TextView? = holder.itemView.findViewById(R.id.package_name)
        val appVersionTextView: TextView? = holder.itemView.findViewById(R.id.version_name)

        if (packageNameTextView != null && packageNameField != null) {
            packageNameTextView.text = packageNameField
        } else {
            packageNameTextView?.text = ""
        }

        if (appVersionTextView != null && appVersion != null) {
            appVersionTextView.text = appVersion
        } else {
            appVersionTextView?.text = ""
        }
    }
}
