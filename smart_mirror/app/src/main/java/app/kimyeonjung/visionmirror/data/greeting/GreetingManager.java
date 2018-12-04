package app.kimyeonjung.visionmirror.data.greeting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Random;

import app.kimyeonjung.visionmirror.R;

public class GreetingManager {
    private Context context;

    public GreetingManager(Context context) {
        this.context = context;
    }

    public String getRandomGreetingMessage() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = pref.getString(context.getResources().getString(R.string.user_name_pref), "Hey");
        String[] messageArray = context.getResources().getStringArray(R.array.greeting_messages);
        return userName + " " + messageArray[new Random().nextInt(messageArray.length - 1)];
    }
}
