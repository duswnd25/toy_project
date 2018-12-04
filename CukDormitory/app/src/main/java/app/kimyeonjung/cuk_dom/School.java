package app.kimyeonjung.cuk_dom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

@SuppressLint("SetJavaScriptEnabled")
public class School extends CommonClass {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.suggest_cukholic);

		setActionBarHomeButton();

		Crouton.makeText(School.this, "서비스 종료되었습니다.", Style.ALERT).show();

	}

	public void goCukholic(View v) {

		Uri uri = Uri
				.parse("https://play.google.com/store/apps/details?id=app.kimyeonjung.CukHolic");
		Intent ihouse = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(ihouse);
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

	/*
	 * Back키를 눌렀을때 더이상 뒤로갈 수 없으면 앱을 종료한다는 메시지를히스토리가 남아있으면 이전 페이지로 이동한다.
	 */

}
