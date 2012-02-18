package org.donorweb.utils;

public class Constants {

	public static final String EVENT_DATE_CALENDAR_URL = "http://www.google.com/calendar/feeds/206u2ql56e9gkelav9rl3h0k34@group.calendar.google.com/public/full?alt=jsonc&orderby=starttime&max-results=15&singleevents=true&sortorder=ascending&futureevents=true";

	public static final String BLOOD_GROUP_STOCK_URL = "https://spreadsheets.google.com/feeds/list/p4del_Qc7xjduJAcAHpVmaA/ocz/public/basic?alt=json-in-script";

	public static final String EVENT_ADDRESS = "event.address";
	public final static String C2DM_AUTH = "push.authentication";

	public static final String SHARED_PREFERENCE_FILE = "blood_donation_app";
	public final static String C2DM_EMAIL_ID = "madjokr@gmail.com";

	public static final String PUSH_MESSAGE = "push.notification";

	public static final String BLOOD_DONATED_DATE = "blood.donated.date";

	public static final String NEXT_BLOOD_DONATION_DATE = "next.blood.donation.date";

	public static final int RED = 1; // CRITICAL
	public static final int ORANGE = 2; // VERYLOW
	public static final int YELLOW = 3; // LOW
	public static final int GREEN = 4; // HEALTHY

	public static final int A_RED_MAX = 270;
	public static final int A_ORA_MAX = 360;
	public static final int A_YEL_MAX = 540;
	public static final float A_GRE_MAX = 900f;

	public static final int B_RED_MAX = 180;
	public static final int B_ORA_MAX = 240;
	public static final int B_YEL_MAX = 360;
	public static final float B_GRE_MAX = 900f;

	public static final int O_RED_MAX = 405;
	public static final int O_ORA_MAX = 540;
	public static final int O_YEL_MAX = 810;
	public static final float O_GRE_MAX = 1200f;

	public static final int AB_RED_MAX = 45;
	public static final int AB_ORA_MAX = 60;
	public static final int AB_YEL_MAX = 90;
	public static final float AB_GRE_MAX = 300f;

}
