package org.donorweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.donorweb.R;
import org.donorweb.activity.AbstractDonateBloodActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

}
