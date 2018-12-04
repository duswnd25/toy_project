package app.studiotft.sid_radio.ui.main;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.studiotft.sid_radio.CommonClass;
import app.studiotft.sid_radio.R;


public class MainActivity extends CommonClass {

	private List<MainMenuItem> menuItems = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String[] menuArr = getResources().getStringArray(R.array.main_menu_array);
		menuItems.add(new MainMenuItem(menuArr[0], R.drawable.bg_pattern, R.drawable.ic_error_outline_white));
		menuItems.add(new MainMenuItem(menuArr[1], R.drawable.bg_saboten, R.drawable.ic_shopping_cart_white));
		menuItems.add(new MainMenuItem(menuArr[2], R.drawable.bg_miracle, R.drawable.ic_maps_white));
		menuItems.add(new MainMenuItem(menuArr[3], R.drawable.bg_inno, R.drawable.ic_cafe_white));
		menuItems.add(new MainMenuItem(menuArr[4], R.drawable.bg_media, R.mipmap.ic_launcher));
		menuItems.add(new MainMenuItem(menuArr[5], R.drawable.bg_media, R.drawable.ic_twitter_white));
		menuItems.add(new MainMenuItem(menuArr[6], R.drawable.bg_album, R.drawable.ic_setting_white));

		ListView mainMenuListView = (ListView) findViewById(R.id.activity_main_menu_list_view);
		mainMenuListView.setDividerHeight(0);

		ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, menuItems);
		mainMenuListView.setAdapter(adapter);
	}
}
