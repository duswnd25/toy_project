package app.kimyeonjung.remindme.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import app.kimyeonjung.remindme.R;
import app.kimyeonjung.remindme.contact.ContactItem;
import app.kimyeonjung.remindme.contact.ListViewAdapter;
import app.kimyeonjung.remindme.utils.Keys;


public class MainActivity extends CommonClass implements View.OnClickListener {

	private String suggestTel = null;
	private ArrayList<ContactItem> contactIemArrayList = new ArrayList<>();
	private ListViewAdapter adapter;
	private TextView suggestName, suggestDate;
	private int prevSelect = 0;
	private boolean isFirst = true;

	protected void onResume() {
		super.onResume();
		if (!isFirst) {
			getContactList(prevSelect);
		} else {
			isFirst = false;
		}
		this.getSuggestContact();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getMyActionBar().setHomeButtonEnabled(true);

		this.adapter = new ListViewAdapter(this, contactIemArrayList);

		ListView manageListView = (ListView) findViewById(R.id.activity_main_list);
		manageListView.setDividerHeight(0);
		manageListView.setAdapter(adapter);
		manageListView.setEmptyView(findViewById(R.id.activity_main_empty));

		this.suggestDate = (TextView) findViewById(R.id.activity_main_recent_date);
		this.suggestName = (TextView) findViewById(R.id.activity_main_name);
		this.suggestName.setSelected(true);
		findViewById(R.id.activity_main_suggest_box).setOnClickListener(this);
		Spinner selectCareTypeSpinner = (Spinner) findViewById(R.id.activity_main_spinner);

		String[] careTypeArray = new String[]{"전체", "관리대상", "비 관리대상"};
		ArrayAdapter<String> storeList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, careTypeArray);
		selectCareTypeSpinner.setAdapter(storeList);
		selectCareTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				prevSelect = position;
				getContactList(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void getSuggestContact() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.ContactDBName);
		query.fromLocalDatastore();
		query.whereEqualTo("manage", true);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> contactList,
			                 ParseException error) {
				if (error == null && !contactList.isEmpty()) {
					int num = (int) (Math.random() * contactList.size());
					suggestName.setText(contactList.get(num).getString("name"));
					suggestDate.setText(contactList.get(num).getString("tel"));
					suggestTel = contactList.get(num).getString("tel");
				} else if (contactList.isEmpty()) {
					suggestName.setText(null);
					suggestDate.setText(null);
					suggestTel = null;
				} else {
					Log.e(getString(R.string.app_name) + " - 저장된 연락처 조회", error.getLocalizedMessage());
				}
			}
		});
	}

	private void getContactList(int type) {
		this.contactIemArrayList.clear();
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.ContactDBName);
		query.fromLocalDatastore();
		switch (type) {
			case 0:
				break;
			case 1:
				query.whereEqualTo("manage", true);
				break;
			case 2:
				query.whereEqualTo("manage", false);
				break;
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> contactList,
			                 ParseException error) {
				if (error == null && !contactList.isEmpty()) {
					for (ParseObject item : contactList) {
						contactIemArrayList.add(new ContactItem(
								item.getString("name"),
								item.getString("tel"),
								item.getString("memo"),
								item.getBoolean("manage"),
								item.getBoolean("block")
						));
					}
					adapter.notifyDataSetChanged();
				} else {
					if (error != null) {
						Log.e(getString(R.string.app_name) + " - 저장된 연락처 조회", error.getLocalizedMessage());
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add:
				startActivity(new Intent(this, NewContact.class));
				break;
			case R.id.action_settings:
				startActivity(new Intent(this, Setting.class));
				break;
			case R.id.action_reload:
				this.getContactList(prevSelect);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.activity_main_suggest_box:
				if (this.suggestTel != null) {
					this.call();
				}
				break;
		}
	}

	private void call() {
		AlertDialog.Builder send_phone_number_alert = new AlertDialog.Builder(MainActivity.this);
		send_phone_number_alert
				.setMessage(R.string.really_call)
				.setCancelable(false)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(Intent.ACTION_CALL,
										Uri.parse("tel:" + suggestTel));
								startActivity(intent);
							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		AlertDialog alert = send_phone_number_alert.create();
		alert.show();
	}
}
