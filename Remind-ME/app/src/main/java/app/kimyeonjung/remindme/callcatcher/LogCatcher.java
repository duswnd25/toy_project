package app.kimyeonjung.remindme.callcatcher;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import app.kimyeonjung.remindme.R;
import app.kimyeonjung.remindme.ui.ShowCallInfo;
import app.kimyeonjung.remindme.utils.Keys;

public class LogCatcher extends BroadcastReceiver {

	private static int prevState = TelephonyManager.CALL_STATE_IDLE;
	private SharedPreferences savedData;

	@Override
	public void onReceive(final Context context, final Intent intent) {

		this.savedData = PreferenceManager.getDefaultSharedPreferences(context);
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		telManager.listen(new PhoneStateListener() {
			public void onCallStateChanged(int state, String incomingNumber) {
				if (prevState != state && state == TelephonyManager.CALL_STATE_RINGING) {
					showCallInfoAlert(context, incomingNumber);
				}
				prevState = state;
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			this.showSmsReceiveAlert(intent, context);
		}
	}

	private void showCallInfoAlert(final Context context, String incomingNumber) {
		if (savedData.getBoolean("alert_call", true)) {

			boolean block = savedData.getBoolean("alert_call_block", true);
			boolean noBlock = savedData.getBoolean("alert_call_block_no", true);

			ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.ContactDBName);
			query.fromLocalDatastore();
			query.whereEqualTo("tel", PhoneNumberUtils.formatNumber(incomingNumber));

			if (!block && noBlock) {
				query.whereEqualTo("block", false);
			} else if (block && !noBlock) {
				query.whereEqualTo("block", true);
			}

			if (!block && !noBlock) {
				query.cancel();
			} else {
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					@Override
					public void done(ParseObject parseObject, ParseException e) {
						if (e == null) {
							Intent i = new Intent(context, ShowCallInfo.class);
							i.putExtra("name", parseObject.getString("name"));
							i.putExtra("name", parseObject.getString("memo"));
							i.putExtra("block", parseObject.getString("block"));
							i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
							i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
							try {
								pi.send();
							} catch (PendingIntent.CanceledException error) {
								error.printStackTrace();
							}
						}
					}
				});
			}
		}
	}

	private void showSmsReceiveAlert(Intent intent, final Context context) {
		if (savedData.getBoolean("alert_message", true)) {

			boolean block = savedData.getBoolean("alert_message_block", true);
			boolean noBlock = savedData.getBoolean("alert_message_block_no", true);

			Bundle bundle = intent.getExtras();
			final Object messages[] = (Object[]) bundle.get("pdus");
			final SmsMessage smsMessage[] = new SmsMessage[messages.length];

			for (int i = 0; i < messages.length; i++) {
				// PDU 포맷으로 되어 있는 메시지를 복원합니다.
				smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
			}

			String origNumber = smsMessage[0].getOriginatingAddress();
			final String message = smsMessage[0].getMessageBody();

			ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.ContactDBName);
			query.fromLocalDatastore();
			query.whereEqualTo("tel", PhoneNumberUtils.formatNumber(origNumber));

			if (!block && noBlock) {
				query.whereEqualTo("block", false);
			} else if (block && !noBlock) {
				query.whereEqualTo("block", true);
			}

			if (!block && !noBlock) {
				query.cancel();
			} else {
				query.getFirstInBackground(new GetCallback<ParseObject>() {
					@Override
					public void done(ParseObject parseObject, ParseException e) {
						if (e == null) {
							Bitmap mainIcon = BitmapFactory.decodeResource(context.getResources(), parseObject.getBoolean("block") ? R.drawable.ic_block : R.drawable.ic_account);
							NotificationCompat.Builder mBuilder =
									new NotificationCompat.Builder(context)
											.setContentTitle(parseObject.getString("name") + " 이 문자를 보냈습니다.")
											.setContentText(message)
											.setSmallIcon(R.mipmap.ic_launcher)
											.setAutoCancel(true)
											.setLargeIcon(mainIcon);
							NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
							mNotificationManager.notify(1, mBuilder.build());
						}
					}
				});
			}
		}
	}
}