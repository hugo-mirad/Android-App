package com.mobi.sellacar;

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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.mobi.mobicarz.AboutEmail;
import com.mobi.mobicarz.R;

public class Customer_Support extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	TextView tv_email;
	TextView tv_phonenumber, tv_contactphno, tv_emailaddress;
	TextView tv_heading, tv_home_back;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			//System.out.println("this is customer");
			setContentView(R.layout.customersupport);

			tv_heading = (TextView) findViewById(R.id.textView1_heading);
			tv_heading.setText("Contact Us");
			tv_email = (TextView) findViewById(R.id.tv_email);
			tv_home_back = (TextView) findViewById(R.id.tv_about_home);
			tv_phonenumber = (TextView) findViewById(R.id.tv_contact);
			tv_contactphno = (TextView) findViewById(R.id.tv_contactphno);
			tv_emailaddress = (TextView) findViewById(R.id.tv_emailaddress);

			tv_home_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) { // TODO Auto-generated
					Intent in = new Intent(getApplicationContext(),
							Sell_Home_More.class);
					startActivity(in);
				}
			});

			tv_contactphno.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					try {
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:8884656693"));
						//System.out.println("this is calling");
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

				AlertDialog alertDialog = new AlertDialog.Builder(
						Customer_Support.this).create();
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

}
