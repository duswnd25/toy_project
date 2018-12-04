package app.kimyeonjung.cathelper.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import app.kimyeonjung.cathelper.R;

public class Register extends ActionBarActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);

		this.initButton();
	}

	private void initButton() {
		findViewById(R.id.register_log_in).setOnClickListener(this);
		findViewById(R.id.register_sign_in).setOnClickListener(this);
	}

	private void signIn() {
		ParseUser user = new ParseUser();
		user.setUsername(((EditText) findViewById(R.id.register_id)).getText().toString());
		user.setPassword(((EditText) findViewById(R.id.register_pw)).getText().toString());
		user.setEmail("user@thegame.com");

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					Toast.makeText(Register.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
					logIn(((EditText) findViewById(R.id.register_id)).getText().toString(), ((EditText) findViewById(R.id.register_pw)).getText().toString());
				} else {
					Toast.makeText(Register.this, "다시 시도해 주세요\n" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void logIn(String id, String pw) {
		ParseUser.logInInBackground(id, pw, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					Toast.makeText(Register.this, "로그인 성공", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(Register.this, "다시 시도해 주세요\n" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void onClick(View v) {
		if (isAllDataAvail()) {
			switch (v.getId()) {
				case R.id.register_log_in:
					this.logIn(((EditText) findViewById(R.id.register_id)).getText().toString(), ((EditText) findViewById(R.id.register_pw)).getText().toString());
					break;
				case R.id.register_sign_in:
					this.signIn();
					break;
			}
		} else {
			Toast.makeText(this, "빈칸을 입력해 주세요.", Toast.LENGTH_SHORT).show();
		}

	}

	private boolean isAllDataAvail() {
		EditText id = (EditText) findViewById(R.id.register_id);
		EditText pw = (EditText) findViewById(R.id.register_pw);
		return !(id.getText().toString().isEmpty() || pw.getText().toString().isEmpty());
	}
}



