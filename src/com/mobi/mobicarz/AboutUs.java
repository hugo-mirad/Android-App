package com.mobi.mobicarz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class AboutUs extends Activity {
	TextView tv_email;
	TextView tv_heading;
	TextView tv_phonenumber, tv_contactphno, tv_emailaddress;
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

	TextView tv_home_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.about);

			tv_heading = (TextView) findViewById(R.id.textView1_heading);
			tv_email = (TextView) findViewById(R.id.tv_email);
			tv_phonenumber = (TextView) findViewById(R.id.tv_contact);
			tv_contactphno = (TextView) findViewById(R.id.tv_contactphno);
			tv_emailaddress = (TextView) findViewById(R.id.tv_emailaddress);
			tv_heading.setText("About Us");
			tv_home_back = (TextView) findViewById(R.id.tv_about_home);
			if (isSmartphone(AboutUs.this)) {

				tv_home_back.setBackgroundResource(R.drawable.home_small);
			} else {

				tv_home_back.setBackgroundResource(R.drawable.home_big);
			}

			tv_home_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							MainHomeScreen.class);
					startActivity(in);
				}
			});
			tv_contactphno.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					try {
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:8887868307"));
						System.out.println("this is calling");
						startActivity(callIntent);
					} catch (ActivityNotFoundException activityException) {
						Log.e("helloandroid dialing example", "Call failed");
					}

					return false;
				}
			});

			tv_emailaddress.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent emailin = new Intent(getApplicationContext(),
							AboutEmail.class);
					startActivity(emailin);
				}
			});

		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(getParent())
						.create();
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

	public static boolean isSmartphone(Activity act) {
		DisplayMetrics metrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int dpi = 0;
		if (metrics.widthPixels < metrics.heightPixels) {
			dpi = (int) (metrics.widthPixels / metrics.density);
		} else {
			dpi = (int) (metrics.heightPixels / metrics.density);
		}

		if (dpi < TABLET_MIN_DP_WEIGHT) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(getApplicationContext(), MainHomeScreen.class);
		startActivity(in);
	}

}
