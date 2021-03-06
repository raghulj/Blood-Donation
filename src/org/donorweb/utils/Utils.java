package org.donorweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.donorweb.R;
import org.donorweb.activity.AbstractDonateBloodActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Utils {

	public static void toast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static String getHttpRequest(AbstractDonateBloodActivity activity,
			String url) {

		final DefaultHttpClient httpClient = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = httpClient.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.d("UTILS", "Http not ok");
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				Log.d("UTILS", "rsponse enetiyti " + entity.toString());
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					return inputStreamToString(inputStream);
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest.abort();
		}
		return null;
	}

	public static String inputStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();

		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		// Read response until the end
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Return full string
		return total.toString();
	}

	public static String getDate(String dateStr) {
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+'");
		Date date = null;
		try {
			date = formatter.parse(dateStr.substring(0, 24));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMATTER = new SimpleDateFormat("MMM dd'('E')'");
		return FORMATTER.format(date);
		// System.out.println("OldDate-->"+dateStr);
		// System.out.println("NewDate-->"+);
	}

	public static String getTime(String dateStr) throws ParseException {
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+'");
		Date date = formatter.parse(dateStr.substring(0, 24));
		FORMATTER = new SimpleDateFormat("HH:mm");
		String newDate = FORMATTER.format(date);
		System.out.println(newDate);
		return newDate;
	}

	public static int getAGroupColor(int unitVal) {
		if (unitVal <= Constants.A_RED_MAX) {
			return Constants.RED;
		} else if (unitVal <= Constants.A_ORA_MAX) {
			return Constants.ORANGE;
		} else if (unitVal <= Constants.A_YEL_MAX) {
			return Constants.YELLOW;
		} else {
			return Constants.GREEN;
		}
	}

	public static int getBGroupColor(int unitVal) {
		if (unitVal <= Constants.B_RED_MAX) {
			return Constants.RED;
		} else if (unitVal <= Constants.B_ORA_MAX) {
			return Constants.ORANGE;
		} else if (unitVal <= Constants.B_YEL_MAX) {
			return Constants.YELLOW;
		} else {
			return Constants.GREEN;
		}
	}

	public static int getABGroupColor(int unitVal) {
		if (unitVal <= Constants.AB_RED_MAX) {
			return Constants.RED;
		} else if (unitVal <= Constants.AB_ORA_MAX) {
			return Constants.ORANGE;
		} else if (unitVal <= Constants.AB_YEL_MAX) {
			return Constants.YELLOW;
		} else {
			return Constants.GREEN;
		}
	}

	public static int getOGroupColor(int unitVal) {
		if (unitVal <= Constants.O_RED_MAX) {
			return Constants.RED;
		} else if (unitVal <= Constants.O_ORA_MAX) {
			return Constants.ORANGE;
		} else if (unitVal <= Constants.O_YEL_MAX) {
			return Constants.YELLOW;
		} else {
			return Constants.GREEN;
		}
	}

	public static String getYesterdayDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
		calendar.set(Calendar.DAY_OF_MONTH, (calendar.get(Calendar.DATE) - 1));
		String date = format.format(calendar.getTime());
		return date;
	}

	public static int getResourceColor(int color) {
		Log.d("88888", "color value " + color);
		switch (color) {
		case 1:
			return R.drawable.red_drawable;
		case 2:
			Log.d("88888", "color value " + R.drawable.orange_drawable);
			return R.drawable.orange_drawable;
		case 3:
			return R.drawable.yellow_drawable;
		case 4:
			return R.drawable.green_drawable;
		}
		return -1;
	}

	// Method to store a value in shared preference file
	public static void storeStringPreference(Context context, String key,
			String value) {
		Editor editor = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getStringPreference(Context context, String key) {
		SharedPreferences savedSession = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
		return savedSession.getString(key, null);
	}

	public static ProgressDialog showProgressDialog(Context context,
			String message, boolean cancelable) {
		final ProgressDialog dialog = new ProgressDialog(context);
		if (message != null && !message.equals("")) {
			dialog.setMessage(message);
		} else {
			dialog.setMessage("  Loading...");
		}

		dialog.setIndeterminate(true);
		dialog.setCancelable(cancelable);
		return dialog;
	}

	public static String truncate(String value) {

		if (value != null && value.length() > 30) {
			value = value.substring(0, 30) + "..";
			return value;
		}
		return value;
	}

	public static void addToCalendar(Context ctx, final String title) {
		final ContentResolver cr = ctx.getContentResolver();
		Cursor cursor;
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

		Date dd = new Date("2012/02/19 05:07:00");
		cal.set(111 + 1900, 01, 25, 12, 30, 00);

		final long dtstart = cal.getTimeInMillis();

		Calendar endCal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		endCal.set(111 + 1900, 01, 25, 18, 30, 00);
		final long dtend = endCal.getTimeInMillis();

		if (Integer.parseInt(Build.VERSION.SDK) == 8)
			cursor = cr.query(
					Uri.parse("content://com.android.calendar/calendars"),
					new String[] { "_id", "displayname" }, null, null, null);
		else
			cursor = cr.query(Uri.parse("content://calendar/calendars"),
					new String[] { "_id", "displayname" }, null, null, null);
		if (cursor.moveToFirst()) {
			final String[] calNames = new String[cursor.getCount()];
			final int[] calIds = new int[cursor.getCount()];
			for (int i = 0; i < calNames.length; i++) {
				calIds[i] = cursor.getInt(0);
				calNames[i] = cursor.getString(1);
				cursor.moveToNext();
			}

			// AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			// builder.setSingleChoiceItems(calNames, -1, new
			// DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			ContentValues cv = new ContentValues();
			cv.put("calendar_id", calIds[0]);
			cv.put("title", title);
			cv.put("dtstart", dtstart);
			cv.put("hasAlarm", 1);
			cv.put("dtend", dtend);

			Uri newEvent;
			if (Integer.parseInt(Build.VERSION.SDK) == 8)
				newEvent = cr.insert(
						Uri.parse("content://com.android.calendar/events"), cv);
			else
				newEvent = cr.insert(
						Uri.parse("content://com.android.calendar/events"), cv);

			if (newEvent != null) {
				long id = Long.parseLong(newEvent.getLastPathSegment());
				ContentValues values = new ContentValues();
				values.put("event_id", id);
				values.put("method", 1);
				values.put("minutes", 15); // 15 minuti
				if (Integer.parseInt(Build.VERSION.SDK) == 8)
					cr.insert(Uri
							.parse("content://com.android.calendar/reminders"),
							values);
				else
					cr.insert(Uri.parse("content://calendar/reminders"), values);

			}
			// dialog.cancel();
			// }
			//
			// });

			// builder.create().show();
		}
		cursor.close();
	}

}
