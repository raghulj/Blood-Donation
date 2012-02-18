package org.donorweb;

import org.donorweb.activity.PlaceInformationActivity;
import org.donorweb.utils.Constants;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class C2DMMessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.w("C2DM", "Message Receiver called");
		if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
			Log.w("C2DM", "Received message");
			final String payload = intent.getStringExtra("payload");
			final String notificationText = intent
					.getStringExtra("notificationText");
			Log.d("C2DM", "dmControl: payload = " + payload);
			// TODO Send this to my application server to get the real data
			// Lets make something visible to show that we received the message
			createNotification(context, payload, notificationText);

		}
	}

	public void createNotification(Context context, String payload,
			String notificationText) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,
				"Message received", System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		Intent intent = new Intent(context, PlaceInformationActivity.class);
		intent.putExtra("payload", payload);
		intent.putExtra(Constants.PUSH_MESSAGE, true);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		notification.setLatestEventInfo(context, "Blood requirement",
				notificationText, pendingIntent);
		notificationManager.notify(0, notification);

	}

}
