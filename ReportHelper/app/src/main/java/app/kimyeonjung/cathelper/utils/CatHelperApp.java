package app.kimyeonjung.cathelper.utils;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;

import io.fabric.sdk.android.Fabric;

public class CatHelperApp extends Application {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());
		Parse.initialize(this, "vPBel4g0oD7Jgr0ZoaPvesnYGAfb6NiTfPI0JP8b", "jFedwEpgtnRteDManEGs7ept01moAxJLnqA8Sox0");
	}
}