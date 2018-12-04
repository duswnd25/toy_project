package app.kimyeonjung.cuk_dom;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;

import com.parse.ParseAnalytics;

public class CommonClass extends ActionBarActivity {

	protected ActionBar actionbar;
	protected SharedPreferences savedData;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ParseAnalytics.trackAppOpened(getIntent());
		savedData = PreferenceManager.getDefaultSharedPreferences(this);
		actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.red900)));
		actionbar.setTitle(getString(R.string.app_name));
		actionbar.setSubtitle(getVersionCode());

	}

	protected void parseLogger(String content) {
		Log.d("Parse 연동", content);
	}

	protected String getVersionCode() {

		/*
		 * 메인 액티비티는 Common액티비티를 상속하지 않기 때문에 액션바 부분을 따로 구현함 앱 버전명은 패키지 인포에서
		 * 매니페스트에 저장된 버전코드를 불러와서 사용함
		 */

		PackageInfo pi = null;

		try {

			pi = getPackageManager().getPackageInfo(getPackageName(), 0);

		}

		catch (NameNotFoundException e) {

			e.printStackTrace();

		}

		return String.valueOf(pi.versionName);
	}

	protected void setActionBarTitle(String title, String subtitle) {
		actionbar.setTitle(title);
		actionbar.setSubtitle(subtitle);
	}

	protected void setActionBarHomeButton() {
		actionbar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:
			finish();
			break;
		}

		return super.onOptionsItemSelected(item);

	}

	/*
	 * 아래부분은 기기의 휴대폰 번호를 String값으로 받아오는 부분임이부분은 악의적인 사용을 막기위해 필요함 (삭제금지)
	 */
	protected String getMy10DigitPhoneNumber() {

		if (getMyPhoneNumber() == null) {

			return getMyTabletNumber().substring(0);

		}

		else
			return getMyPhoneNumber().substring(0);

	}

	protected String getMyPhoneNumber() {

		return ((TelephonyManager) getSystemService("phone")).getLine1Number();

	}

	protected String getMyTabletNumber() {

		String tablet = null;

		AccountManager manager = AccountManager.get(this);
		Account[] accounts = manager.getAccounts();
		final int count = accounts.length;
		Account account = null;

		for (int i = 0; i < count; i++) {

			account = accounts[i];

			if (account.type.equals("com.facebook.auth.login")) {

				tablet = (account.name + "[F]");

				break;
			} else {

				String isgoogle = account.type;

				if (isgoogle.equals("com.google")) {
					tablet = (account.name + "/" + "[G]");

				} else {
					tablet = (account.name + "/" + account.type);
				}
			}

		}

		if (tablet.equals("")) {
			for (int i = 0; i < count; i++) {

				account = accounts[i];
				if (account.type.equals("com.google")) { // 이러면 구글 계정 구분 가능

					tablet = (account.name + "[G]");

					break;
				}

			}
		}
		return tablet;

	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
}
