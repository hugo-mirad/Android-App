package com.mobi.sellacar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class VehicleDescription extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	private KeyListener originalKeyListener;
	Button btn_vehicledescription_back, btn_vehicledescription_save,
			btn_vehicledescription_edit, btn_vehicledescription_cancel;
	EditText et_vehicledescription_tile, et_vehicledescripion_des;
	TextView tv_make, tv_model, tv_year;
	String make, year, model, vehicledescription_title, vehicledescription_des,
			statename, StateID;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String title, description;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.vehicledescription);

			make = SellCarDetailView.sellcardetails_make;
			model = SellCarDetailView.sellcardetails_model;
			year = SellCarDetailView.sellcardetails_year;
			statename = "UN";// SellCarDetailView.sellcardetails_StateID;

			btn_vehicledescription_back = (Button) findViewById(R.id.btn_vehicledescription_back);
			btn_vehicledescription_edit = (Button) findViewById(R.id.btn_vehicledescription_edit);
			btn_vehicledescription_save = (Button) findViewById(R.id.btn_vehicledescription_save);
			btn_vehicledescription_cancel = (Button) findViewById(R.id.btn_vehicledescription_cancel);
			tv_make = (TextView) findViewById(R.id.tv_vehicledescription_make);
			tv_model = (TextView) findViewById(R.id.tv_vehicledescription_model);
			tv_year = (TextView) findViewById(R.id.tv_vehicledescription_year);
			et_vehicledescripion_des = (EditText) findViewById(R.id.et_vehicledescription_des);
			et_vehicledescription_tile = (EditText) findViewById(R.id.et_vehicledescription_title);
			originalKeyListener = et_vehicledescripion_des.getKeyListener();
			et_vehicledescripion_des.setKeyListener(null);
			originalKeyListener = et_vehicledescription_tile.getKeyListener();
			et_vehicledescription_tile.setKeyListener(null);

			tv_make.setText(make);
			tv_model.setText(model);
			tv_year.setText(year);
			title = SellCarDetailView.sellcardetails_title;
			description = SellCarDetailView.sellcardetails_description;
			if (description.equals("Emp")) {
				et_vehicledescripion_des.setText("");

			} else {
				et_vehicledescripion_des.setText(description);
			}
			if (title.equals("Emp")) {
				et_vehicledescription_tile.setText("");
			} else {

				et_vehicledescription_tile.setText(title);
			}

			btn_vehicledescription_back
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			btn_vehicledescription_edit
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							btn_vehicledescription_edit
									.setVisibility(View.INVISIBLE);
							btn_vehicledescription_save
									.setVisibility(View.VISIBLE);
							btn_vehicledescription_back
									.setVisibility(View.INVISIBLE);
							btn_vehicledescription_cancel
									.setVisibility(View.VISIBLE);

							et_vehicledescription_tile
									.setKeyListener(originalKeyListener);
							et_vehicledescription_tile.requestFocus();
							et_vehicledescription_tile.setFocusable(true);
							et_vehicledescription_tile
									.setFocusableInTouchMode(true);
							et_vehicledescripion_des
									.setKeyListener(originalKeyListener);
							et_vehicledescripion_des.requestFocus();
							et_vehicledescripion_des.setFocusable(true);
							et_vehicledescripion_des
									.setFocusableInTouchMode(true);
						}
					});
			btn_vehicledescription_cancel
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							btn_vehicledescription_back
									.setVisibility(View.VISIBLE);
							btn_vehicledescription_cancel
									.setVisibility(View.INVISIBLE);
							btn_vehicledescription_edit
									.setVisibility(View.VISIBLE);
							btn_vehicledescription_save
									.setVisibility(View.INVISIBLE);
							originalKeyListener = et_vehicledescripion_des
									.getKeyListener();
							et_vehicledescripion_des.setKeyListener(null);
							et_vehicledescripion_des.setFocusable(false);
							originalKeyListener = et_vehicledescription_tile
									.getKeyListener();
							et_vehicledescription_tile.setKeyListener(null);
							et_vehicledescription_tile.setFocusable(false);
							et_vehicledescripion_des
									.setText(SellCarDetailView.sellcardetails_description);
							et_vehicledescription_tile
									.setText(SellCarDetailView.sellcardetails_title);

						}
					});
			btn_vehicledescription_save
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							vehicledescription_title = et_vehicledescription_tile
									.getText().toString();

							vehicledescription_des = et_vehicledescripion_des
									.getText().toString();
							if (vehicledescription_title
									.equals(SellCarDetailView.sellcardetails_title)

									&& vehicledescription_des
											.equals(SellCarDetailView.sellcardetails_description)) {
								/*System.out
										.println("this is vehicle description condition");*/
								Toast.makeText(getApplicationContext(),
										"No changes to save",
										Toast.LENGTH_SHORT).show();
							} else {
								pDialog = new ProgressDialog(
										VehicleDescription.this);
								pDialog.setMessage("Please wait..");
								pDialog.setIndeterminate(true);
								pDialog.setCancelable(false);
								pDialog.show();

								MyTask myTask = new MyTask();
								myTask.execute();
								// vehicledescription_senddata();
							}
						}
					});

			// state id's
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
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						VehicleDescription.this).create();
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

			final String SOAP_ACTION = "http://tempuri.org/UpdateCarDetails";

			final String METHOD_NAME = "UpdateCarDetails";
			final String NAMESPACE = "http://tempuri.org/";

			final String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=UpdateCarDetails";
			final String TAG = "HELLO";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			// SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("UID", SellCarDetailView.sellcardetails_uid);
			request.addProperty("Year", year);
			request.addProperty("ExteriorColor",
					SellCarDetailView.sellcardetails_exteriorcolor);
			request.addProperty("InteriorColor",
					SellCarDetailView.sellcardetails_interiorcolor);
			request.addProperty("Transmission",
					SellCarDetailView.sellcardetails_transimssion);
			request.addProperty("DriveTrain",
					SellCarDetailView.sellcardetails_drivetrain);
			request.addProperty("NumberOfDoors",
					SellCarDetailView.sellcardetails_numberofdoors);
			request.addProperty("MakeModelID",
					SellCarDetailView.sellcardetails_makemodelid);
			request.addProperty("BodyTypeID",
					SellCarDetailView.sellcardetails_bodyid);
			request.addProperty("CarID", SellCarDetailView.sellcardetails_carid);
			request.addProperty("Price", SellCarDetailView.sellcardetails_price);
			request.addProperty("Mileage",
					SellCarDetailView.sellcardetails_milleagedisplay);
			request.addProperty("VIN", SellCarDetailView.sellcardetails_vin);
			request.addProperty("NumberOfCylinder",
					SellCarDetailView.sellcardetails_numberofCylinder);
			request.addProperty("FueltypeID",
					SellCarDetailView.sellcardetails_FueltypeID);
			request.addProperty("zip", SellCarDetailView.sellcarderails_zip);
			request.addProperty("City", SellCarDetailView.sellcarderails_city);
			request.addProperty("Description", vehicledescription_des);
			request.addProperty("VehicleCondition",
					SellCarDetailView.sellcardetails_vechiclecondition);
			request.addProperty("Title", vehicledescription_title);
			request.addProperty("StateID", StateID);
			request.addProperty("AuthenticationID", Uid);
			request.addProperty("CustomerID", Uno);
			request.addProperty("SessionID", Seller_Login.SessionID);
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
				SoapPrimitive result = (SoapPrimitive) soapEnvelope
						.getResponse();

				String temp1 = result.toString();
				// bank.setText(temp1);
				Log.v("TAG", temp1);
				if (temp1.equals("Success")) {
					VehicleDescription.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Thank You, Your modifications are saved",
									Toast.LENGTH_SHORT).show();

							finish();
						}
					});

				} else if (temp1.equals("Session timed out")) {

					VehicleDescription.this.runOnUiThread(new Runnable() {
						public void run() {
							Intent in = new Intent(getApplicationContext(),
									Seller_Login.class);
							Toast.makeText(getApplicationContext(),
									"Your session is timed out",
									Toast.LENGTH_SHORT).show();
							startActivity(in);
						}
					});
				} else if (temp1.equals("Failed")) {
					VehicleDescription.this.runOnUiThread(new Runnable() {
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
				VehicleDescription.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Server Error occured,Please try again",
								Toast.LENGTH_LONG).show();
					}
				});
			}

			return null;
		}

	}

}
