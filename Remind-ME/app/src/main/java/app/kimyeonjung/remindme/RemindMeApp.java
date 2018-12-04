package app.kimyeonjung.remindme;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;

import io.fabric.sdk.android.Fabric;

public class RemindMeApp extends Application {

	public RemindMeApp() {

	}

	@Override
	public void onCreate() {
		Fabric.with(this, new Crashlytics());
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "N2uSMBfy7o3bRmeew9ihFElQJKuMFsnBVerTd4D0", "GF8KJl5FEoCYFEN4N9BGs2kgs8TLynGuf3OytkAv");
	}
}
