package com.mobi.sellacar;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

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
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class SellerInformation extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	ProgressDialog pDialog;
	private KeyListener originalKeyListener;
	TextKeyListener originalKeyListener1;
	String make, model, year, bodystyleid, city, zip, phonenumber, email,
			statename, StateID, sellerid, sellername, sp_statename;
	TextView tv_sellinfo_make, tv_sellinfo_model, tv_sellinfo_year;
	EditText et_city, et_zip, et_phonenumber, et_email;
	Spinner sp_sellerinfo_state;
	Button btn_sellerinfo_save, btn_sellerinfo_edit, btn_sellerinfo_back,
			btn_sellerinfo_cancel;
	String[] stateitems = { "UN", "AK", "AL", "AR", "AS", "AZ", "CA", "CO",
			"CT", "DC", "DE", "FL", "FM", "GA", "GU", "HI", "IA", "ID", "IL",
			"IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO",
			"MP", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH",
			"OK", "ON", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX",
			"UT", "VA", "VI", "VT", "WA", "WV", "WY", "nil" };
	String car_id, url, url1;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String UID, SessionID, city1, zip1, email1, phonenumber1;

	int prv_state;
	String zipresult;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.sellerinformation);

			make = SellCarDetailView.sellcardetails_make;
			model = SellCarDetailView.sellcardetails_model;
			year = SellCarDetailView.sellcardetails_year;
			city = SellCarDetailView.sellcarderails_city.replace("%20", " ");

			zip = SellCarDetailView.sellcarderails_zip;
			phonenumber = SellCarDetailView.sellcardetails_phonenumber;
			email = SellCarDetailView.sellcardetails_email;
			statename = SellCarDetailView.sellcardetails_StateID;
		//	System.out.println("this is state name" + statename);
			SessionID = Seller_Login.SessionID;
			UID = SellCarDetailView.sellcardetails_uid;
			sellerid = SellCarDetailView.sellcardetails_sellerID.trim();
			sellername = SellCarDetailView.sellcardetails_sellerName.trim()
					.replaceAll(" ", "%20");
			car_id = SellCarDetailView.sellercardetail_carid;

			tv_sellinfo_make = (TextView) findViewById(R.id.tv_sellerinformation_make);
			tv_sellinfo_model = (TextView) findViewById(R.id.tv_sellerinformation_model);
			tv_sellinfo_year = (TextView) findViewById(R.id.tv_sellerinformation_year);
			et_city = (EditText) findViewById(R.id.et_sellinfo_city);

			originalKeyListener1 = (TextKeyListener) et_city.getKeyListener();

			et_city.setKeyListener(null);

			et_zip = (EditText) findViewById(R.id.et_sellinfo_zip);

			originalKeyListener = et_zip.getKeyListener();
			et_zip.setKeyListener(null);

			et_phonenumber = (EditText) findViewById(R.id.et_sellinfo_phonenumber);

			originalKeyListener = et_phonenumber.getKeyListener();
			et_phonenumber.setKeyListener(null);

			sp_sellerinfo_state = (Spinner) findViewById(R.id.sp_sellinfo_state);
			btn_sellerinfo_save = (Button) findViewById(R.id.btn_sellerinformation_save);
			btn_sellerinfo_edit = (Button) findViewById(R.id.btn_sellerinformation_edit);
			btn_sellerinfo_back = (Button) findViewById(R.id.btn_sellerinformation_back);
			btn_sellerinfo_cancel = (Button) findViewById(R.id.btn_sellerinformation_cancel);
			tv_sellinfo_make.setText(make);
			tv_sellinfo_model.setText(model);
			tv_sellinfo_year.setText(year);
			// et_city.setText(city);
			if (zip.equals("Emp")) {
				et_zip.setText("");
			} else {
				et_zip.setText(zip);
			}
			if (city.equals("Emp")) {
				et_city.setText("");
			} else {
				et_city.setText(city);
			}

			et_phonenumber.setText(phonenumber);
			/*System.out.println("this is city" + city);
			System.out.println("this is city" + zip);
			System.out.println("this is city" + email);
			System.out.println("this is city" + phonenumber);*/

			ArrayAdapter aa = new ArrayAdapter(this,
					android.R.layout.simple_spinner_item, stateitems);

			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_sellerinfo_state.setAdapter(aa);

			List<String> programmingList3 = Arrays.asList(stateitems);
			boolean result = programmingList3.contains(statename);

			/*System.out.println("Does programming Array contains Java? "
					+ result);*/

			prv_state = programmingList3.indexOf(statename);
		//	System.out.println("this is index" + prv_state);
			String s = String.valueOf(prv_state);
		//	System.out.println("this is indexs" + s);

			sp_sellerinfo_state.setSelection(prv_state);
			sp_sellerinfo_state
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							statename = (String) sp_sellerinfo_state
									.getItemAtPosition(sp_sellerinfo_state
											.getSelectedItemPosition());
							sp_statename = (String) sp_sellerinfo_state
									.getItemAtPosition(sp_sellerinfo_state
											.getSelectedItemPosition());
						/*	System.out
									.println("this sp state name" + statename);*/
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

			btn_sellerinfo_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					btn_sellerinfo_edit.setVisibility(View.INVISIBLE);
					btn_sellerinfo_save.setVisibility(View.VISIBLE);
					btn_sellerinfo_back.setVisibility(View.INVISIBLE);
					btn_sellerinfo_cancel.setVisibility(View.VISIBLE);

					et_city.setKeyListener(originalKeyListener1);
					et_city.requestFocus();
					et_city.setFocusable(true);
					et_city.setFocusableInTouchMode(true);

					et_zip.setKeyListener(originalKeyListener);
					et_zip.requestFocus();
					et_zip.setFocusable(true);
					et_zip.setFocusableInTouchMode(true);

					et_phonenumber.setKeyListener(originalKeyListener);
					et_phonenumber.requestFocus();
					et_phonenumber.setFocusable(true);
					et_phonenumber.setFocusableInTouchMode(true);

					sp_sellerinfo_state.setClickable(true);

				}
			});
			btn_sellerinfo_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					city1 = et_city.getText().toString().replace(" ", "%20")
							.trim();
					if (city1.equals("")) {
						city1 = "Emp";
					}
					zip1 = et_zip.getText().toString().replace(" ", "%20")
							.trim();

					phonenumber1 = et_phonenumber.getText().toString()
							.replace(" ", "%20").trim();
					String state = /* "UN"; */SellCarDetailView.sellcardetails_StateID;
				//	System.out.println("this is state in on click" + state);
					if (city1.equals(city) && zip1.equals(zip)
							&& phonenumber1.equals(phonenumber)
							&& sp_statename.equals(state)) {
					//	System.out.println("this is seller info condition");
						Toast.makeText(getApplicationContext(),
								"No changes to save", Toast.LENGTH_SHORT)
								.show();
					} else if(phonenumber1.length()<10){
						Toast.makeText(getApplicationContext(), "Please enter valid Phone number", Toast.LENGTH_LONG).show();
					}

					else {
					//	System.out.println("this is checking zip code");
						final String SOAP_ACTION = "http://tempuri.org/CheckZips";

						final String METHOD_NAME = "CheckZips";
						final String NAMESPACE = "http://tempuri.org/";

						final String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=CheckZips";
						final String TAG = "HELLO";

						SoapObject request = new SoapObject(NAMESPACE,
								METHOD_NAME);
						request.addProperty("zipId", zip1);
						request.addProperty("AuthenticationID", Uid);
						request.addProperty("CustomerID", Uno);
						SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
								SoapEnvelope.VER11);
						soapEnvelope.dotNet = true;
						soapEnvelope.setOutputSoapObject(request);
						HttpTransportSE aht = new HttpTransportSE(URL);
						try {
							Log.v(TAG, "this is login test 24");
							aht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
							aht.debug = true;
							aht.call(SOAP_ACTION, soapEnvelope);
							SoapPrimitive response = (SoapPrimitive) soapEnvelope
									.getResponse();

							zipresult = response.toString();
							/*System.out.println("this is boolean value"
									+ zipresult);*/
							if (zipresult.equals("true")) {
								pDialog = new ProgressDialog(
										SellerInformation.this);
								pDialog.setMessage("Please wait..");
								pDialog.setIndeterminate(true);
								pDialog.setCancelable(false);
								pDialog.show();

								MyTask myTask = new MyTask();
								myTask.execute();
								// sellinfo_senddata();
							} else {
								Toast.makeText(getApplicationContext(),
										"Invalid zip code", Toast.LENGTH_SHORT)
										.show();
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (XmlPullParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/* sellinfo_senddata(); */
					}
				}
			});
			btn_sellerinfo_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			btn_sellerinfo_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_sellerinfo_back.setVisibility(View.VISIBLE);
					btn_sellerinfo_cancel.setVisibility(View.INVISIBLE);
					btn_sellerinfo_edit.setVisibility(View.VISIBLE);
					btn_sellerinfo_save.setVisibility(View.INVISIBLE);

					sp_sellerinfo_state.setClickable(false);

					sp_sellerinfo_state.setSelection(prv_state);
					/*System.out.println("this is previous state name"
							+ prv_state);*/

					originalKeyListener = et_city.getKeyListener();
					et_city.setKeyListener(null);
					et_city.setFocusable(false);
					originalKeyListener = et_phonenumber.getKeyListener();
					et_phonenumber.setKeyListener(null);
					et_phonenumber.setFocusable(false);

					originalKeyListener = et_zip.getKeyListener();
					et_zip.setKeyListener(null);
					et_zip.setFocusable(false);
					
					if (city.equals("Emp")) {
						et_city.setText("");
					} else {
						et_city.setText(city);
					}
					if (zip.equals("Emp")) {
						et_zip.setText("");
					} else {
						et_zip.setText(zip);
					}
				}
			});
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						SellerInformation.this).create();
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

	public class MyTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub

			if (statename.equalsIgnoreCase("UN")) {
				StateID = "0";
			} else if (statename.equalsIgnoreCase("AK")) {
				StateID = "2";
			} else if (statename.equalsIgnoreCase("AL")) {
				StateID = "1";
			} else if (statename.equalsIgnoreCase("AR")) {
				StateID = "5";
			} else if (statename.equalsIgnoreCase("AS")) {
				StateID = "3";
			} else if (statename.equalsIgnoreCase("AZ")) {
				StateID = "4";
			} else if (statename.equalsIgnoreCase("CA")) {
				StateID = "6";
			} else if (statename.equalsIgnoreCase("CO")) {
				StateID = "7";
			} else if (statename.equalsIgnoreCase("CT")) {
				StateID = "8";
			} else if (statename.equalsIgnoreCase("DC")) {
				StateID = "10";
			} else if (statename.equalsIgnoreCase("DE")) {
				StateID = "9";
			} else if (statename.equalsIgnoreCase("FL")) {
				StateID = "12";
			} else if (statename.equalsIgnoreCase("FM")) {
				StateID = "11";
			} else if (statename.equalsIgnoreCase("GA")) {
				StateID = "13";
			} else if (statename.equalsIgnoreCase("GU")) {
				StateID = "14";
			} else if (statename.equalsIgnoreCase("HI")) {
				StateID = "15";
			} else if (statename.equalsIgnoreCase("IA")) {
				StateID = "18";
			} else if (statename.equalsIgnoreCase("ID")) {
				StateID = "59";
			} else if (statename.equalsIgnoreCase("IL")) {
				StateID = "16";
			} else if (statename.equalsIgnoreCase("IN")) {
				StateID = "17";
			} else if (statename.equalsIgnoreCase("KS")) {
				StateID = "19";
			} else if (statename.equalsIgnoreCase("KY")) {
				StateID = "20";
			} else if (statename.equalsIgnoreCase("LA")) {
				StateID = "21";
			} else if (statename.equalsIgnoreCase("MA")) {
				StateID = "25";
			} else if (statename.equalsIgnoreCase("MD")) {
				StateID = "24";
			} else if (statename.equalsIgnoreCase("ME")) {
				StateID = "22";
			} else if (statename.equalsIgnoreCase("MH")) {
				StateID = "23";
			} else if (statename.equalsIgnoreCase("MI")) {
				StateID = "26";
			}

			else if (statename.equalsIgnoreCase("MN")) {
				StateID = "27";
			} else if (statename.equalsIgnoreCase("MO")) {
				StateID = "29";
			} else if (statename.equalsIgnoreCase("MP")) {
				StateID = "39";
			} else if (statename.equalsIgnoreCase("MS")) {
				StateID = "28";
			} else if (statename.equalsIgnoreCase("MT")) {
				StateID = "30";
			} else if (statename.equalsIgnoreCase("NC")) {
				StateID = "37";
			} else if (statename.equalsIgnoreCase("ND")) {
				StateID = "38";
			} else if (statename.equalsIgnoreCase("NE")) {
				StateID = "31";
			} else if (statename.equalsIgnoreCase("NH")) {
				StateID = "33";
			} else if (statename.equalsIgnoreCase("NJ")) {
				StateID = "34";
			} else if (statename.equalsIgnoreCase("NN")) {
				StateID = "35";
			} else if (statename.equalsIgnoreCase("NV")) {
				StateID = "32";
			} else if (statename.equalsIgnoreCase("NY")) {
				StateID = "36";
			} else if (statename.equalsIgnoreCase("OH")) {
				StateID = "40";
			} else if (statename.equalsIgnoreCase("OK")) {
				StateID = "41";
			} else if (statename.equalsIgnoreCase("ON")) {
				StateID = "60";
			} else if (statename.equalsIgnoreCase("OR")) {
				StateID = "42";
			} else if (statename.equalsIgnoreCase("PA")) {
				StateID = "44";
			} else if (statename.equalsIgnoreCase("PR")) {
				StateID = "45";
			} else if (statename.equalsIgnoreCase("PW")) {
				StateID = "43";
			} else if (statename.equalsIgnoreCase("RI")) {
				StateID = "46";
			} else if (statename.equalsIgnoreCase("SC")) {
				StateID = "47";
			} else if (statename.equalsIgnoreCase("SD")) {
				StateID = "48";
			} else if (statename.equalsIgnoreCase("TN")) {
				StateID = "49";
			} else if (statename.equalsIgnoreCase("TX")) {
				StateID = "50";
			} else if (statename.equalsIgnoreCase("UT")) {
				StateID = "51";
			} else if (statename.equalsIgnoreCase("VA")) {
				StateID = "54";
			} else if (statename.equalsIgnoreCase("VI")) {
				StateID = "53";
			} else if (statename.equalsIgnoreCase("VT")) {
				StateID = "52";
			} else if (statename.equalsIgnoreCase("WA")) {
				StateID = "55";
			} else if (statename.equalsIgnoreCase("WI")) {
				StateID = "57";
			} else if (statename.equalsIgnoreCase("WV")) {
				StateID = "56";
			} else if (statename.equalsIgnoreCase("WY")) {
				StateID = "58";
			}

			if (SessionID.equals("Session timed out")) {
				//System.out.println("this is session" + SessionID);
				Intent in = new Intent(getApplicationContext(),
						Seller_Login.class);
				startActivity(in);
			} else {
				final String SOAP_ACTION = "http://tempuri.org/UpdateSellerInformation";

				final String METHOD_NAME = "UpdateSellerInformation";
				final String NAMESPACE = "http://tempuri.org/";

				final String URL = "http://www.unitedcarexchange.com/Mobileservice/Carservice.asmx?op=UpdateSellerInformation";
				final String TAG = "HELLO";

				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("sellerID", sellerid);
				request.addProperty("sellerName", sellername);
				request.addProperty("city", city1);
				request.addProperty("state", statename);
				request.addProperty("zip", zip1);
				request.addProperty("phone", phonenumber1);
				request.addProperty("email", email);
				request.addProperty("carID", car_id);
				request.addProperty("UID", UID);
				request.addProperty("AuthenticationID", Uid);
				request.addProperty("CustomerID", Uno);
				request.addProperty("SessionID", SessionID);
				SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				soapEnvelope.dotNet = true;
				soapEnvelope.setOutputSoapObject(request);
				HttpTransportSE aht = new HttpTransportSE(URL);
				try {
					Log.v(TAG, "this is login test 24");
					aht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					aht.debug = true;
					aht.call(SOAP_ACTION, soapEnvelope);
					SoapPrimitive res = (SoapPrimitive) soapEnvelope
							.getResponse();
				//	System.out.println("this is seller info result" + res);
					String seller_res = res.toString();
					// zipresult = res.toString();
					if (seller_res.equals("Success")) {
						SellerInformation.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(
										getApplicationContext(),
										"Thank You, Your modifications are saved",
										Toast.LENGTH_SHORT).show();
								finish();
							}
						});
					} else if (seller_res.equals("Session timed out")) {
						SellerInformation.this.runOnUiThread(new Runnable() {
							public void run() {
								Intent in = new Intent(getApplicationContext(),
										Seller_Login.class);
								startActivity(in);
							}
						});
					} else if (seller_res.equals("Failed")) {
						SellerInformation.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(),
										"Error occured,Please try again",
										Toast.LENGTH_LONG).show();
							}
						});
					}

				} catch (Exception e) {
					e.printStackTrace();
					pDialog.cancel();
					SellerInformation.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Server Error occured,Please try again",
									Toast.LENGTH_LONG).show();
						}
					});

				}

			}
			return null;
		}

	}

}
