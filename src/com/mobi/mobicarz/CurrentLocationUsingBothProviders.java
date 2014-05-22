package com.mobi.mobicarz;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class CurrentLocationUsingBothProviders {
	Object activity;
	public LocationManager mLocationManager;
	private static final int TEN_SECONDS = 10000;
	private static final int TEN_METERS = 10;
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	Context mContext;
	
	public void setup(Context context,Object act) {
		mContext = context;
		Location gpsLocation = null;
		Location networkLocation = null;
		activity = act;
			
		
		mLocationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
		
		mLocationManager.removeUpdates(listener);

		gpsLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER,
				R.string.not_support_gps);
		networkLocation = requestUpdatesFromProvider(
				LocationManager.NETWORK_PROVIDER, R.string.not_support_network);

		// If both providers return last known locations, compare the two and
		// use the better
		// one to update the UI. If only one provider returns a location, use
		// it.
//		if (flag) {
			if (gpsLocation != null && networkLocation != null) {
				((MainActivity) activity).updateUIlocation(getBetterLocation(gpsLocation,networkLocation));
			} else if (gpsLocation != null) {
				((MainActivity) activity).updateUIlocation(gpsLocation);
			} else if (networkLocation != null) {
				((MainActivity) activity).updateUIlocation(networkLocation);
			}
		}
	
	public Location requestUpdatesFromProvider(final String provider,
			final int errorResId) {
		 Location location = null;
	        if (mLocationManager.isProviderEnabled(provider)) {
	            mLocationManager.requestLocationUpdates(provider, TEN_SECONDS, TEN_METERS, listener);
	            location = mLocationManager.getLastKnownLocation(provider);
	        } else {
	           // Toast.makeText((MainActivity)activity, errorResId, Toast.LENGTH_LONG).show();
	        }
	        return location;
	}
	
	public final LocationListener listener = new LocationListener() {

		public void onLocationChanged(Location location) {
			// A new location update is received. Do something useful with it.
			// Update the UI with
			// the location update.
			
			((MainActivity)activity).updateUIlocation(location);
			/*if(LocationProviderOrDriverInfo)
				((LocationBothProvidersActvity)activity).updateUILocation(location);
			else
				((DriverInformationActivity)activity).updateUILocation(location);*/
				
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};
	
	protected Location getBetterLocation(Location newLocation,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return newLocation;
		}
		return currentBestLocation;
	}
	
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}
