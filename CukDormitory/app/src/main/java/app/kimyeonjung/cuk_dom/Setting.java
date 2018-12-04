package app.kimyeonjung.cuk_dom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Setting extends CommonClass {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list);
		setActionBarHomeButton();
		Main_list();

	}

	public void Main_list() {

		String[] menu = { "자동로그인", "앱 정보", "기록 지우기", "ADMIN" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu);
		ListView side_list = (ListView) findViewById(android.R.id.list);
		side_list.setAdapter(adapter);
		side_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {

				case 0:
					Intent autolog = new Intent(Setting.this, Autolog.class);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					startActivity(autolog);
					break;

				case 1:
					Intent introduce = new Intent(Setting.this, Introduce.class);
					startActivity(introduce);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);

					break;

				case 2:
					SharedPreferences Log = getSharedPreferences("Success_Log",
							0);

					SharedPreferences Log_admin = getSharedPreferences(
							"Log_admin", 0);

					Editor editor = Log.edit();
					editor.putString("Log", "");
					editor.commit();

					Editor admin_editor = Log_admin.edit();
					admin_editor.putString("Log_admin", "");
					admin_editor.commit();

					Toast toast = Toast.makeText(Setting.this, getResources()
							.getText(R.string.setting_delete),
							Toast.LENGTH_LONG);
					toast.show();

					break;
				case 3:
					Intent admin = new Intent(Setting.this, Admin.class);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					startActivity(admin);
					break;

				}

			}

		});
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
