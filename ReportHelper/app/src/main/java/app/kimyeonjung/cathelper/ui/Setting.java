package app.kimyeonjung.cathelper.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import app.kimyeonjung.cathelper.ui.setting.SettingFragment;

public class Setting extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
	}
}