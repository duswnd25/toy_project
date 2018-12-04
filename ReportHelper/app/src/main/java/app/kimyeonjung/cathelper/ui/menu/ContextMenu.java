package app.kimyeonjung.cathelper.ui.menu;

import android.annotation.SuppressLint;

import com.yalantis.contextmenu.lib.MenuObject;

import java.util.ArrayList;
import java.util.List;

import app.kimyeonjung.cathelper.R;

/**
 * Created by YeonJung on 2015-06-07.
 * 메인 컨텍스트 메뉴를 위한 클래스
 * 여기서 메뉴를 설정하고 get으로 반화ㄴ함
 */
public class ContextMenu {
	private List<MenuObject> menuObjects;

	@SuppressLint("ResourceAsColor")
	public ContextMenu() {

		MenuObject dismiss = new MenuObject("닫기");
		dismiss.setResource(R.drawable.ic_clear_white);
		dismiss.setBgResource(R.color.indigo_500);

		MenuObject account = new MenuObject("계정");
		account.setResource(R.drawable.ic_account_white);
		account.setBgResource(R.color.cyan_700);

		MenuObject setting = new MenuObject("설정");
		setting.setResource(R.drawable.ic_setting_white);
		setting.setBgResource(R.color.lime_600);


		menuObjects = new ArrayList<>();
		menuObjects.add(dismiss);
		menuObjects.add(account);
		menuObjects.add(setting);
	}

	public List<MenuObject> getMenuObject() {
		return this.menuObjects;
	}
}
