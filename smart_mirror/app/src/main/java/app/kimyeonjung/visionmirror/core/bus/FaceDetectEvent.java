package app.kimyeonjung.visionmirror.core.bus;

import android.content.Context;
import android.util.Log;

import app.kimyeonjung.visionmirror.R;

public class FaceDetectEvent {
    private double happiness;

    public FaceDetectEvent(double happiness) {
        this.happiness = happiness;
    }

    public String getHappinessMessage(Context context) {
        String[] messages = context.getResources().getStringArray(R.array.happiness_array);
        if (1.0 >= happiness && happiness > 0.75) {
            return messages[0];
        } else if (0.75 >= happiness && happiness > 0.5) {
            return messages[1];
        } else if (0.5 >= happiness && happiness > 0.25) {
            return messages[2];
        } else if (0.25 >= happiness && happiness > 0.0) {
            return messages[3];
        } else if (0.0 >= happiness && happiness > -0.25) {
            return messages[4];
        } else if (0.25 >= happiness && happiness > -0.5) {
            return messages[5];
        } else if (0.5 >= happiness && happiness > -0.75) {
            return messages[6];
        } else {
            return messages[4];
        }
    }
}