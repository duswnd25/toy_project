package app.studiotft.sid_radio;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import app.studiotft.sid_radio.ui.info.Info;
import io.fabric.sdk.android.Fabric;

/**
 * Created by YeonJung on 2015-07-02.
 * Application 클래스
 */
public class app extends Application {

	public app() {
	}

	@Override
	public void onCreate() {
		Fabric.with(this, new Crashlytics());
		Parse.initialize(this, "DKwR8nfWWdhvftrrCPUtC8n6lQGQ8Y9DUs6gY7pz", "4XLJJwuQpaswL3P84HFrvMG9fmWFwCbPDtm8jtBb");
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.setDefaultPushCallback(this, Info.class);
	}
}
