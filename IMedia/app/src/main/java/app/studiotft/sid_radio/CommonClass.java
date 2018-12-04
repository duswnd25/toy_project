package app.studiotft.sid_radio;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by YeonJung on 2015-07-07.
 * 공용 소스 관리를 위한 클래스
 */
public class CommonClass extends ActionBarActivity {

	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.actionBar = getSupportActionBar();
	}

	protected ActionBar getMyActionBar() {
		return this.actionBar;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == android.R.id.home) {
			finish();
		}

		return super.onOptionsItemSelected(item);
	}
}
