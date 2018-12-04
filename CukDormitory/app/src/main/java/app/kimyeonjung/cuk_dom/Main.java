package app.kimyeonjung.cuk_dom;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Main extends CommonClass implements View.OnClickListener {

	boolean finishFlag = false; // Back키 눌러서 종료 플래그

	private LinearLayout out, school, restaurant, facebook;
	private ImageView propose, setting, calendar, about, call;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		startActivity(new Intent(this, Splash.class));
		setContentView(R.layout.main); // 표시할 레이아웃
		layoutInit();
		listenerInit();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			parseLogger("최근 연결 재연결 성공");
			parseLogger(currentUser.getUsername().toString());
		} else {
			parseLogger("최근 연결 재연결 실패");
			ParseAnonymousUtils.logIn(new LogInCallback() {
				@Override
				public void done(ParseUser user, ParseException e) {
					if (e == null) {
						parseLogger("Anonymouse 로그인 성공");
					} else {
						parseLogger("Anonymouse 로그인 실패");
					}
				}
			});
		}

		// 업데이트로그를 표시하기 위해서 버전값을 저장후 매번 저장된 버전값과 현재버전값을 비교함

		try {
			PackageManager pm = this.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			int VERSION = packageInfo.versionCode;
			int old_Ver = savedData.getInt("version", 0);

			if (old_Ver < VERSION) {

				/*
				 * 현재 버전이 저장된 버전값보다 높을경우 현재 버전값을 저장하고 업데이트 히스토리를 Values/String에서
				 * 불러옴
				 */

				startActivity(new Intent(this, Introduce.class));

				TextView msg = new TextView(this);
				msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				msg.setTextColor(0xff000000);
				msg.setText(R.string.update);
				new AlertDialog.Builder(this).setTitle(R.string.update_history)
						.setView(msg).setPositiveButton("확인", null).show();

				agree_reset();

				SharedPreferences.Editor edit = savedData.edit();
				edit.putInt("version", VERSION);
				edit.commit();
			}
		}

		catch (Exception e) {
		}

	}

	private void layoutInit() {
		out = (LinearLayout) findViewById(R.id.out_main);
		school = (LinearLayout) findViewById(R.id.school_main);
		restaurant = (LinearLayout) findViewById(R.id.restaurant_main);
		facebook = (LinearLayout) findViewById(R.id.facebook_main);
		propose = (ImageView) findViewById(R.id.propose_main);
		setting = (ImageView) findViewById(R.id.setting_main);
		about = (ImageView) findViewById(R.id.about_main);
		calendar = (ImageView) findViewById(R.id.calendar_main);
		call = (ImageView) findViewById(R.id.call_main);
	}

	private void listenerInit() {
		out.setOnClickListener(this);
		school.setOnClickListener(this);
		restaurant.setOnClickListener(this);
		facebook.setOnClickListener(this);
		propose.setOnClickListener(this);
		setting.setOnClickListener(this);
		about.setOnClickListener(this);
		calendar.setOnClickListener(this);
		call.setOnClickListener(this);
	}

	public void onClick(View v) {

		Intent menu = null;
		Boolean isPhone = false;
		switch (v.getId()) {
		case R.id.out_main:
			menu = new Intent(Main.this, Out.class);
			break;
		case R.id.school_main:
			menu = new Intent(Main.this, School.class);
			break;
		case R.id.restaurant_main:
			menu = new Intent(Main.this, Food.class);
			break;
		case R.id.facebook_main:
			menu = new Intent(Main.this, Notice.class);
			break;
		case R.id.propose_main:
			menu = new Intent(Main.this, Propose.class);
			break;
		case R.id.setting_main:
			menu = new Intent(Main.this, Setting.class);
			break;
		case R.id.calendar_main:
			menu = new Intent(Main.this, Calendar.class);
			break;
		case R.id.call_main:
			isPhone = true;
			int random = (int) (Math.random() * 2);
			if (random == 0) {
				startActivity(new Intent("android.intent.action.CALL",
						Uri.parse("tel:02-2164-4660")));
			} else if (random == 1) {
				startActivity(new Intent("android.intent.action.CALL",
						Uri.parse("tel:02-2164-4661")));
			}
			break;

		case R.id.about_main:
			menu = new Intent(Main.this, Introduce.class);
			break;
		}

		if (isPhone == false) {
			startActivity(menu);
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
		} else {

		}
	}

	// 푸시서버에 기기등록
	public void agree_reset() {

		/*
		 * 이부분은 전화번호 사용동의를 구현하기위해 업데이트마다 한번씩 agree_check의 값을 초기화해줌으로써 지속적으로
		 * 이용자에게 동의를 구함
		 */
		SharedPreferences agree_check = getSharedPreferences("agree", 0);
		SharedPreferences.Editor check_edit = agree_check.edit();
		check_edit.putBoolean("agree", false);
		check_edit.commit();

	}

	public void goCukholic(View v) {
		Uri uri = Uri
				.parse("https://play.google.com/store/apps/details?id=app.kimyeonjung.CukHolic");
		Intent ihouse = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(ihouse);
	}

	// Back키 눌러서 종료
	public void onBackPressed() {
		if (finishFlag) {
			finish();
			return;
		}
		Toast.makeText(getApplicationContext(), R.string.back,
				Toast.LENGTH_LONG).show();
		finishFlag = true;
	}

	public void test(View v) {
		Intent notice = new Intent(Main.this, Notice.class);
		startActivity(notice);

	}

}
