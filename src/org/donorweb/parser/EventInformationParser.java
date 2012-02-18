package org.donorweb.parser;

import java.util.ArrayList;

import org.donorweb.model.EventInformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventInformationParser {

	private String mJsonString;

	public EventInformationParser(String responseString) {
		mJsonString = responseString;
	}

	public ArrayList<EventInformation> getJSONEvents() {
		ArrayList<EventInformation> eventList = new ArrayList<EventInformation>();
		JSONObject jArray = null;
		try {
			jArray = new JSONObject(mJsonString);

			// String ver = jArray.getString("apiVersion");
			String data = jArray.getString("data");

			JSONObject jItem = new JSONObject(data);

			JSONArray eventArray = jItem.getJSONArray("items");
			for (int j = 0; j < eventArray.length(); j++) {
				JSONObject eventObj = eventArray.getJSONObject(j);
				eventList.add(getJSONEvent(eventObj));
			}

		} catch (JSONException e) {
			// Log.e("log_tag", "Error parsing data "+e.toString());

			System.out.println(e);
		}
		return eventList;
	}

	public EventInformation getJSONEvent(JSONObject jEventOBJ) {
		EventInformation event = new EventInformation();
		try {
			event.setLocation(jEventOBJ.getString("location"));
			event.setTitle(jEventOBJ.getString("title"));
			event.setDetails(jEventOBJ.getString("details"));

			JSONArray whenArray = jEventOBJ.getJSONArray("when");
			JSONObject jObject = whenArray.getJSONObject(0);

			event.setEventStartTime(jObject.getString("start"));
			event.setEventEndTime(jObject.getString("end"));
		} catch (JSONException e) {
			System.out.println(e);
		}
		return event;

	}

}
