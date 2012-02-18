package org.donorweb.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;

public class CalendarUtils {

	private static final int BLOOD_DONATION_INTERVAL = 90;
	private static final String DONATION_DATE_FORMAT = "MM/dd/yyyy";
	private static String mLastDonatedDay;
	private static String mNextDonationDay;

	// private static int numberOfDaysLeft;

	public static void bloodDonatedToday(Context context) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(DONATION_DATE_FORMAT);
		String dateNow = formatter.format(currentDate.getTime());
		System.out.println("Now the date is :=>  " + dateNow);
		mLastDonatedDay = dateNow;
		currentDate.add(Calendar.DAY_OF_MONTH, BLOOD_DONATION_INTERVAL);
		String sNextDay = formatter.format(currentDate.getTime());
		mNextDonationDay = sNextDay;
		// numberOfDaysLeft = BLOOD_DONATION_INTERVAL;
		System.out.println("Now the date is :=>  " + sNextDay);
		Utils.storeStringPreference(context,
				Constants.NEXT_BLOOD_DONATION_DATE, sNextDay);
		Utils.storeStringPreference(context, Constants.BLOOD_DONATED_DATE,
				mLastDonatedDay);
	}

	public static long getNumberOfDaysLeft(String lastdonateddate,
			String nextDonationDateS) {
		try {
			long nextDonationDay = BLOOD_DONATION_INTERVAL;
			String[] startS = mLastDonatedDay.split("\\/");
			String[] endS = mNextDonationDay.split("\\/");
			Calendar calendarS = Calendar.getInstance();
			Calendar calendarE = Calendar.getInstance();
			int year, mon, day;
			year = Integer.parseInt(startS[2]);
			mon = Integer.parseInt(startS[0]);
			day = Integer.parseInt(startS[1]);
			calendarS.set(year, mon, day);
			year = Integer.parseInt(endS[2]);
			mon = Integer.parseInt(endS[0]);
			day = Integer.parseInt(endS[1]);
			calendarE.set(year, mon, day);

			long diff = calendarE.getTimeInMillis()
					- calendarS.getTimeInMillis();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			nextDonationDay = diffDays;
			return nextDonationDay;
		} catch (Exception s) {
			return 0;
		}

	}

	public static String getNextDonationDate() {
		return mNextDonationDay;
	}

	public static String getLastDonatedDay() {
		return mLastDonatedDay;
	}
}
