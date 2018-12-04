package app.studiotft.sid_radio.ui.setting;

import android.os.Bundle;

import app.studiotft.sid_radio.CommonClass;

public class Setting extends CommonClass {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getMyActionBar().setDisplayHomeAsUpEnabled(true);

		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
	}
}