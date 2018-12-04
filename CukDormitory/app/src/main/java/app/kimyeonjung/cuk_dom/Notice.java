package app.kimyeonjung.cuk_dom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint({ "SetJavaScriptEnabled" })
public class Notice extends CommonClass {
	private ProgressDialog progress;
	private WebView notice;
	boolean finishFlag = false; // Back키 눌러서 종료 플래그

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		PackageManager pm = getPackageManager();
		try {
			pm.getApplicationInfo("com.facebook.katana",
					PackageManager.GET_META_DATA);
			// 패키지가 있을경우 실행할 내용

			final String url = "fb://page/1455802181374519";
			Intent facebookAppIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(url));
			facebookAppIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(facebookAppIntent);
			finish();

		} catch (NameNotFoundException e) {
			// 패키지가 없을경우 실행할 내용

			setContentView(R.layout.webview);
			setActionBarHomeButton();
			// 웹뷰
			notice = (WebView) findViewById(R.id.web);

			notice.loadUrl("https://www.facebook.com/cukdormitorynotice?fref=nf");

			/* 웹뷰 속성 */
			WebSettings settings = notice.getSettings();
			settings.setJavaScriptEnabled(true);
			settings.setSupportZoom(true);
			settings.setBuiltInZoomControls(true);
			settings.setSupportZoom(true);

			// 로딩중 프로그래스바
			progress = ProgressDialog.show(this, "로딩중......", "잠시만 기다려 주세요.");
			notice.setWebViewClient(new WebViewClient() {
				public void onPageFinished(WebView paramWebView,
						String paramString) {
					if (Notice.this.progress.isShowing())
						Notice.this.progress.dismiss();
				}

				public boolean shouldOverrideUrlLoading(WebView paramWebView,
						String paramString) {
					paramWebView.loadUrl(paramString);
					return true;

				}

			});

		}

	}

	/*
	 * Back키를 눌렀을때 더이상 뒤로갈 수 없으면 앱을 종료한다는 메시지를히스토리가 남아있으면 이전 페이지로 이동한다.
	 */

	@Override
	public void onBackPressed() {

		if (notice.canGoBack() == true) { // 히스토리가 남아있을경우
			notice.goBack(); // 뒤로이동

		}

		else { // 히스토리가 남아있지 않을경우

			finish();

		}

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