package org.microg.tools.ui;

import android.os.Build;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {
    //the values must be same as defined in arrays.xml
    private static final String LIGHT_MODE = "light";
    private static final String DARK_MODE = "dark";
    public static final String DEFAULT_MODE = "default"; // follow system settings

    public static void applyTheme(String theme) {
        int mode;
        switch (theme) {
            case LIGHT_MODE:
                mode = AppCompatDelegate.MODE_NIGHT_NO;
                break;
            case DARK_MODE:
                mode = AppCompatDelegate.MODE_NIGHT_YES;
                break;
            default:
                // Android 10 and above supports Dark theme by default
                // For others, Battery saver mode switches the system to Dark theme
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                } else {
                    mode = AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;
                }
                break;
        }
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
