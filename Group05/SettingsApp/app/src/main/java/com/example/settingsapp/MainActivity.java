package com.example.settingsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private View parentLayout;
    private SwitchMaterial switchMaterial;
    private TextView settingTextView, themeTextView;

    private UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        settings = (UserSettings) getApplication();

        inItWidget();
        loadSharePreference();
        inItSwitchListener();
    }

    private void inItSwitchListener() {
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    settings.setCustomTheme(UserSettings.DARK_THEME);
                }
                else{
                    settings.setCustomTheme(UserSettings.LIGHT_THEME);
                }
                SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(UserSettings.CUSTOM_THEME, settings.getCustomTheme());
                editor.apply();
                updateView();
            }
        });
    }

    private void updateView() {
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        if(settings.getCustomTheme().equals(UserSettings.DARK_THEME)){
            settingTextView.setTextColor(white);
            themeTextView.setTextColor(white);
            themeTextView.setText("Dark");
            parentLayout.setBackgroundColor(black);
            switchMaterial.setChecked(true);
        }
        else {
            settingTextView.setTextColor(black);
            themeTextView.setTextColor(black);
            themeTextView.setText("Light");
            parentLayout.setBackgroundColor(white);
            switchMaterial.setChecked(false        );
        }
    }

    private void loadSharePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME, UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
    }

    private void inItWidget() {
        parentLayout = findViewById(R.id.parentLayout);
        switchMaterial = findViewById(R.id.switchMaterial);
        settingTextView = findViewById(R.id.settingTextView);
        themeTextView = findViewById(R.id.themeTextView);
    }
}