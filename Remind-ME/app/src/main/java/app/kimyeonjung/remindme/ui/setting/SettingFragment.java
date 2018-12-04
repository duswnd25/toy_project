package app.kimyeonjung.remindme.ui.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import app.kimyeonjung.remindme.R;

public class SettingFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.setting);

	}
}