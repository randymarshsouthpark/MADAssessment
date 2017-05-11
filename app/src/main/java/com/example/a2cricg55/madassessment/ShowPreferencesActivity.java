package com.example.a2cricg55.madassessment;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ShowPreferencesActivity extends PreferenceActivity {
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
