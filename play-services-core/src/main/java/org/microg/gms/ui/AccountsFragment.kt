package org.microg.gms.ui

import android.accounts.AccountManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.R
import org.microg.gms.auth.AuthConstants

class AccountsFragment : PreferenceFragmentCompat() {

    private val TAG = AccountsFragment::class.java.simpleName

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_accounts)
        updateAccountList()
    }

    override fun onResume() {
        super.onResume()
        updateAccountList()
    }

    private fun updateAccountList() {
        val accountManager = AccountManager.get(requireContext())
        val accounts = accountManager.getAccountsByType(AuthConstants.DEFAULT_ACCOUNT_TYPE)

        val preferenceCategory = findPreference<PreferenceCategory>("prefcat_current_accounts")

        if (accounts.isEmpty()) {
            preferenceCategory?.isVisible = false
        } else {
            preferenceCategory?.isVisible = true
            preferenceCategory?.removeAll()

            var isFirstAccount = true

            accounts.forEach { account ->
                val newPreference = Preference(requireContext()).apply {
                    title = account.name
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_google_logo)
                    preferenceCategory?.addPreference(this)

                    setOnPreferenceClickListener {
                        showConfirmationDialog(account.name)
                        true
                    }
                }

                if (isFirstAccount) {
                    isFirstAccount = false
                    newPreference.summary = getString(R.string.pref_accounts_default)
                }

                preferenceCategory?.addPreference(newPreference)
            }
        }
    }

    private fun showConfirmationDialog(accountName: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle(getString(R.string.dialog_title_remove_account))
            setMessage(getString(R.string.dialog_message_remove_account))
            setPositiveButton(getString(R.string.dialog_confirm_button)) { _, _ ->
                removeAccount(accountName)
                val toastMessage = getString(R.string.toast_remove_account_success, accountName)
                showToast(toastMessage)
                updateAccountList()
            }
            setNegativeButton(getString(R.string.dialog_cancel_button)) { dialog, _ ->
                dialog.dismiss()
            }
            create().show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun removeAccount(accountName: String): Boolean {
        val accountManager = AccountManager.get(requireContext())
        val accounts = accountManager.getAccountsByType(AuthConstants.DEFAULT_ACCOUNT_TYPE)

        val accountToRemove = accounts.firstOrNull { it.name == accountName }
        accountToRemove?.let {
            return try {
                val removedSuccessfully = accountManager.removeAccount(it, null, null).result
                if (removedSuccessfully) {
                    updateAccountList()
                }
                removedSuccessfully
            } catch (e: Exception) {
                Log.e(TAG, "Error removing account: $accountName", e)
                false
            }
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<Preference>("pref_manage_accounts")?.setOnPreferenceClickListener {
            try {
                startActivity(Intent(Settings.ACTION_SYNC_SETTINGS))
            } catch (activityNotFoundException: ActivityNotFoundException) {
                Log.e(TAG, "Failed to launch sync settings", activityNotFoundException)
            }
            true
        }
    }
}
