package app.kimyeonjung.cuk_dom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

@SuppressLint({ "SetJavaScriptEnabled" })
public class Food extends CommonClass {
	private ProgressDialog progress;
	private WebView food;
	boolean finishFlag = false; // Back키 눌러서 종료 플래그

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.webview);
		setActionBarHomeButton();
		// 웹뷰
		food = (WebView) findViewById(R.id.web);

		food.loadUrl("http://www.catholic.ac.kr/bFramework/board/findBoardList.action?TableIdx=5&TableCode=bf_ihouse_04&Category=9");

		/* 웹뷰 속성 */
		WebSettings settings = food.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);

		// 로딩중 프로그래스바
		progress = ProgressDialog.show(this, "로딩중......", "잠시만 기다려 주세요.");
		food.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView paramWebView, String paramString) {
				if (Food.this.progress.isShowing())
					Food.this.progress.dismiss();

			}

			public boolean shouldOverrideUrlLoading(WebView paramWebView,
					String paramString) {
				paramWebView.loadUrl(paramString);
				return true;

			}

		});

		Crouton.makeText(Food.this, "제대로 표시되지 않을 경우\n국제관 학생식당에 문의해 주세요.",
				Style.INFO).show();

	}

	/*
	 * Back키를 눌렀을때 더이상 뒤로갈 수 없으면 앱을 종료한다는 메시지를히스토리가 남아있으면 이전 페이지로 이동한다.
	 */

	@Override
	public void onBackPressed() {

		if (food.canGoBack() == true) { // 히스토리가 남아있을경우
			food.goBack(); // 뒤로이동

		}

		else { // 히스토리가 남아있지 않을경우

			finish();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
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