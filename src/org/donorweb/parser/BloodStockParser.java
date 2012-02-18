package org.donorweb.parser;

import java.util.HashMap;

import org.donorweb.activity.AbstractDonateBloodActivity;
import org.donorweb.model.BloodGroupDetail;
import org.donorweb.utils.Constants;
import org.donorweb.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BloodStockParser {
	private String urlStr;
	private String dateStr;
	private AbstractDonateBloodActivity mActivity;

	/*
	 * var groupA_redMax = 270; var groupA_orangeMax = 360; // Orange colour NOT
	 * supported! var groupA_yellowMax = 540; var groupA_greenMax = 900; var
	 * groupA_max = Math.ceil ( Math.max (groupA_greenMax, groupA_value) / 300 )
	 * * 300;
	 * 
	 * 
	 * var groupB_redMax = 180; var groupB_orangeMax = 240; // Orange colour NOT
	 * supported! var groupB_yellowMax = 360; var groupB_greenMax = 900
	 * 
	 * var groupO_redMax = 405; var groupO_orangeMax = 540; // Orange colour NOT
	 * supported! var groupO_yellowMax = 810; var groupO_greenMax = 1200; var
	 * groupO_max = Math.ceil ( Math.max (groupO_greenMax, groupO_value) / 300 )
	 * * 300;
	 * 
	 * var groupAB_redMax = 45; var groupAB_orangeMax = 60; // Orange colour NOT
	 * supported! var groupAB_yellowMax = 90; var groupAB_greenMax = 300; var
	 * groupAB_max = Math.ceil ( Math.max (groupAB_greenMax, groupAB_value) /
	 * 300 ) * 300;
	 */

	public BloodStockParser(AbstractDonateBloodActivity activity,
			String tUrlStr, String dStr) {
		mActivity = activity;
		urlStr = tUrlStr;
		dateStr = dStr;
	}

	public HashMap<String, BloodGroupDetail> getStockDetails() {
		// initialize
		// InputStream is = null;
		// String result = "";
		// // JSONObject jArray = null;
		// // http post
		// try {
		// HttpClient httpclient = new DefaultHttpClient();
		// HttpPost httppost = new HttpPost(urlStr);
		// HttpResponse response = httpclient.execute(httppost);
		// HttpEntity entity = response.getEntity();
		// is = entity.getContent();
		// } catch (Exception e) {
		// System.out.println(e);
		// }
		// // convert response to string
		// try {
		// BufferedReader reader = new BufferedReader(new InputStreamReader(
		// is, "iso-8859-1"), 8);
		// StringBuilder sb = new StringBuilder();
		// String line = null;
		// while ((line = reader.readLine()) != null) {
		// sb.append(line + "\n");
		// }
		// is.close();
		// result = sb.toString();
		// } catch (Exception e) {
		//
		// System.out.println(e);
		// // Log.e("log_tag", "Error converting result "+e.toString());
		// }

		String result = Utils.getHttpRequest(mActivity,
				Constants.BLOOD_GROUP_STOCK_URL);

		Log.d("SDFSDF", "Resumt " + result);
		String jsonStr = formatStr(result);
		return getJSONEvents(jsonStr);

	}

	private String formatStr(String result) {
		// String startsWith = "(";
		String startsWith = "gdata.io.handleScriptLoaded\\(";
		String endsWith = "\\);";

		// int i = result.indexOf(startsWith);
		// int j = result.lastIndexOf(endsWith);
		// String ret = "";
		// if ((-1 == i) || (-1 == j)) {
		// // do nothing if no proper string
		// } else {
		// ret.substring(i + 1, j);
		// }

		String[] str = result.split(startsWith);
		str = str[1].split("\\);");
		Log.d("SDFSDF", str[0]);
		return str[0];
	}

	private HashMap<String, BloodGroupDetail> getJSONEvents(String result) {
		HashMap<String, BloodGroupDetail> pieList = new HashMap<String, BloodGroupDetail>();
		JSONObject jArray = null;
		try {

			jArray = new JSONObject(result);

			String data = jArray.getString("feed");
			JSONObject jFeed = new JSONObject(data);

			// String entryStr =jFeed.getString("entry");
			JSONArray entryArray = jFeed.getJSONArray("entry");
			HashMap<String, String> stockCollection = new HashMap<String, String>();
			for (int j = 0; j < entryArray.length(); j++) {
				JSONObject entryObj = entryArray.getJSONObject(j);

				String eTitle = entryObj.getString("title");
				JSONObject titleObj = new JSONObject(eTitle);
				String entryDate = titleObj.getString("$t");

				String eContent = entryObj.getString("content");
				JSONObject contentObj = new JSONObject(eContent);
				String contentStr = contentObj.getString("$t");
				Log.d("A:B:", entryDate);
				stockCollection.put(entryDate.trim(), contentStr);
			}// for

			if (stockCollection.containsKey(dateStr)) {
				Log.d("SDfsdf", " sddssdssdsd");
				pieList = getPieDetails(stockCollection.get(dateStr));
			} else {
				Log.d("SDfsdf", "not ddata");
			}

		} catch (JSONException e) {
			// Log.e("log_tag", "Error parsing data "+e.toString());

			System.out.println(e);
		}
		return pieList;
	}

	private HashMap<String, BloodGroupDetail> getPieDetails(String contentStr) {
		HashMap<String, BloodGroupDetail> pieMap = new HashMap<String, BloodGroupDetail>();
		// StringTokenizer st = new StringTokenizer(contentStr, "\\,");
		if (contentStr != null) {
			String[] st = contentStr.split("\\,");

			for (int i = 0; i < st.length; i++) {
				BloodGroupDetail pItem = new BloodGroupDetail();

				// String token = st.nextToken();
				String token = st[i];
				String[] data = token.split("\\:");
				pItem.setLabel(data[0].trim());
				pItem.setCount(Integer.parseInt(data[1].trim()));

				pieMap.put(data[0].trim(), pItem);
			}// for each token
			return pieMap;
		}
		return null;
	}
}
