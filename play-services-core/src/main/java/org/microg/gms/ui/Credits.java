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

import android.os.Bundle;
import android.content.Context;
import android.view.MenuItem;
import com.google.android.gms.R;
import com.neko.collapsingtoolbar.CollapsingToolbarBaseActivity;

public class Credits extends CollapsingToolbarBaseActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context applicationContext = getApplicationContext();
        setContentView(getResources("layout/uwu_layout_content"));
        getFragmentManager().beginTransaction().replace(R.id.uwu_content, new CreditsFragment()).commit();
    }

    public int getResources(String str) {
        return getApplicationContext().getResources().getIdentifier(str, null, getApplicationContext().getPackageName());
    }
}
