package app.studiotft.sid_radio.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import app.studiotft.sid_radio.CommonClass;
import app.studiotft.sid_radio.R;


public class InfoDetail extends CommonClass implements View.OnClickListener {

	private String link;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_detail);

		getMyActionBar().setDisplayHomeAsUpEnabled(true);

		TextView description = (TextView) findViewById(R.id.activity_info_detail_text);

		ObservableScrollView contentScroll = (ObservableScrollView) findViewById(R.id.activity_info_detail_content_scroll);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_info_detail_fab);
		fab.attachToScrollView(contentScroll);
		fab.setOnClickListener(this);

		Intent getData = getIntent();
		this.link = getData.getStringExtra("link");
		this.title = getData.getStringExtra("title");


		description.setText(getData.getStringExtra("description").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ").replaceAll("&nbsp;", "    "));
		((TextView) findViewById(R.id.activity_info_detail_title)).setText(this.title);
		findViewById(R.id.activity_info_detail_title).setSelected(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.activity_info_detail_fab:
				Intent shareInfo = new Intent(android.content.Intent.ACTION_SEND);
				shareInfo.setType("text/plain");
				shareInfo.putExtra(android.content.Intent.EXTRA_SUBJECT, this.title);
				shareInfo.putExtra(android.content.Intent.EXTRA_TEXT, this.link);
				startActivity(Intent.createChooser(shareInfo, getResources().getString(R.string.share)));
				break;
		}
	}
}
