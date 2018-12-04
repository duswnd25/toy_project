package app.kimyeonjung.aftercall;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.ArrayList;

public class SendSms extends ActionBarActivity {

    public String getMyPhoneNumber() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String rawNumber = mTelephonyManager.getLine1Number();  // may be null or empty
        String formattedNumber;
        formattedNumber = PhoneNumberUtils.formatNumber(rawNumber);
        return formattedNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences SMS_BODY = getSharedPreferences("sms_body", 0);

        Intent intent = getIntent();

        String number = intent.getExtras().getString("incoming_number");

        String body = SMS_BODY.getString("sms_body", "저장된 문구가 없습니다.");

        Toast.makeText(getApplicationContext(), number + "메시지를 전송하였습니다.",
                Toast.LENGTH_LONG).show();

        Toast.makeText(getApplicationContext(), number + "메시지를 전송하였습니다.",
                Toast.LENGTH_LONG).show();
        sendSMS(number, body);
        finish();

    }

    public void sendSMS(String phoneNumber, String message) {
        String SENT = "단문 메시지 발송";
        PendingIntent piSent = PendingIntent.getBroadcast(this, 0, new Intent(
                SENT), 0);
        String DELIVERED = "단문 메시지 도착";
        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);
        SmsManager smsManager = SmsManager.getDefault();

        int length = message.length();
        int MAX_SMS_MESSAGE_LENGTH = 160;
        if (length > MAX_SMS_MESSAGE_LENGTH) {
            ArrayList<String> messageList = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, messageList,
                    null, null);
            smsManager.sendTextMessage(getMyPhoneNumber(), null, phoneNumber + " 님에게 전송함", piSent,
                    piDelivered);
        } else {
            smsManager.sendTextMessage(phoneNumber, null, message, piSent,
                    piDelivered);
        }

    }
}