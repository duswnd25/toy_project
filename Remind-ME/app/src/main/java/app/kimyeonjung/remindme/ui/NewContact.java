package app.kimyeonjung.remindme.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import app.kimyeonjung.remindme.R;
import app.kimyeonjung.remindme.utils.Keys;

/**
 * Created by YeonJung on 2015-06-07.
 * 새 연락처 액티비티
 */

public class NewContact extends CommonClass implements View.OnClickListener {

	private MaterialEditText nameEdit, telEdit;
	private Switch manageSwitch, blockSwitch;
	private Cursor contactCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);

		this.nameEdit = (MaterialEditText) findViewById(R.id.activity_contact_info_name);
		this.telEdit = (MaterialEditText) findViewById(R.id.activity_contact_info_phone);
		this.blockSwitch = (Switch) findViewById(R.id.activity_contact_info_block_switch);
		this.manageSwitch = (Switch) findViewById(R.id.activity_contact_info_manage_switch);

		findViewById(R.id.activity_contact_info_cancel).setOnClickListener(this);
		findViewById(R.id.activity_contact_info_save).setOnClickListener(this);


		final ImageView manageImage = (ImageView) findViewById(R.id.activity_contact_info_manage_image);
		final ImageView blockImage = (ImageView) findViewById(R.id.activity_contact_info_block_image);

		this.manageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					manageImage.setImageResource(R.drawable.ic_block);
				} else {
					manageImage.setImageResource(R.drawable.ic_call);
				}
			}
		});

		this.blockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					blockImage.setImageResource(R.drawable.ic_no_watch);
				} else {
					blockImage.setImageResource(R.drawable.ic_watch);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.activity_contact_info_save:
				makeNewContact();
				break;
			case R.id.activity_contact_info_cancel:
				break;
		}
	}

	private void makeNewContact() {
		final String name, tel, memo;
		final boolean manage, block;

		name = this.nameEdit.getText().toString();
		tel = this.telEdit.getText().toString();
		manage = !this.manageSwitch.isChecked();
		memo = ((EditText) findViewById(R.id.activity_contact_info_memo)).getText().toString();
		block = this.blockSwitch.isChecked();

		ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.ContactDBName);
		query.fromLocalDatastore();
		query.whereEqualTo("name", name);
		query.whereEqualTo("tel", tel);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject parseObject, ParseException e) {
				if (parseObject == null) {
					ParseObject contact = new ParseObject(Keys.ContactDBName);
					contact.put("name", name);
					contact.put("tel", PhoneNumberUtils.formatNumber(tel));
					contact.put("manage", manage);
					contact.put("block", block);
					contact.put("memo", memo);
					contact.pinInBackground(new SaveCallback() {
						@Override
						public void done(ParseException e) {
							if (e != null) {
								Toast.makeText(NewContact.this, "저장 실패!", Toast.LENGTH_SHORT).show();
								Log.e(getString(R.string.app_name), e.getMessage());
							} else {
								finish();
							}
						}
					});
				} else {
					Toast.makeText(NewContact.this, "이미 등록된 연락처 입니다.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_from_contact:
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
				startActivityForResult(intent, 0);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == -1) {
			contactCursor = getContentResolver().query(
					data.getData(),
					new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
							ContactsContract.CommonDataKinds.Phone.NUMBER
					},
					null,
					null,
					null
			);
			contactCursor.moveToFirst();
			this.nameEdit.setText(contactCursor.getString(0));
			this.telEdit.setText(contactCursor.getString(1));
			contactCursor.close();
		}
	}
}