package com.mobi.sellacar;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class MultisiteListing extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	String carid, uid, sessionid, url;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	JSONArray contacts = null;
	public static String AASuccess, multisitename, multisitesurl, posteddate,
			validdays;
	public static final String TAG_AASuccess = "AASuccess";
	public static final String TAG_multisitename = "MultiSiteName";
	public static final String TAG_multisitesurl = "MultisitesURL";
	public static final String TAG_posteddate = "PostedDate";
	public static final String TAG_validdays = "ValidDays";
	ArrayList<HashMap<String, String>> mulitsitelist = new ArrayList<HashMap<String, String>>();
	ListView multisite_lv;
	MultisiteListViewAdapter adapter1;
	Button btn_multisitelist_back;
	TextView tv_multisite_heading;
	String make, model, year;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.multisitelist);
			if (isSmartphone(MultisiteListing.this)) {
				//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				//Toast.makeText(this, "Smartphone", Toast.LENGTH_SHORT).show();
			} else {
				//Toast.makeText(this, "Tablet", Toast.LENGTH_SHORT).show();
			}
			tv_multisite_heading = (TextView) findViewById(R.id.tv_multisiteheading);
			carid = SellCarDetailView.sellcardetails_carid;
			uid = SellCarDetailView.sellcardetails_uid;
			sessionid = Seller_Login.SessionID;
			multisite_lv = (ListView) findViewById(R.id.listView1);
			btn_multisitelist_back = (Button) findViewById(R.id.btn_multisitelist_back);

			make = SellCarDetailView.sellcardetails_make;
			model = SellCarDetailView.sellcardetails_model;
			year = SellCarDetailView.sellcardetails_year;

			tv_multisite_heading.setText(year + " " + make + " " + model);

			btn_multisitelist_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetMultisiteListingsByCarID/"
					+ carid
					+ "/"
					+ Uno
					+ "/"
					+ Uid
					+ "/"
					+ sessionid
					+ "/"
					+ uid + "/";
		//	System.out.println("this is url" + url);
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			try {
				contacts = json
						.getJSONArray("GetMultisiteListingsByCarIDResult");
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					AASuccess = c.getString(TAG_AASuccess);
					// sellpic = c.getString(TAG_pic);
					if (AASuccess.equals("Session timed out")) {
						Intent in = new Intent(getApplicationContext(),
								Seller_Login.class);
						startActivity(in);
					}else if(AASuccess.equals("Failure")){
						Toast.makeText(getApplicationContext(), "They are no ads for this car",Toast.LENGTH_LONG).show();
						finish();
					}
					else {
						/* AASuccess=c.getString(TAG_AASuccess); */
						multisitename = c.getString(TAG_multisitename);
						multisitesurl = c.getString(TAG_multisitesurl);
						posteddate = c.getString(TAG_posteddate);
						validdays = c.getString(TAG_validdays);

						/*System.out.println("this is multi success" + AASuccess);
						System.out.println("this is multi site name"
								+ multisitename);
						System.out.println("this is multi site url"
								+ multisitesurl);
						System.out.println("this is multi post" + posteddate);
						System.out.println("this is multi valid" + validdays);*/

						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_AASuccess, AASuccess);
						map.put(TAG_multisitename, multisitename);
						map.put(TAG_multisitesurl, multisitesurl);
						map.put(TAG_posteddate, posteddate);
						map.put(TAG_validdays, validdays);
						mulitsitelist.add(map);
						adapter1 = new MultisiteListViewAdapter(
								getApplicationContext(), MultisiteListing.this,
								mulitsitelist);

						multisite_lv.setAdapter(adapter1);
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
				//pDialog.cancel();
				
						Toast.makeText(getApplicationContext(),
								"Server Error occured,Please try again",
								Toast.LENGTH_LONG).show();
				
			}
			/*
			 * adapter1 = new MultisiteListViewAdapter(getApplicationContext(),
			 * MultisiteListing.this, mulitsitelist);
			 * 
			 * multisite_lv.setAdapter(adapter1);
			 */

			multisite_lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							MultisiteListingWebView.class);
					// in.putExtra(TAG_CARID, sellcarid);
					// in.putExtra("product", product);
					TextView tv_carid = (TextView) view
							.findViewById(R.id.textView5_multireport);
					String playerChanged = tv_carid.getText().toString();
					//System.out.println("this is url of list" + playerChanged);
					Bundle b = new Bundle();
					b.putString("product", playerChanged);
					// in.putExtra("product", playerChanged);
					in.putExtras(b);
					Toast.makeText(MultisiteListing.this, playerChanged,
							Toast.LENGTH_SHORT).show();
					startActivity(in);
				}
			});
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						MultisiteListing.this).create();
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
}
