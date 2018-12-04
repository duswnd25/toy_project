package app.kimyeonjung.remindme.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.kimyeonjung.remindme.R;


/**
 * Created by YeonJung on 2015-06-29.
 * 전화 수신시 정보 출력용
 */
public class ShowCallInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_show_call_info);

		// Dialog 사이즈 조절 하기
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = LinearLayout.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(params);

		Intent getData = getIntent();

		((TextView) findViewById(R.id.dialog_show_call_info_name)).setText(getData.getStringExtra("name"));
		((TextView) findViewById(R.id.dialog_show_call_info_memo)).setText(getData.getStringExtra("memo"));

		findViewById(R.id.dialog_show_call_info_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
