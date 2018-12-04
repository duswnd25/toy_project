package app.kimyeonjung.remindme.ui;

import android.os.Bundle;

import app.kimyeonjung.remindme.ui.setting.SettingFragment;

public class Setting extends CommonClass {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
	}
}