package app.studiotft.sid_radio.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import app.studiotft.sid_radio.R;


public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);

        Preference contactToDev = findPreference("contact_to_dev");
        contactToDev.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"duswnd25@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "[I-Media] - 문의");
                intent.putExtra(Intent.EXTRA_TEXT, "제목은 수정하지 말아주세요");
                SettingFragment.this.startActivity(Intent.createChooser(intent, "메일 보내기"));
                return false;
            }
        });
    }
}