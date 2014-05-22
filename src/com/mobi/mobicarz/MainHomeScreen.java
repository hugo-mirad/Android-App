package com.mobi.mobicarz;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mobi.sellacar.Seller_Login;

public class MainHomeScreen extends Activity {
	public static String CustomerID;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	Button btn_home_findcar, btn_home_login, btn_home_info;
	private int pid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		this.pid = android.os.Process.myPid();
		if (isOnline()) {

			final TelephonyManager tm = (TelephonyManager) getBaseContext()
					.getSystemService(Context.TELEPHONY_SERVICE);

			final String tmDevice, tmSerial, androidId;
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			androidId = ""
					+ android.provider.Settings.Secure.getString(
							getContentResolver(),
							android.provider.Settings.Secure.ANDROID_ID);

			UUID deviceUuid = new UUID(androidId.hashCode(),
					((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
			CustomerID = deviceUuid.toString();
		//	System.out.println("this is device id" + CustomerID);

			setContentView(R.layout.mainhomescreen);

			btn_home_findcar = (Button) findViewById(R.id.btn_home_findacar);
			btn_home_login = (Button) findViewById(R.id.btn_home_login);
			btn_home_info = (Button) findViewById(R.id.btn_home_info);
	

			btn_home_findcar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(in);
				}
			});

			btn_home_login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							Seller_Login.class);
					startActivity(in);
				}
			});
			btn_home_info.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							AboutUs.class);
					startActivity(in);
				}
			});

		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						MainHomeScreen.this).create();
				alertDialog.setTitle("Info");
				alertDialog
						.setMessage("Internet not available, Cross check your internet connectivity and try again");
				alertDialog.setIcon(R.drawable.icon);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
				alertDialog.show();
			} catch (Exception e) {

			}
		}

	}

	public void onBackPressed() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MainHomeScreen.this);

		// set title
		alertDialogBuilder.setTitle("Quit");

		// set dialog message
		alertDialogBuilder
				.setMessage("Are you sure you want to quit!")
				.setCancelable(false)
				.setPositiveButton("Quit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							
								Intent intent = new Intent(Intent.ACTION_MAIN);
								intent.addCategory(Intent.CATEGORY_HOME);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								dialog.cancel();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();

	}
}
