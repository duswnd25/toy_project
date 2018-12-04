package app.kimyeonjung.cuk_dom;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class Admin extends CommonClass {

	private AccountManager manager;

	private ProgressDialog progress;
	private WebView log;
	private String recent_list, pw;
	private TextView device_info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank);
		setActionBarHomeButton();
		login();
		manager = AccountManager.get(this);
		bindEvent();
	}

	public void login() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("관리자 로그인이 필요한 메뉴 입니다.");
		alert.setMessage("운영팀 또는 허가받은자만 접근할 수 있습니다.");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Ok.

				pw = input.getText().toString();

				if ("ihouse2164".equals(pw)) {
					setContentView(R.layout.admin);
					recent_log();
					device_info();

				} else {

					finish();

				}

			}
		});

		alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				finish();
			}
		});

		alert.show();

	}

	private void bindEvent() {
		for (Account account : manager.getAccounts()) {
			Log.i(account.type, account.name);
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void propose() {
		setContentView(R.layout.webview);
		log = (WebView) findViewById(R.id.web);
		log.loadUrl("file:///android_asset/log.html");
		progress();
		/* 웹뷰 속성 */
		WebSettings settings = log.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
	}

	public void progress() {

		// 로딩중 프로그래스바
		progress = ProgressDialog.show(this, "로딩중......", "잠시만 기다려 주세요.");
		log.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView paramWebView, String paramString) {
				if (Admin.this.progress.isShowing())
					Admin.this.progress.dismiss();
			}

			public boolean shouldOverrideUrlLoading(WebView paramWebView,
					String paramString) {
				paramWebView.loadUrl(paramString);
				return true;

			}

		});

	}

	public void device_info() {

		device_info = (TextView) findViewById(R.id.admin_device);
		device_info.setText("번호 : " + getMyPhoneNumber() + "\n" + "계정명 : "
				+ getMyTabletNumber() + "\n" + "전송되는 값 : "
				+ getMy10DigitPhoneNumber());

	}

	public void recent_log() {

		TextView side_info = (TextView) findViewById(R.id.recent_log_admin);
		recent_list = savedData.getString("Log_admin", "No_Data");
		side_info.setText(recent_list + "\n\n");

	}

	/* 액션바 메뉴 선택했을때 액션 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:

			finish();

			break;
		}

		return super.onOptionsItemSelected(item);

	}
}
