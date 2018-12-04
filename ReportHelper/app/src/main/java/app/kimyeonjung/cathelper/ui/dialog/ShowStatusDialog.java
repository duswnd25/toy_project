package app.kimyeonjung.cathelper.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import app.kimyeonjung.cathelper.R;

/**
 * Created by YeonJung on 2015-06-07.
 * 현재상황을 보여주기 위한 다이얼로그
 */

public class ShowStatusDialog extends Dialog implements View.OnClickListener {

	private TextView contentView;
	private String content;
	private ImageView statusImg;
	private int resId;
	private Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.dialog_status);

		setLayout();
		setContent(content);
		setImg(this.resId);
	}


	public ShowStatusDialog(Context context, String content, int redId) {
		super(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
		this.content = content;
		this.resId = redId;
	}

	private void setContent(String content) {
		contentView.setText(content);
	}

	private void setImg(int resId) {
		this.statusImg.setImageResource(resId);
	}

	private void setLayout() {
		contentView = (TextView) findViewById(R.id.dialog_status_text);
		statusImg = (ImageView) findViewById(R.id.dialog_status_image);
		ok = (Button) findViewById(R.id.dialog_status_ok);
		ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.dialog_status_ok:
				dismiss();
				break;
		}
	}
}