package org.microg.gms.ui;

import android.os.Bundle;
import com.google.android.gms.R;
import androidx.preference.PreferenceFragment;

public class DevOptionFragment extends PreferenceFragment {
    public void onCreatePreferences(Bundle bundle, String str) {
        setPreferencesFromResource(R.xml.preferences_devoption, str);
    }
}
