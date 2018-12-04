package app.kimyeonjung.cuk_dom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Propose extends CommonClass {

	private ProgressDialog progress;
	private WebView propose;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.webview);
		setActionBarHomeButton();
		// 웹뷰

		propose = (WebView) findViewById(R.id.web);
		propose.setWebChromeClient(new ChromeClient());
		// 웹뷰 속성
		WebSettings settings = propose.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setUseWideViewPort(false);

		AlertDialog.Builder send_phone_number_alert = new AlertDialog.Builder(
				this);
		// 다이얼로그의 내용은 String 파일에서 불러옴
		send_phone_number_alert
				.setMessage(R.string.select_propose_type)
				.setCancelable(false)
				.setPositiveButton("시설고장 신고 바로가기",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								Uri uri = Uri
										.parse("http://www.catholic.ac.kr/~i-house/help/help_02.html");
								Intent ihouse = new Intent(Intent.ACTION_VIEW,
										uri);
								startActivity(ihouse);
								finish();
							}
						})
				.setNegativeButton("생활 관련",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								propose.loadUrl("https://docs.google.com/forms/d/1EfZIQTePkBj8j2Q7ASxE7u4zAgJDbKiQ5pXFkK3E5eU/viewform");
								progress();
							}
						});
		AlertDialog alert = send_phone_number_alert.create();
		alert.setTitle("건의 종류"); // 다이얼로그 제목
		alert.setIcon(R.drawable.cukholic);
		alert.show();

	}

	public void progress() {

		// 로딩중 프로그래스바
		progress = ProgressDialog.show(this, "로딩중......", "잠시만 기다려 주세요.");
		propose.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView paramWebView, String paramString) {
				if (Propose.this.progress.isShowing())
					Propose.this.progress.dismiss();
			}

			public boolean shouldOverrideUrlLoading(WebView paramWebView,
					String paramString) {
				paramWebView.loadUrl(paramString);
				return true;
			}
		});

	}

	/* 웹페이지 대화상자 구현 이부분이 없으면 연장신청 안됨 */
	private class ChromeClient extends WebChromeClient {

		// Javascript alert 호출 시 실행
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			final JsResult finalRes = result;

			// AlertDialog 생성
			new AlertDialog.Builder(view.getContext())
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finalRes.confirm();
								}
							}).setCancelable(false).create().show();
			return true;
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