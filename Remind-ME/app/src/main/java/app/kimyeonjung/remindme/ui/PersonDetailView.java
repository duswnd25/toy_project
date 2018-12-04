package app.kimyeonjung.remindme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
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

public class PersonDetailView extends CommonClass implements View.OnClickListener {

	private MaterialEditText nameEdit, telEdit, memoEdit;
	private Switch manageSwitch, blockSwitch;
	private String originalName, originalTel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_info);

		Intent getData = getIntent();

		final ImageView manageImage = (ImageView) findViewById(R.id.activity_contact_info_manage_image);
		final ImageView blockImage = (ImageView) findViewById(R.id.activity_contact_info_block_image);

		this.nameEdit = (MaterialEditText) findViewById(R.id.activity_contact_info_name);
		this.telEdit = (MaterialEditText) findViewById(R.id.activity_contact_info_phone);
		this.memoEdit = (MaterialEditText) findViewById(R.id.activity_contact_info_memo);
		this.manageSwitch = (Switch) findViewById(R.id.activity_contact_info_manage_switch);
		this.blockSwitch = (Switch) findViewById(R.id.activity_contact_info_block_switch);

		this.nameEdit.setText(getData.getStringExtra("name"));
		this.telEdit.setText(getData.getStringExtra("tel"));
		this.memoEdit.setText(getData.getStringExtra("memo"));

		this.originalName = getData.getStringExtra("name");
		this.originalTel = getData.getStringExtra("tel");

		if (!getData.getBooleanExtra("manage", false)) {
			this.manageSwitch.setChecked(!getData.getBooleanExtra("manage", false));
			manageImage.setImageResource(R.drawable.ic_no_watch);
		}

		if (getData.getBooleanExtra("block", false)) {
			this.blockSwitch.setChecked(getData.getBooleanExtra("block", false));
			blockImage.setImageResource(R.drawable.ic_block);
		}

		findViewById(R.id.activity_contact_info_save).setOnClickListener(this);
		findViewById(R.id.activity_contact_info_cancel).setOnClickListener(this);


		this.blockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					blockImage.setImageResource(R.drawable.ic_block);
				} else {
					blockImage.setImageResource(R.drawable.ic_call);
				}
			}
		});

		this.manageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					manageImage.setImageResource(R.drawable.ic_no_watch);
				} else {
					manageImage.setImageResource(R.drawable.ic_watch);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.activity_contact_info_save:
				this.updateProfile();
				break;
			case R.id.activity_contact_info_cancel:
				finish();
				break;
		}
	}

	private void updateProfile() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.ContactDBName);
		query.fromLocalDatastore();
		query.whereEqualTo("tel", this.originalTel);
		query.whereEqualTo("name", this.originalName);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject contact, ParseException e) {
				contact.put("name", nameEdit.getText().toString());
				contact.put("tel", PhoneNumberUtils.formatNumber(telEdit.getText().toString()));
				contact.put("manage", !manageSwitch.isChecked());
				contact.put("block", blockSwitch.isChecked());
				contact.put("memo", memoEdit.getText().toString());
				contact.pinInBackground(new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e != null) {
							Toast.makeText(PersonDetailView.this, "저장 실패!", Toast.LENGTH_SHORT).show();
							Log.e(getString(R.string.app_name), e.getMessage());
						} else {
							finish();
						}
					}
				});
			}
		});
	}
}
