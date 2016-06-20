package com.smilena.wordchecker;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Smilena on 6/15/2016.
 */
public class UserSettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.settings);

    }
}
