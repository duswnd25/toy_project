package app.studiotft.sid_radio.ui.info;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import app.studiotft.sid_radio.CommonClass;
import app.studiotft.sid_radio.R;


public class Info extends CommonClass implements ActionBar.OnNavigationListener {

	private String[] rssUrls = new String[]{"http://innocent-music.kr/main/v4_notices/rss", "http://innocent-music.kr/main/v4_infos/rss"};
	private ListView infoListView;
	private Boolean isNotice = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		getMyActionBar().setDisplayHomeAsUpEnabled(true);
		getMyActionBar().setTitle(null);

		getMyActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		String[] infoItem = getResources().getStringArray(R.array.info_spinner_arr);

		ArrayAdapter ActionSpinnerAdapter = new ArrayAdapter<>(this,
				R.layout.item_dropdown, android.R.id.text1, infoItem);
		getMyActionBar().setListNavigationCallbacks(ActionSpinnerAdapter, this);

		this.infoListView = (ListView) findViewById(R.id.activity_info_list_view);
		this.infoListView.setDividerHeight(0);

		int[] bgArr = new int[]{
				R.drawable.bg_album,
				R.drawable.bg_inno,
				R.drawable.bg_pattern
		};
		findViewById(R.id.activity_info_root).setBackgroundResource(bgArr[((int) (Math.random() * bgArr.length))]);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		switch (itemPosition) {
			case 0:
				this.isNotice = true;
				new ParsingRSS().execute();
				break;
			case 1:
				this.isNotice = false;
				new ParsingRSS().execute();
				break;
		}
		return false;
	}

	private class ParsingRSS extends AsyncTask<String, String, String> {
		private ProgressDialog progress = new ProgressDialog(Info.this);
		private List<String> titles;
		private List<String> descriptions;
		private List<String> links;
		private List<String> dates;

		@Override
		protected void onPreExecute() {
			progress.setMessage(getString(R.string.loading));
			progress.setCancelable(false);
			progress.show();
			titles = new ArrayList<>();
			descriptions = new ArrayList<>();
			links = new ArrayList<>();
			dates = new ArrayList<>();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				Document doc = Jsoup
						.connect(isNotice ? rssUrls[0] : rssUrls[1])
						.ignoreContentType(isTaskRoot())
						.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
						.timeout(9999 * 9999)
						.get();

				Elements items = doc.getElementsByTag("item");

				for (Element temp : items) {
					titles.add(
							temp
									.getElementsByTag("title")
									.text()
									.replace("<![CDATA[", "")
									.replace("]]>", "")
					);
					descriptions.add(
							temp
									.getElementsByTag("description")
									.text()
									.replace("<![CDATA[", "")
									.replace("]]>", "")
					);
					links.add(
							/*링크 부분이 넘어올 때 </link>http 이런식으로 넘어와서 comments 주소를 가져옴*/
							temp
									.getElementsByTag("comments")
									.text()
									.replace("<![CDATA[", "")
									.replace("]]>", "")
					);
					dates.add(
							temp
									.getElementsByTag("pubDate")
									.text()
									.replace("<![CDATA[", "")
									.replace("]]>", "")
					);
				}
			} catch (Exception e) {
				Log.e("오류", e.getLocalizedMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			progress.dismiss();

			if (titles.size() == 0) {
				Toast toast = Toast.makeText(Info.this, getResources()
						.getText(R.string.error), Toast.LENGTH_LONG);
				toast.show();
			} else {
				String[] list_title = titles.toArray(new String[titles.size()]);
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
						Info.this, R.layout.item_common_list_item, R.id.item_common_list_item_text,
						list_title);
				infoListView.setAdapter(adapter);
				infoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = new Intent(Info.this, InfoDetail.class);
						intent.putExtra("title", titles.get(position));
						intent.putExtra("description", descriptions.get(position));
						intent.putExtra("link", links.get(position));
						intent.putExtra("dates", dates.get(position));
						startActivity(intent);
					}
				});
			}
		}
	}
}
