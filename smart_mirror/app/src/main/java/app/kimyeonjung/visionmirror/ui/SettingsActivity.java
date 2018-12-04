package app.kimyeonjung.visionmirror.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import app.kimyeonjung.visionmirror.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Preference userNamePref = findPreference(getString(R.string.user_name_pref));
        userNamePref.setSummary(prefs.getString(userNamePref.getKey(), ""));
        userNamePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary((String) o);
                return true;
            }
        });


        Preference useTempTypePref = findPreference(getString(R.string.use_temp_type_c_pref));
        useTempTypePref.setSummary(prefs.getBoolean(useTempTypePref.getKey(), true) ? getString(R.string.temp_c) : getString(R.string.temp_f));
        useTempTypePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary((Boolean) o ? getString(R.string.temp_c) : getString(R.string.temp_f));
                return true;
            }
        });
    }
}
