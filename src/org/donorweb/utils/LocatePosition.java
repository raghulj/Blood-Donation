package org.donorweb.utils;

import java.util.Iterator;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LocatePosition {

	private static final int MIN_SATELLITE_REQUIRED = 5;

	private static final int GPS_WAIT_TIME = 1000;

	LocationResult locationResult;
	private Location bestLocation = null;

	private LocationManager mLocationManager;
	public Context context;

	private boolean isGPSEnabled = false;

	private int mGpsWaitCount = 0;
	private int satelliteCount = 0;

	private String mStringLocationProvider;

	private Location mNetworkLocation;
	private Location mGPSLocation;
	Criteria mFineCriteria;
	Criteria mApproxCriteria;

	Context mContext;

	public LocatePosition(Context context, LocationResult locResult) {
		mContext = context;
		mLocationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locationResult = locResult;
		addListeners();

		mFineCriteria = new Criteria();
		mFineCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		mFineCriteria.setAltitudeRequired(false);
		mFineCriteria.setBearingRequired(false);
		mFineCriteria.setCostAllowed(true);

		mApproxCriteria = new Criteria();
		mApproxCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
		mApproxCriteria.setAltitudeRequired(false);
		mApproxCriteria.setBearingRequired(false);
		mApproxCriteria.setCostAllowed(true);

		mStringLocationProvider = mLocationManager.getBestProvider(
				mFineCriteria, true);

	}

	public boolean isGPSEnabled() {
		if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return false;
		}
		return true;
	}

	public void getLocation() {
		addListeners();
		mHandler.sendEmptyMessageDelayed(0, GPS_WAIT_TIME);
	}

	private void tryGettingBestLocation() {
		if (getLastKnownLocation() == null) {
			System.out.println("last location is null");

			if (mGPSLocation == null) {
				if (mGpsWaitCount < 10) {
					System.out.println("Waiting for GPS " + mGpsWaitCount);

					mHandler.sendEmptyMessageDelayed(0, GPS_WAIT_TIME);
					mGpsWaitCount++;

				} else {

					System.out.println("best location is network ");

					mStringLocationProvider = mLocationManager.getBestProvider(
							mApproxCriteria, true);
					if (mGpsWaitCount <= 10) {
						mHandler.sendEmptyMessageDelayed(0, GPS_WAIT_TIME);

					} else {
						removeListeners();
					}
					bestLocation = mNetworkLocation;

				}
			} else {
				System.out.println("best location is gps");

				bestLocation = mGPSLocation;
				removeListeners();
			}
		} else {
			System.out.println("best location is last known");

			bestLocation = getLastKnownLocation();
			removeListeners();
		}
		locationResult.gotLocation(bestLocation);

	}

	private Location getLastKnownLocation() {
		Log.d("lcoation", "Location provider " + mStringLocationProvider);
		return mLocationManager.getLastKnownLocation(mStringLocationProvider);
	}

	private void addListeners() {
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, mNetworkListener);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				0, 0, mGPSListener);
		mLocationManager.addGpsStatusListener(gpsStatusListener);

	}

	public void removeListeners() {
		if (mLocationManager != null) {
			mLocationManager.removeUpdates(mGPSListener);
			mLocationManager.removeUpdates(mNetworkListener);
			mLocationManager.removeGpsStatusListener(gpsStatusListener);
		}
	}

	/**
	 * Best location abstract result class
	 */
	public static abstract class LocationResult {
		public abstract void gotLocation(Location location);
	}

	/**
	 * GpsStatus listener. OnChainged counts connected satellites count.
	 */
	public final GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
		public void onGpsStatusChanged(int event) {

			if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
				try {
					// Check number of satellites in list to determine fix state
					GpsStatus status = mLocationManager.getGpsStatus(null);
					Iterable<GpsSatellite> satellites = status.getSatellites();

					satelliteCount = 0;

					Iterator<GpsSatellite> satI = satellites.iterator();
					while (satI.hasNext()) {
						GpsSatellite satellite = satI.next();
						System.out.println("Satellite: snr="
								+ satellite.getSnr() + ", elevation="
								+ satellite.getElevation());
						satelliteCount++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					satelliteCount = MIN_SATELLITE_REQUIRED + 1;
				}

				System.out.println("#### sat_count = " + satelliteCount);
			}
		}
	};

	LocationListener mGPSListener = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}

		public void onLocationChanged(Location location) {
			locationResult.gotLocation(location);
		}
	};

	LocationListener mNetworkListener = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}

		public void onLocationChanged(Location location) {
			mNetworkLocation = location;
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			tryGettingBestLocation();
		}
	};

}
