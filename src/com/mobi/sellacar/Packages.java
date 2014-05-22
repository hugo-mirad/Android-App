package com.mobi.sellacar;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class Packages extends Activity {
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

	ListView seller_package_lv;

	ListView lv;
	String url, addpackage_url;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String seller_carid, session_id, packageid, userpackid;
	String _Description, _Maxcars, Dateofpurchase, vaildtill, postedcars;
	String _Description1, _Maxcars1, Dateofpurchase1, vaildtill1, postedcars1,
			carcount;
	private static final String TAG_ID = "_Description";
	private static final String TAG_Packageid = "_PackageID";
	private static final String TAG_Maxcars = "_MaxCars";
	private static final String TAG_paydate = "_PayDate";
	private static final String TAG_validity = "_ValidityPeriod";
	private static final String TAG_userpackid = "_UserPackID";
	private static final String TAG_carcount = "_CarsCount";
	static String package1, package2;
	Button btn_package_back, btn_addpackage;
	String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.seller_package);

		//	System.out.println("this is package");
			seller_carid = Seller_Login.UID;
			session_id = Seller_Login.SessionID;
			//System.out.println("this is se id" + session_id);
			seller_package_lv = (ListView) findViewById(R.id.list_package);
			btn_package_back = (Button) findViewById(R.id.btn_sellerpackage_back);
			btn_addpackage = (Button) findViewById(R.id.addpackage);
			btn_package_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			btn_addpackage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					btn_addpackage.setEnabled(false);
					// btn_addpackage.setText("ADD PACKAGE");
					String SOAP_ACTION = "http://tempuri.org/AddPackageRequestMobile";
					String METHOD_NAME = "AddPackageRequestMobile ";
					String NAMESPACE = "http://tempuri.org/";
					String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=UploadPictureByCarIDFromAndroid";
					String TAG = "HELLO";

					SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
					request.addProperty("UID", seller_carid);
					request.addProperty("AuthenticationID", Uid);
					request.addProperty("CustomerID", Uno);
					request.addProperty("SessionID", session_id);
					try {
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
								SoapEnvelope.VER11);
						envelope.dotNet = true;

						envelope.setOutputSoapObject(request);

						AndroidHttpTransport aht = new AndroidHttpTransport(URL);

						aht.call(SOAP_ACTION, envelope);

						// SoapObject response = (SoapObject)
						// envelope.getResponse();
						SoapPrimitive response = (SoapPrimitive) envelope
								.getResponse();
						String temp1 = response.toString();
						
						Log.v("TAG", temp1);
						if (temp1.equals("Success")) {
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
									Packages.this);

							// set title
							alertDialogBuilder.setTitle("Thank You");

							// set dialog message
							alertDialogBuilder
									.setMessage(
											"Our service respresentative will contact you shortly")
									.setCancelable(false)
									.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													btn_addpackage
															.setEnabled(true);
												}
											});

							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder
									.create();

							// show it
							alertDialog.show();
						} else if (temp1.equals("Session timed out")) {
							Intent in = new Intent(getApplicationContext(),
									Seller_Login.class);
							startActivity(in);
						} else if (temp1.equals("Failed")) {
							Toast.makeText(getApplicationContext(),
									"Error occured,Please try again",
									Toast.LENGTH_LONG).show();
							btn_addpackage.setEnabled(true);
						}
					} catch (Exception e) {
						e.printStackTrace();

						Toast.makeText(getApplicationContext(),
								"Server Error occured,Please try again",
								Toast.LENGTH_LONG).show();

					}
				}
			});
			ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

			url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetPackageDetailsByUID/"
					+ seller_carid
					+ "/"
					+ Uid
					+ "/"
					+ Uno
					+ "/"
					+ session_id
					+ "";
		//	System.out.println("this is url" + url);
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			JSONArray GetPackageDetailsByUIDResult;
			try {
				GetPackageDetailsByUIDResult = json
						.getJSONArray("GetPackageDetailsByUIDResult");
				for (int i = 0; i < GetPackageDetailsByUIDResult.length(); i++) {
					JSONObject c = GetPackageDetailsByUIDResult
							.getJSONObject(i);
					String res = c.getString("_AASuccess");
					if (res.equals("Success")) {
						_Description = c.getString(TAG_ID);
						packageid = c.getString(TAG_Packageid);
						_Maxcars = c.getString(TAG_Maxcars);
						// package1 = c.getString(TAG_Packageid);
						Dateofpurchase = c.getString(TAG_paydate);
						vaildtill = c.getString(TAG_validity);
						carcount = c.getString(TAG_carcount);
						userpackid = c.getString(TAG_userpackid);
						

						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, _Description);
						map.put(TAG_Packageid, packageid);
						map.put(TAG_Maxcars, _Maxcars);
						map.put(TAG_paydate, Dateofpurchase);
						map.put(TAG_validity, vaildtill);
						map.put(TAG_carcount, carcount);
						map.put(TAG_userpackid, userpackid);
						contactList.add(map);

					} else {
						Intent in = new Intent(getApplicationContext(),
								Seller_Login.class);
						startActivity(in);
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				Toast.makeText(getApplicationContext(),
						"Server Error occured,Please try again",
						Toast.LENGTH_LONG).show();

			}

			ListAdapter adapter = new SimpleAdapter(this, contactList,
					R.layout.list_item, new String[] { TAG_ID, TAG_Packageid,
							TAG_Maxcars, TAG_validity, TAG_carcount,
							TAG_paydate, TAG_userpackid }, new int[] {
							R.id.name, R.id.packageid, R.id.maxcars,
							R.id.validy, R.id.postedcars, R.id.paydate,
							R.id.userpackid });

			// setListAdapter(adapter);
			seller_package_lv.setAdapter(adapter);

			// lv = getListView();
			seller_package_lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					int itemPosition = position;
					//System.out.println("this is item pos" + itemPosition);

					// ListView Clicked item value
					String itemValue = seller_package_lv.getItemAtPosition(
							position).toString();
					//System.out.println("this is item" + itemValue);
					TextView packagename = (TextView) view
							.findViewById(R.id.name);
					String playerChanged = packagename.getText().toString();
					TextView tv_packageid = (TextView) view
							.findViewById(R.id.packageid);
					String packageid = tv_packageid.getText().toString();
					//System.out.println("this is package id" + packageid);
					TextView tv_date = (TextView) view
							.findViewById(R.id.paydate);
					TextView tv_validy = (TextView) view
							.findViewById(R.id.validy);
					TextView tv_maxcars = (TextView) view
							.findViewById(R.id.maxcars);
					TextView tv_carscount = (TextView) view
							.findViewById(R.id.postedcars);
					TextView tv_userpackid = (TextView) view
							.findViewById(R.id.userpackid);
					String date = tv_date.getText().toString();
					// System.out.println("this is package date" + date);
					String validy = tv_validy.getText().toString();
					// System.out.println("this is package validy" + validy);
					String maxcars = tv_maxcars.getText().toString();
					// System.out.println("this is package maxcars" + maxcars);
					String carscount = tv_carscount.getText().toString();
					String packid = tv_userpackid.getText().toString();
					// System.out.println("this is package carscount" + packid);
					Intent in = new Intent(getApplicationContext(),
							Seller_Package_Basic.class);
					in.putExtra("item", itemValue);
					in.putExtra("_Maxcars2", maxcars);
					in.putExtra("packageid", packageid);
					in.putExtra("_Dateofpurchase", date);
					in.putExtra("_vaildtill", validy);
					in.putExtra("_postedcars", carscount);
					in.putExtra("Description", playerChanged);
					in.putExtra("userpackid", packid);

					startActivity(in);

					//System.out.println("this is package name" + playerChanged);

				}
			});
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(Packages.this)
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

}
