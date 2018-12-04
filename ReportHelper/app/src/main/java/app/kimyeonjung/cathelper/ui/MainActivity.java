package app.kimyeonjung.cathelper.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerActivity;
import net.daum.mf.speech.api.SpeechRecognizerClient;
import net.daum.mf.speech.api.SpeechRecognizerManager;

import java.util.ArrayList;
import java.util.List;

import app.kimyeonjung.cathelper.R;
import app.kimyeonjung.cathelper.ui.dialog.ShowStatusDialog;


/**
 * 본 sample의 main activity.
 * 직접 음성인식 기능을 제어하는 API를 호출하는 버튼과 기본으로 제공되는 UI를 통해 음성인식을 수행하는
 * 두가지 형태를 제공한다.
 * 음성인식 API의 callback을 받기 위해 {@link net.daum.mf.speech.api.SpeechRecognizeListener} interface를 구현하였다.
 *
 * @author Daum Communications Corp.
 * @since 2013
 */
public class MainActivity extends CommonClass implements View.OnClickListener, SpeechRecognizeListener {

	public static final String APIKEY = "de919f246f680a9bd17bf633464192bd";
	private EditText resultEdit;


	public void onResume() {
		super.onResume();
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			SharedPreferences savedData = PreferenceManager.getDefaultSharedPreferences(this);
			savedData.edit().putString("user_name", currentUser.getUsername()).apply();
			Toast.makeText(MainActivity.this, currentUser.getUsername(), Toast.LENGTH_SHORT).show();
		} else {
			startActivity(new Intent(MainActivity.this, Register.class));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		SpeechRecognizerManager.getInstance().initializeLibrary(this);

		this.listenerInit();
		this.setButtonsStatus(true);
		this.resultEdit = (EditText) findViewById(R.id.main_result_edit);
	}

	private void listenerInit() {
		findViewById(R.id.main_start_record).setOnClickListener(this);
		findViewById(R.id.main_copy).setOnClickListener(this);
		findViewById(R.id.main_send).setOnClickListener(this);
	}

	private void setButtonsStatus(boolean enabled) {
		findViewById(R.id.main_start_record).setEnabled(enabled);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_start_record:
				Intent startRecord = new Intent(getApplicationContext(), VoiceRecoderActivity.class);
				startRecord.putExtra(SpeechRecognizerActivity.EXTRA_KEY_API_KEY, APIKEY);
				startRecord.putExtra(SpeechRecognizerActivity.EXTRA_KEY_SERVICE_TYPE, SpeechRecognizerClient.SERVICE_TYPE_DICTATION);
				startRecord.putExtra(SpeechRecognizerActivity.EXTRA_KEY_SHOW_SUGGEST_LIST, false); // 후보단어 목록 표시 생략
				startActivityForResult(startRecord, 0);
				break;
			case R.id.main_copy:
				if (!isTextEmpty()) {
					Intent sendText = new Intent(android.content.Intent.ACTION_SEND);
					sendText.setType("text/plain");
					sendText.putExtra(Intent.EXTRA_TEXT, this.resultEdit.getText().toString());
					startActivity(Intent.createChooser(sendText, "전달하기"));
				}
				break;
			case R.id.main_send:
				if (!isTextEmpty()) {
					new GetLastOrder().execute();
				}
				break;
		}
	}


	private boolean isTextEmpty() {
		if (this.resultEdit.getText().toString().isEmpty()) {
			Toast.makeText(MainActivity.this, "내용이 없습니다.", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			ArrayList<String> sttResultList = data.getStringArrayListExtra(VoiceRecoderActivity.EXTRA_KEY_RESULT_ARRAY);
			final String[] resultArr = new String[sttResultList.size()];
			for (int i = 0; i < resultArr.length; i++) {
				resultArr[i] = sttResultList.get(i);
			}

			AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
			ab.setSingleChoiceItems(resultArr, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							resultEdit.setText(resultEdit.getText().toString() + " " + resultArr[whichButton]);
							dialog.dismiss();
						}
					});
			ab.show();

		} else if (requestCode == RESULT_CANCELED) {
			// 음성인식의 오류 등이 아니라 activity의 취소가 발생했을 때.
			if (data == null) {
				return;
			}

			int errorCode = data.getIntExtra(VoiceRecoderActivity.EXTRA_KEY_ERROR_CODE, -1);
			String errorMsg = data.getStringExtra(VoiceRecoderActivity.EXTRA_KEY_ERROR_MESSAGE);

			if (errorCode != -1 && !TextUtils.isEmpty(errorMsg)) {
				new AlertDialog.Builder(this).
						setMessage(errorMsg).
						setPositiveButton("확인", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).
						show();
			}
		}
	}

	@Override
	public void onReady() {
	}

	@Override
	public void onBeginningOfSpeech() {
	}

	@Override
	public void onEndOfSpeech() {
	}

	@Override
	public void onError(int errorCode, String errorMsg) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setButtonsStatus(true);
			}
		});

	}

	@Override
	public void onPartialResult(String text) {
	}

	@Override
	public void onResults(Bundle results) {

	}

	@Override
	public void onAudioLevel(float v) {
	}

	@Override
	public void onFinished() {
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// API를 더이상 사용하지 않을 때 finalizeLibrary()를 호출한다.
		SpeechRecognizerManager.getInstance().finalizeLibrary();
	}

	private class GetLastOrder extends AsyncTask<Void, Void, Void> {
		private int maxOrder = 0;
		private ShowStatusDialog statusDialog;
		private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage(getString(R.string.loading));
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				ParseQuery<ParseObject> query = new ParseQuery<>("Todo");
				query.orderByDescending("order");
				List<ParseObject> ob = query.find();
				for (ParseObject item : ob) {
					if (maxOrder < item.getInt("order")) {
						maxOrder = item.getInt("order");
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			progressDialog.dismiss();

			ParseObject newPost = new ParseObject("Todo");
			newPost.put("content", resultEdit.getText().toString());
			newPost.put("done", false);
			newPost.put("order", ++this.maxOrder);
			newPost.put("user", ParseUser.getCurrentUser());
			newPost.setACL(new ParseACL(ParseUser.getCurrentUser()));
			newPost.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {
					if (e == null) {
						statusDialog = new ShowStatusDialog(MainActivity.this, "전송 성공", R.drawable.ic_cloud_done_black);
						statusDialog.show();
					} else {
						statusDialog = new ShowStatusDialog(MainActivity.this, "전송 실패", R.drawable.ic_cloud_off_black);
						statusDialog.show();
					}
				}
			});
		}
	}
}
