package app.studiotft.sid_radio.ui.main;

/**
 * Created by YeonJung on 2015-07-04.
 * 메인메뉴
 */
public class MainMenuItem {
	private String menu;
	private Integer backgroundImgResId;
	private Integer logImgResId;

	public MainMenuItem(String menu, int backgroundImgResId, int logImgResId) {
		this.menu = menu;
		this.backgroundImgResId = backgroundImgResId;
		this.logImgResId = logImgResId;
	}

	public String getMenu() {
		return this.menu;
	}

	public Integer getBackgroundImgResId() {
		return this.backgroundImgResId;
	}

	public Integer getLogImgResId() {
		return this.logImgResId;
	}

}
