package org.donorweb.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.donorweb.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class BloodMobileTrackerActivity extends MapActivity {

	LinearLayout linearLayout;
	MapView mapView;
	// private Road mRoad;
	private String address;// = "3188 Geylang Bahru. Singapore 339717";
	private double latitudeDecTo;
	private double longitudeDecTo;
	// private double latitudeDecFrom = 1.315556;
	// private double longitudeDecFrom = 103.764444;
	LocationManager lm;
	private LocationListener locationListener;
	// MyLocation myLocation = new MyLocation();
	Location mCurrentLocation;

	// LocatePosition mLocatePosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view_layout);
		setTitle("Map Location");
		address = getIntent().getStringExtra("location");

		JSONObject jsonLocatinDetails = getLocationInfo();
		getLatLong(jsonLocatinDetails);
		mapView = (MapView) findViewById(R.id.mapView_element);
		mapView.setBuiltInZoomControls(true);

		// mLocatePosition = new LocatePosition(this, mLocationResult);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(
				drawable, this);
		GeoPoint point = new GeoPoint((int) (latitudeDecTo * 1E6),
				(int) (longitudeDecTo * 1E6));
		OverlayItem overlayitem = new OverlayItem(point, "Location", address);

		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);

		MapController mapController = mapView.getController();

		mapController.animateTo(point);
		mapController.setZoom(11);
	}

	public JSONObject getLocationInfo() {
		StringBuilder stringBuilder = new StringBuilder();
		try {

			this.address = this.address.replaceAll(" ", "%20");
			Log.d("SDFSDF", "address is " + address);

			HttpPost httppost = new HttpPost(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ address + "&sensor=false");
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();

			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
			Log.d("SDFSDF", "ClientProtocol Exception" + e);
		} catch (IOException e) {
			Log.d("SDFSDF", "Io Exception" + e);
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			Log.d("SDFSDF", "JsonException" + e);
		}

		return jsonObject;
	}

	public boolean getLatLong(JSONObject jsonObject) {

		try {

			this.longitudeDecTo = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lng");

			this.latitudeDecTo = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lat");
			Log.d("Location Values", String.format("%s,%s",
					this.longitudeDecTo, this.latitudeDecTo));

		} catch (JSONException e) {
			return false;

		}

		return true;
	}

	public void GetLanAndLongiFromAddress() {
		Geocoder coder = new Geocoder(this);
		List<Address> address;
		try {
			address = coder.getFromLocationName(this.address, 5);
			if (address == null) {
				// Alert msg should be populated
				System.out.println("No address found");
				Log.d("SDFSDF", "No Address Found");
			}
			Address location = address.get(0);
			this.latitudeDecTo = location.getLatitude();
			this.longitudeDecTo = location.getLongitude();
			Log.d("Location Details - ",
					String.format("%s,%s", latitudeDecTo, longitudeDecTo));
		} catch (Exception e) {
			// Alert msg should be populated
			System.out.println("Exception while fetching address");
			Log.d("SDFSDF - Exception", "Exception While fetching" + e);
		}
	}

	private InputStream getConnection(String url) {
		InputStream is = null;
		try {
			URLConnection conn = new URL(url).openConnection();
			is = conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

}
