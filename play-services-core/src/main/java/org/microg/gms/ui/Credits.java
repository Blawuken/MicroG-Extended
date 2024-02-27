/*
 * Copyright (C) 2017 microG Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.microg.gms.ui;

import androidx.fragment.app.Fragment;
import com.google.android.gms.R;
import org.microg.tools.ui.AbstractSettingsActivity;
import org.microg.tools.ui.ResourceSettingsFragment;

public class Credits extends AbstractSettingsActivity {

    @Override
    protected Fragment getFragment() {
        return new AsFragment();
    }

    public static class AsFragment extends ResourceSettingsFragment {
        public AsFragment() {
            preferencesResource =  R.xml.preferences_credits;
        }
    }
}
