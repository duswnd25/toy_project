package app.kimyeonjung.cuk_dom;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class IntroduceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.introduce);

	}

}