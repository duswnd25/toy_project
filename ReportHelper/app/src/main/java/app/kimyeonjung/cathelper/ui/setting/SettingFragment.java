package app.kimyeonjung.cathelper.ui.setting;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import app.kimyeonjung.cathelper.R;


public class SettingFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.setting);

		Preference autoLogIn = findPreference("launchAutoLogin");
		Preference licenseCheck = findPreference("launchLicenseChecker");
		Preference questionToAdmin = findPreference("question_to_admin");

	}
}