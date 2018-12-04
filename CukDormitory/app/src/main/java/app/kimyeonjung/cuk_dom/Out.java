package app.kimyeonjung.cuk_dom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.ParseAnalytics;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

@SuppressLint({ "DefaultLocale" })
public class Out extends CommonClass implements View.OnClickListener {

	private String myResult;
	private String name;
	private String room_id;
	private String what;
	private String total_list;
	private String temp, temp_for_admin;

	Calendar calendar = Calendar.getInstance();
	String today = calendar.getTime().toString().split("\\ ")[0];
	final int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
	final String today_num = calendar.getTime().toString().split("\\ ")[2];
	static SlidingMenu menu;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.out);
		setActionBarHomeButton();
		layoutInit();

		/* 사이드 메뉴 구현 */
		/* 화면 크기를 구해서 대입하고 아래에서 화면크기의 절반으로 설정함 */
		DisplayMetrics display = this.getResources().getDisplayMetrics();
		int width = display.widthPixels;
		int menu_width = width - width / 3;
		if (menu_width < 100) {
			menu_width = 100;
		}

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setBehindWidth(width / 3 * 2);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.sidemenu);
		/* 여기까지 사이드 메뉴 */

		RadioButton outSelectRadio = (RadioButton) findViewById(R.id.out_what2_layout);

		if (today.equals("Fri") == true || today.equals("Sat") == true
				|| today.equals("Sun") == true) {
			outSelectRadio.setText(R.string.out_special_friday);
		}

		agree();
		log();
	}

	private void layoutInit() {
		Button submit = (Button) findViewById(R.id.out_submit_button);
		Button submitLog = (Button) findViewById(R.id.out_log_button);
		submit.setOnClickListener(this);
		submitLog.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.out_submit_button:
			out_submit();
			break;
		case R.id.out_log_button:
			Out.menu.toggle();
			break;
		}
	}

	protected void log() {
		// 사이드메뉴의 텍스트 입력
		total_list = savedData.getString("Log", "");
		TextView side_info = (TextView) findViewById(R.id.side_info);
		side_info.setText(total_list);
	}

	@SuppressWarnings("deprecation")
	public void out_submit() {

		// 호실, 이름, 외출외박 을 String 값으로 입력받음
		name = ((EditText) Out.this.findViewById(R.id.out_name_edit_layout))
				.getText().toString(); // 이름
		room_id = ((EditText) Out.this.findViewById(R.id.out_room_edit_layout))
				.getText().toString(); // 호실
		RadioGroup Select_Group = (RadioGroup) findViewById(R.id.out_what_layout);

		if ("".equals(name) || "".equals(room_id)) {

			Crouton.makeText(Out.this, R.string.out_fill_blank, Style.ALERT)
					.show();

		} else {

			RadioButton Select_Radio = (RadioButton) findViewById(Select_Group
					.getCheckedRadioButtonId()); // 라디오 버튼 ID로 어떤건지 분별
			what = Select_Radio.getText().toString().replace("Outing", "외출")
					.replace("SleepOver", "외박").replace("郊游", "외출")
					.replace("彻夜狂欢", "외박"); // 외출인지 외박인지 입력받음
			Map<String, String> dimensions = new HashMap<String, String>();
			dimensions.put("Day", today);
			dimensions.put("Type", what);
			ParseAnalytics.trackEvent("외출/외박", dimensions);

			Net_check();

			/*
			 * 안드로이드 3.X이상부터는 안드로이드 정책이 변하여 메인 스레드에서 네트워크통신을 시도할 경우 에러로 간주하여 앱
			 * 실행이 불가능해진다. 만일 메인 스레드에서 전부 실행시 네트워크가 불안정하거나 작업이 오래걸릴경우 전체적인 동작이
			 * 느려지면서 (작업하는동안 메인 스레드가 정지되기때문에 앱 전체가 정지된것처럼 보임) 전체적인 성능저하가 일어난다
			 * 때문에 구글에서 이를 방지하기위해 차단한것으로 보임
			 */

			new Thread(new Runnable() { // 실행가능한 새 스레드 생성

						public void run() { // 여기부터 작동코드

							try {

								HttpURLConnection send_to_server = (HttpURLConnection) new URL(
										"http://110.15.152.77/write.php")
										.openConnection();

								send_to_server.setDefaultUseCaches(false);
								send_to_server.setDoInput(true);
								send_to_server.setDoOutput(true);
								send_to_server.setRequestMethod("POST");
								send_to_server.setRequestProperty(
										"content-type",
										"application/x-www-form-urlencoded");
								StringBuffer Send_Buffer = new StringBuffer();

								Send_Buffer.append("name").append("=")
										.append(name).append("&");
								Send_Buffer.append("room_id").append("=")
										.append(room_id).append("&");
								Send_Buffer.append("phone").append("=")
										.append(getMy10DigitPhoneNumber())
										.append("&");
								Send_Buffer.append("what").append("=")
										.append(what);

								PrintWriter Send_PrintWriter = new PrintWriter(
										new OutputStreamWriter(send_to_server
												.getOutputStream(), "utf-8"));
								Send_PrintWriter.write(Send_Buffer.toString());
								Send_PrintWriter.flush();
								BufferedReader Send_BufferedReader = new BufferedReader(
										new InputStreamReader(send_to_server
												.getInputStream(), "utf-8"));
								StringBuilder localStringBuilder = new StringBuilder();
								while (true) {

									String str = Send_BufferedReader.readLine();

									if (str == null) {

										// 서버에서 결과값을 받아서 입력받음
										myResult = localStringBuilder
												.toString();

										/*
										 * 서버로 데이터를 전송하기위해 새 스레드를 생성하여 데이터를
										 * 전송하였는데 이렇게 할 경우 메인UI스레드가 아니기때문에 이
										 * 스레드에서는 UI변경이 불가능해진다(정책상) 그렇기때문에
										 * UI스레드를 새로 만들어서 텍스트부분(total_list,
										 * resultF)을 변경한다.
										 */
										runOnUiThread(new Runnable() {

											@Override
											public void run() {

												/*
												 * 외출/외박중 하나를 반드시~ 부분을 서버에서 받아온
												 * 결과값 이름/호실/유형/시간 신청이 확인되었습니다.
												 * 또는 10시 30분 이후로는 신청이 불가능합니다.
												 * 등으로 변경한다.
												 */
												((TextView) findViewById(R.id.out_resultF_layout))
														.setText(myResult);
												Crouton.makeText(Out.this,
														myResult, Style.CONFIRM)
														.show();
												// SharedPreferneces의 Log중
												// Success_Log 를 호출
												// Log 값을 temp 에 일시적으로 저장
												temp = savedData.getString(
														"Log", "");
												temp_for_admin = savedData
														.getString("Log_admin",
																"");

												if (myResult.contains("없습니다.")
														|| "10시 30분 이후에는 외출/외박 신청을 할 수 없습니다."
																.equals(myResult)
														|| "이름과 방번호를 다시 입력해주세요."
																.equals(myResult)) {
													Editor editor = savedData
															.edit();
													editor.putString("Log",
															"\n" + temp);
													editor.putString(
															"Log_admin",
															"\n"
																	+ "외출/외박 신청 실패\n"
																	+ "사유 : "
																	+ myResult
																	+ "\n"
																	+ getMy10DigitPhoneNumber()
																	+ "\n"
																	+ temp_for_admin);
													editor.commit();

												} else {

													String name = myResult
															.split("\\ ")[0];

													String room = myResult
															.split("\\ ")[2];
													String out = myResult
															.split("\\ ")[4];
													String date = myResult
															.split("\\ ")[6];
													String date2 = myResult
															.split("\\ ")[7];
													String message = myResult
															.split("\\ ")[9];

													// Success_Log값을 새 값으로 변경
													// (새로운값
													// + 기존
													// 값)
													Editor editor = savedData
															.edit();
													editor.putString("Log",
															"\n" + "이름 : "
																	+ name

																	+ "\n호실 : "
																	+ room
																	+ "\n유형 : "
																	+ out
																	+ "\n일자 : "
																	+ date
																	+ " / "
																	+ date2
																	+ "\n"
																	+ message
																	+ temp);

													editor.putString(
															"Log_admin",
															"\n"
																	+ "이름 : "
																	+ name

																	+ "\n호실 : "
																	+ room
																	+ "\n유형 : "
																	+ out
																	+ "\n일자 : "
																	+ date
																	+ " / "
																	+ date2
																	+ "\n"
																	+ message
																	+ "\n번호 : "
																	+ getMy10DigitPhoneNumber()
																	+ "\n"
																	+ temp_for_admin);
													editor.commit();

												}

												log();
											}

										});
										return;

									}

									localStringBuilder.append(str + "\n");

								}

							}

							catch (MalformedURLException localMalformedURLException) {

								return;

							}

							catch (IOException localIOException) {

							}

						}

					}).start();

		}
	}

	public void Net_check() {

		// 인터넷에 연결돼 있나 확인
		ConnectivityManager connect = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
				|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState() == NetworkInfo.State.CONNECTED) {
			// 연결 돼있는 경우

		} else {
			// 연결 돼있지 않은 경우

			AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(
					this);

			alert_internet_status.setTitle(R.string.net_error);
			alert_internet_status.setMessage(R.string.net_error_message);
			alert_internet_status.setPositiveButton(R.string.calcel,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss(); // 닫기
							// SharedPreferneces의 Log중

							temp = savedData.getString("Log", "");
							temp_for_admin = savedData.getString("Log_admin",
									"");

							Editor editor = savedData.edit();
							editor.putString("Log", "\n"
									+ "인터넷 연결 실패로 인한 신청 실패\n" + temp);
							editor.putString("Log_admin", "\n"
									+ "외출/외박 신청 실패\n" + "사유 : " + "인터넷 연결 불가"
									+ "\n번호 : " + getMy10DigitPhoneNumber()
									+ "\n" + temp_for_admin);
							editor.commit();

							// 사이드메뉴의 최근 신청기록 갱신
							TextView side_info = (TextView) findViewById(R.id.side_info);
							total_list = savedData.getString("Log", "");
							side_info.setText(total_list + "\n\n");

						}

					});

			alert_internet_status.show();

		}

	}

	// 최초 실행시 번호전송 동의 다이얼로그 구현
	public void agree() {

		// agree_check에 저장된 값을 check에 저장
		boolean check = savedData.getBoolean("agree", false);

		/*
		 * check값이 0인지 아닌지 확인함 최초실행시 0임 이 값은 앱전체적으로 최초 실행시 Main 액티비티의 push메시지
		 * 등록부분에서 입력받음 push메시지 등록부분이 1회 최초 실행되기 때문에 값이 다시 0으로 초기화될 일은 없음
		 */
		if (check == false) {

			// 0일경우 다이얼로그 구현
			AlertDialog.Builder send_phone_number_alert = new AlertDialog.Builder(
					this);
			// 다이얼로그의 내용은 String 파일에서 불러옴
			send_phone_number_alert
					.setMessage(R.string.out_send_phone_number_alert_text)
					.setCancelable(false)
					.setPositiveButton("동의",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									// 동의할 경우 agree_check의 값을 0에서 1로 변경
									Editor edit = savedData.edit();
									edit.putBoolean("agree", true);
									edit.commit();
								}
							})
					.setNegativeButton("비동의",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									// 비 동의시 Out 액티비티를 종료하여 사용불가로 만듬

									finish();
								}
							});
			AlertDialog alert = send_phone_number_alert.create();
			alert.setTitle("개인정보 활용 동의"); // 다이얼로그 제목
			alert.setIcon(R.drawable.cukholic);
			alert.show();
		}

		else {

			/* 이미 동의했을경우 그냥 넘어감 */

		}

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

}