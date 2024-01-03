package org.microg.gms.ui;

import android.os.Bundle;
import com.google.android.gms.R;
import androidx.preference.PreferenceFragment;

public class CreditsFragment extends PreferenceFragment {
    public void onCreatePreferences(Bundle bundle, String str) {
        setPreferencesFromResource(R.xml.preferences_credits, str);
    }
}
