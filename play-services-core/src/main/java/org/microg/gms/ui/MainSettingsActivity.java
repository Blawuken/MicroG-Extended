package org.microg.gms.ui;

import android.annotation.support.v3.services.privacy;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.color.DynamicColors;
import com.google.android.gms.R;
import org.microg.tools.ui.BaseActivity;
import org.microg.gms.ui.SettingsFragmentTheme;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import org.microg.gms.ui.settings.SettingsProvider;
import com.quickersilver.themeengine.ThemeChooserDialogBuilder;
import com.quickersilver.themeengine.ThemeEngine;
import com.google.android.gms.databinding.SettingsRootActivityBinding;

import static org.microg.gms.ui.settings.SettingsProviderKt.getAllSettingsProviders;

public class MainSettingsActivity extends BaseActivity {
    private AppBarConfiguration appBarConfiguration;

    private NavController getNavController() {
        return ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navhost)).getNavController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        for (SettingsProvider settingsProvider : getAllSettingsProviders(this)) {
            settingsProvider.preProcessSettingsIntent(intent);
        }

        SettingsRootActivityBinding binding = SettingsRootActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        for (SettingsProvider settingsProvider : getAllSettingsProviders(this)) {
            settingsProvider.extendNavigation(getNavController());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivityIfAvailable(this);
        }

        appBarConfiguration = new AppBarConfiguration.Builder(getNavController().getGraph()).build();
        NavigationUI.setupWithNavController(toolbarLayout, toolbar, getNavController(), appBarConfiguration);
        privacy.show(this);
        ThemeEngine themeEngine = ThemeEngine.getInstance(this);
        binding.changeTheme.setOnClickListener(view -> {
            new ThemeChooserDialogBuilder(this)
                    .setTitle(R.string.title_choose_theme)
                    .setPositiveButton("OK", (dialog, theme) -> {
                        themeEngine.setStaticTheme(theme);
                        recreate();
                    })
                    .setNegativeButton("Cancel")
                    .setNeutralButton("Default", (dialog, which) -> {
                        themeEngine.resetTheme();
                        recreate();
                    })
                    .setIcon(R.drawable.ic_round_brush)
                    .create()
                    .show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            new SettingsFragmentTheme().show(getSupportFragmentManager(), "Settings");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(getNavController(), appBarConfiguration) || super.onSupportNavigateUp();
    }
}
