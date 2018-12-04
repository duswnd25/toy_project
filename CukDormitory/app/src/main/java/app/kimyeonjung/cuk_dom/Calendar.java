package app.kimyeonjung.cuk_dom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class Calendar extends CommonClass {

	private ProgressDialog progress;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.webview);
		setActionBarHomeButton();

		WebView calendar = (WebView) findViewById(R.id.web);
		calendar.loadUrl("file:///android_asset/calendar.html");
		WebSettings settings = calendar.getSettings();

		/* 웹뷰 속성 */
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setUseWideViewPort(false);

		/* 로딩중 프로그래스바 구현 */
		progress = ProgressDialog.show(this, "로딩중....", "잠시만 기다려 주세요.");
		calendar.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView paramWebView, String paramString) {
				if (Calendar.this.progress.isShowing())
					Calendar.this.progress.dismiss();
			}

			public boolean shouldOverrideUrlLoading(WebView paramWebView,
					String paramString) {
				paramWebView.loadUrl(paramString);
				return true;
			}
		});

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