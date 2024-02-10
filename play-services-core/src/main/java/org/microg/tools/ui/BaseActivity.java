package org.microg.tools.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.quickersilver.themeengine.ThemeEngine;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeEngine.applyToActivity(this);
    }
}
