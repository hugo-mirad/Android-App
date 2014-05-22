package com.mobi.sellacar;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class Sell_Home extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	Button btn_sellhome_logout, btn_sellhome_more;
	String seller_carid, url, session_id, url1;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	JSONArray contacts = null, contacts1 = null, logout_contacts = null;
	String res;
	public static String reg_CarIDs;
	String sellyearofmake, sellmodel, sellmake, sellmillage, sellprice,
			sellpicloc, sellpic, sellpicurl, sellcarid;
	String imageurl;
	ListView sellhome_lv;
	public static final String TAG_MAKE = "_make";
	public static final String TAG_YEAR = "_yearOfMake";
	public static final String TAG_model = "_model";
	public static final String TAG_Mileage = "_mileage";
	public static final String TAG_PRICE = "_price";
	public static final String TAG_picloc = "_PICLOC0";
	public static final String TAG_pic = "_PIC0";
	public static final String TAG_IMAGE = null;
	public static final String TAG_CARID = "_carid";
	ImageView imageview;
	ListViewAdapter adapter;

	public static String reg_Address, reg_AltEmail, reg_AltPhone,
			reg_BusinessName, reg_City, reg_Name, reg_PhoneNumber,
			reg_StateCode, reg_StateID, reg_UId, reg_UserID, reg_UserName,
			reg_Zip, reg_state;


	ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	ProgressDialog pDialog;
	static String Carid;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.sell_home);

			seller_carid = Seller_Login.UID;
			session_id = Seller_Login.SessionID;
			//System.out.println("this is sell home");
			btn_sellhome_logout = (Button) findViewById(R.id.btn_logout);
			btn_sellhome_more = (Button) findViewById(R.id.btn_sellhome_more);
			sellhome_lv = (ListView) findViewById(R.id.sellhomedisplay_lv);

			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Please wait..");
			pDialog.setIndeterminate(true);
			pDialog.setCancelable(false);
			pDialog.show();
			new MyAsyncTask().execute(url);

			sellhome_lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					Intent in = new Intent(getApplicationContext(),
							SellCarDetailView.class);
					
					TextView tv_carid = (TextView) view
							.findViewById(R.id.tv_selldisplay_carid);
					String playerChanged = tv_carid.getText().toString();
					Bundle b = new Bundle();
					b.putString("product", playerChanged);
					in.putExtras(b);

					startActivity(in);
				}
			});
			btn_sellhome_more.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							Sell_Home_More.class);
					startActivity(in);
				}
			});
			btn_sellhome_logout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					String logout_url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/PerformLogoutMobile/"
							+ seller_carid
							+ "/"
							+ session_id
							+ "/"
							+ Uid
							+ "/"
							+ Uno + "";

					//System.out.println("this is logout" + logout_url);
					JSONParser jParser = new JSONParser();

					JSONObject logout_json = jParser.getJSONFromUrl(logout_url);
			
					try {
						res = logout_json
								.getString("PerformLogoutMobileResult");
						//System.out.println("this is logout res" + res);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (res.equals("Success")) {
						Intent in = new Intent(getApplicationContext(),
								Seller_Login.class);
						startActivity(in);
					} else if (res.equals("Failure")) {
						Toast.makeText(getApplicationContext(),
								"Could not connect to server",
								Toast.LENGTH_LONG).show();
					}
				}
			});

		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						Sell_Home.this).create();
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

	class MyAsyncTask extends
			AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... arg0) {

			// TODO Auto-generated method stub
			url1 = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/FindMobileCarsByUID/"
					+ seller_carid + "/" + Uid + "/" + Uno;

			// url1="http://test1.unitedcarexchange.com/carservice/ServiceMobile.svc/FindMobileCarsByUID/2218/ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654/12345/";
		//	System.out.println("this is url1");
			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.getJSONFromUrl(url1);
		//	System.out.println("this is json obj" + json);
			try {
				// Getting Array of Contacts
				contacts1 = json.getJSONArray("FindMobileCarsByUIDResult");
				for (int i = 0; i < contacts1.length(); i++) {
					JSONObject c = contacts1.getJSONObject(i);
					reg_CarIDs = c.getString("_carid");
					String caruid = c.getString("_CarUniqueID");
					sellmake = c.getString(TAG_MAKE);
					sellmillage = c.getString(TAG_Mileage);
					sellmodel = c.getString(TAG_model);
					sellprice = c.getString(TAG_PRICE);
					sellyearofmake = c.getString(TAG_YEAR);
					sellpic = c.getString(TAG_pic);
					sellpicloc = c.getString(TAG_picloc);
					sellcarid = c.getString(TAG_CARID);

					// sellpicurl=c.getString(TAG_IMAGE);
				/*	System.out.println("this is carid" + caruid);
					System.out.println("this is make" + sellmake);
					System.out.println("this is model" + sellmodel);
					System.out.println("this is year" + sellyearofmake);
					System.out.println("this is price" + sellprice);
					System.out.println("this is pic" + sellpic);
					System.out.println("this is pic loc" + sellpicloc);*/

					if (sellpicloc.equalsIgnoreCase("Emp")
							|| sellpicloc.equalsIgnoreCase(null)) {
						imageurl = "http://www.unitedcarexchange.com/"
								+ sellpic.replaceAll(" ", "%20");
					} else {
						imageurl = "http://www.unitedcarexchange.com/"
								+ sellpicloc + sellpic.replaceAll(" ", "%20");
					}
				//	System.out.println("this is image url" + imageurl);

					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_MAKE, sellmake);
					map.put(TAG_Mileage, sellmillage);
					map.put(TAG_model, sellmodel);
					map.put(TAG_PRICE, sellprice);
					map.put(TAG_YEAR, sellyearofmake);
					map.put(TAG_IMAGE, imageurl);
					map.put(TAG_CARID, sellcarid);

					contactList.add(map);

				}
			} catch (JSONException e) {
				e.printStackTrace();
				pDialog.cancel();
				Sell_Home.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Server Error occured,Please try again",
								Toast.LENGTH_LONG).show();
					}
				});
			}
			return null;
		}

		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {

			pDialog.dismiss();

			adapter = new ListViewAdapter(getApplicationContext(),
					Sell_Home.this, contactList);

			sellhome_lv.setAdapter(adapter);
		}

	}

}
