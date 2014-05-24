package com.mobi.mobicarz;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.Toast;

import com.mobi.sellacar.Seller_Login;

public class MainHomeScreen extends Activity {
	public static String CustomerID;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	
	public static JSONArray MakeListObj;
	ArrayList<MakeObjects> modelList;
	// = new ArrayList<MakeObjects>();
	String[] MakeList_array;

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
			
		//	loaddata();
				
				
				String uno =  MainHomeScreen.CustomerID; 
				String url1 = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetMakeCounts/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654"
						+ "/" + uno;
				System.out.println("this is home url"+url1);
				JSONParser jParser = new JSONParser();
				JSONObject json = jParser.getJSONFromUrl(url1);
			//	System.out.println("this is url" + url1);
				try {
					MakeListObj = json.getJSONArray("GetMakeCountsResult");
					int k = MakeListObj.length();
					System.out.println("this is json array length" + k+MakeListObj);
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							"Server Error occured,Please try again", Toast.LENGTH_LONG)
							.show();
				}
				catch (NullPointerException e) {
					
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
				}
	

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
	/*private void loaddata() {
		// TODO Auto-generated method stub
		String url = "http://unitedcarexchange.com/CarService/Service.svc/GetMakes";
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(url);
		//System.out.println("this is url" + url);
		try {
			MakeListObj = json.getJSONArray("GetMakesResult");
			MakeList_array = new String[MakeListObj.length()];

			MakeObjects modelCarObjects = null;

			for (int i = 0; i < MakeListObj.length(); i++) {

				modelCarObjects = new MakeObjects();
				// add to country names array
				modelCarObjects.setModelName(MakeListObj.getJSONObject(i)
						.getString("_make"));
				modelCarObjects.setModelId(MakeListObj.getJSONObject(i)
						.getString("_makeID"));
				modelList.add(modelCarObjects);

				MakeList_array[i] = modelCarObjects.getModelName();
				int k = MakeList_array.length;
			//	System.out.println("this is length of make" + k);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
		}
	}*/
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
