package app.kimyeonjung.cuk_dom;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.ParseException;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.PushService;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class Application extends android.app.Application {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "xMefxvRsNtBoC6aenZPMUzH5BnNWotppSwdzsePa",
				"BQoA4QKU03G0MHdBPTwsAPCF8LfDzVsu9sWFV2oj");
		ParseUser.enableAutomaticUser();
		ParseUser.getCurrentUser().saveInBackground();
		PushService.setDefaultPushCallback(this, Main.class);
	}

	public void onDestroy() {
		Crouton.cancelAllCroutons();
	}
}