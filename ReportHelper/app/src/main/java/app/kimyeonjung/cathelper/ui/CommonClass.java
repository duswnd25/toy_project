package app.kimyeonjung.cathelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import app.kimyeonjung.cathelper.R;
import app.kimyeonjung.cathelper.ui.menu.ContextMenu;

/**
 * Created by YeonJung on 2015-06-07.
 * 공용상속클래스
 */
public class CommonClass extends ActionBarActivity implements OnMenuItemClickListener {

	private ContextMenuDialogFragment mMenuDialogFragment;
	private FragmentManager fragmentManager;
	private ActionBar actionbar;

	public CommonClass() {

		ContextMenu contextMenu = new ContextMenu();
		MenuParams menuParams = new MenuParams();
		menuParams.setActionBarSize(360);
		menuParams.setMenuObjects(contextMenu.getMenuObject());

		mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
		fragmentManager = getSupportFragmentManager();
	}

	protected ActionBar getActionbar() {
		return this.actionbar;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.actionbar = getSupportActionBar();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_menu:
				mMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onMenuItemClick(View clickedView, int position) {
		switch (position) {
			case 0:
				mMenuDialogFragment.dismiss();
				break;
			case 1:
				Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				startActivity(new Intent(this, Setting.class));
				break;
		}
	}
}
