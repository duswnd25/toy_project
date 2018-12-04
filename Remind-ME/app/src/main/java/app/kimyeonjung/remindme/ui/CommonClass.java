package app.kimyeonjung.remindme.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by YeonJung on 2015-06-28.
 * 공용 클래스
 */
public class CommonClass extends ActionBarActivity {
	private ActionBar actionBar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
	}

	public ActionBar getMyActionBar() {
		return actionBar;
	}
}
