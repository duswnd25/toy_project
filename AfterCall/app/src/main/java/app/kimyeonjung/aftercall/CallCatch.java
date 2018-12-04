package app.kimyeonjung.aftercall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallCatch extends BroadcastReceiver {
    private static int pState = TelephonyManager.CALL_STATE_IDLE;

    @Override
    public void onReceive(final Context context, final Intent intent) {

		/*
         * if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
		 * 
		 * String incomingNumber = broadcast
		 * .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
		 * 
		 * Intent send = new Intent(context, Send_sms.class);
		 * send.putExtra("incoming_number", incomingNumber); // "명칭", "실제값"
		 * send.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * send.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
		 * context.startActivity(send);
		 * 
		 * } else if
		 * (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) { String
		 * incomingNumber = broadcast .getString(Intent.EXTRA_PHONE_NUMBER);
		 * Intent send = new Intent(context, Send_sms.class);
		 * send.putExtra("incoming_number", incomingNumber); // "명칭", "실제값"
		 * send.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * send.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
		 * context.startActivity(send); }
		 * 
		 * }
		 */

        TelephonyManager telManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        telManager.listen(new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state != pState) {
                    if (state == TelephonyManager.CALL_STATE_IDLE) {

                    } else if (state == TelephonyManager.CALL_STATE_RINGING) {

                        Intent send = new Intent(context, SendSms.class);
                        send.putExtra("incoming_number", incomingNumber); // "명칭",
                        // "실제값"
                        send.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        send.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        context.startActivity(send);
                    } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    }
                    pState = state;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);

        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
            Intent send = new Intent(context, SendSms.class);
            send.putExtra("incoming_number",
                    intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)); // "명칭",
            // "실제값"
            send.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            send.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            context.startActivity(send);
        }
    }

}
