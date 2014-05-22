package com.mobi.mobicarz;

import android.content.Context;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

class PhoneCallListener extends PhoneStateListener {

	Context con;

	public PhoneCallListener(Context context) {
		con = context;
	}

	private boolean isPhoneCalling = false;

	String LOG_TAG = "LOGGING 123";

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {

		if (TelephonyManager.CALL_STATE_RINGING == state) {
			// phone ringing
			Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
		}

		if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
			// active
			Log.i(LOG_TAG, "OFFHOOK");

			isPhoneCalling = true;
		}

		if (TelephonyManager.CALL_STATE_IDLE == state) {
			// run when class initial and phone call ended, need detect flag
			// from CALL_STATE_OFFHOOK
			Log.i(LOG_TAG, "IDLE");

			if (isPhoneCalling) {

				Log.i(LOG_TAG, "restart app");

				isPhoneCalling = false;
			}

		}
	}
}
