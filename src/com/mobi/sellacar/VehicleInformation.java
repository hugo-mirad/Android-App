package com.mobi.sellacar;

import java.util.Arrays;
import java.util.List;

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
import android.text.InputType;
import android.text.method.KeyListener;
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

public class VehicleInformation extends Activity {
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

	String make, model, year, StateID, statename, fuelname, FueltypeID,
			vehicle_body, extreiorcolor, intreiorcolor, transmission,
			condition, drive_train, engine_cyclinder, no_doors, fuel;
	String sp_extreiorcolor, sp_intreiorcolor, sp_transmission, sp_condition,
			sp_drivetrain, sp_enginecyclinder, sp_doors, sp_fuel;
	TextView tv_vehicleinfo_make, tv_vehicleinfo_model, tv_vehicleinfo_year;
	private KeyListener originalKeyListener;
	Button btn_vehicleinfo_back, btn_vehicleinfo_edit, btn_vehicleinfo_save,
			btn_vehicleinfo_cancel;
	EditText et_vehicleinfo_title, et_vehicleinfo_askingprice,
			et_vehicleinfo_mileage, et_vehicleinfo_vin;
	String vehicleinfo_title, vehicleinfo_askingprice, vehicleinfo_mileage,
			vehicleinfo_exteriorcolor, vehicleinfo_interiorcolor,
			vehicleinfo_transmission, vehicleinfo_condition,
			vehicleinfo_drivetrain, vehicleinfo_enginecylinder,
			vehicleinfo_door, vehicleinfo_fueltype, vehicleinfo_vin;
	String[] stateitems = { "UN", "AK", "AL", "AR", "AS", "AZ", "CA", "CO",
			"CT", "DC", "DE", "FL", "FM", "GA", "GU", "HI", "IA", "ID", "IL",
			"IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO",
			"MP", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH",
			"OK", "ON", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX",
			"UT", "VA", "VI", "VT", "WA", "WV", "WY" };
	String[] exteriorcolor_array = { "Unspecified", "Light Pewter Metallic",
			"Cyper Metallic Gray", "Gold Mist", "Yellow", "Red Jewel Tintcoat",
			"Machine Silver Metallic", "Gray", "Ruby Red", "Blue Met", "Other",
			"Gold", "Burgundy", "Beige", "Two-Tone", "Silver Met", "Brown",
			"White", "Orange", "Red", "Tan", "Blue", "Red Met", "Gray Met",
			"Turquoise", "Green", "Rust", "Black", "Silver", "Victory Red",
			"Purple", "Offwhite", "Mineral White Metallic", "Maroon",
			"Dark Wood", "Metallic", "Champagne", "Light gray", "Dark Blue",
			"Light Blue/Silver" };
	String[] interior_color = { "Unspecified", "Charcoal", "Black", "Gray",
			"Other", "Gold", "Burgundy", "Beige", "Dark Gray", "Brown",
			"White", "Orange", "Red", "Tan", "Blue", "Light Gray", "Beige/Tan",
			"Silver", "Turquoise", "Green", "Offwhite", "Metal Finish",
			"Imperial Blue", "Dark Blue", "Light Blue", "Maroon" };

	String[] Transmission = { "Unspecified", "3 Speed Automatic",
			"4 Speed Automatic", "4 Speed Automatic with Electronic Overdrive",
			"4 Speed Automatic with Overdrive", "4 Speed Shiftable Automatic",
			"5 Speed Automatic", "5 Speed Manual", "6 Speed Automatic",
			"6 Speed Manual", "6 Speed Shiftable Automatic", "Automatic",
			"Automatic with Overdrive", "Manual",

			"Other", "Triptronic" };
	String[] Condition = { "Unspecified", "Excellent", "Very Good", "Good",
			"Fair", "Poor", "Parts or Salvage" };
	String[] Drive_Train = { "Unspecified", "2 wheel drive",
			"2 wheel drive - front", "2 wheel drive - rear", "4 wheel drive",
			"4 wheel drive - rear", "All wheel drive", "Rear wheel drive" };
	String[] Engine_Cylinders = { "Unspecified", "3 Cylinder", "4 Cylinder",
			"4 Cylinder Gasoline", "4 Cylinder Supercharg", "4 Cylinder Turbo",
			"5 Cylinder", "5 Cylinder Gasoline", "6 Cylinder",
			"6 Cylinder Gasoline", "7 Cylinder", "8 Cylinder",
			"8 Cylinder Diesel", "8 Cylinder Diesel Tur",
			"8 Cylinder Gasoline", "8 Cylinder Turbo", "Hybrid" };
	String[] doors = { "Unspecified", "Two Door", "Four Door", "Five Door",
			"Three Door" };
	String[] fuel_Types = { "Unspecified", "Diesel", "Petrol", "Hybrid",
			"Electric", "Gasoline", "E-85/Gasoline", "Gasoline Hybrid", "Other" };
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	Spinner et_vehicleinfo_exteriorcolor, et_vehicleinfo_interiorcolor,
			et_vehicleinfo_transmission, et_vehicleinfo_condition,
			et_vehicleinfo_drivrtrain, et_vehicleinfo_enginecylinders,
			et_vehicleinfo_door, et_vehicleinfo_fueltype;
	List<String> programmingList;
	ArrayAdapter aa, interiorcolor_adapter, transmission_adapter,
			condition_adapter, drivrtrain_adapter, engine_adapter,
			doors_adapter, fuel_adapter;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.vehicleinformation);

			make = SellCarDetailView.sellcardetails_make;
			model = SellCarDetailView.sellcardetails_model;
			year = SellCarDetailView.sellcardetails_year;
			extreiorcolor = SellCarDetailView.sellcardetails_exteriorcolor;
			intreiorcolor = SellCarDetailView.sellcardetails_interiorcolor;
			transmission = SellCarDetailView.sellcardetails_transimssion;
			condition = SellCarDetailView.sellcardetails_vechiclecondition;
			drive_train = SellCarDetailView.sellcardetails_drivetrain;
			engine_cyclinder = SellCarDetailView.sellcardetails_numberofCylinder;
			no_doors = SellCarDetailView.sellcardetails_numberofdoors;
			fuel = SellCarDetailView.sellcardetails_fuel;

			tv_vehicleinfo_make = (TextView) findViewById(R.id.tv_vehicleinformation_make);
			tv_vehicleinfo_model = (TextView) findViewById(R.id.tv_vehicleinformation_model);
			tv_vehicleinfo_year = (TextView) findViewById(R.id.tv_vehicleinformation_year);

			tv_vehicleinfo_make.setText(make);
			tv_vehicleinfo_model.setText(model);
			tv_vehicleinfo_year.setText(year);
			statename = /*"UN";*/ SellCarDetailView.sellcardetails_StateID;
			fuelname = SellCarDetailView.sellcardetails_fuel;
			btn_vehicleinfo_back = (Button) findViewById(R.id.btn_vehicleinformation_back);
			btn_vehicleinfo_edit = (Button) findViewById(R.id.btn_vehicleinformation_edit);
			btn_vehicleinfo_save = (Button) findViewById(R.id.btn_vehicleinformation_save);
			btn_vehicleinfo_cancel = (Button) findViewById(R.id.btn_vehicleinformation_cancel);

			et_vehicleinfo_askingprice = (EditText) findViewById(R.id.et_vehicleinfo_askingprice);

			et_vehicleinfo_condition = (Spinner) findViewById(R.id.et_vehicleinfo_condition);

			et_vehicleinfo_door = (Spinner) findViewById(R.id.et_vehicleinfo_doors);

			et_vehicleinfo_drivrtrain = (Spinner) findViewById(R.id.et_vehicleinfo_drivetrain);

			et_vehicleinfo_enginecylinders = (Spinner) findViewById(R.id.et_vehicleinfo_enginecylinders);

			et_vehicleinfo_exteriorcolor = (Spinner) findViewById(R.id.et_vehicleinfo_exteriorcolor);

			et_vehicleinfo_fueltype = (Spinner) findViewById(R.id.et_vehicleinfo_fueltype);

			et_vehicleinfo_interiorcolor = (Spinner) findViewById(R.id.et_vehicleinfo_interiorcolor);

			et_vehicleinfo_mileage = (EditText) findViewById(R.id.et_vehicleinfo_mileage);

			et_vehicleinfo_title = (EditText) findViewById(R.id.et_vehicleinfo_title);

			et_vehicleinfo_transmission = (Spinner) findViewById(R.id.et_vehicleinfo_transmission);

			et_vehicleinfo_vin = (EditText) findViewById(R.id.et_vehicleinfo_vin);

			addadapters();

			keyboardlistener();

			btn_vehicleinfo_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			btn_vehicleinfo_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_vehicleinfo_edit.setVisibility(View.INVISIBLE);
					btn_vehicleinfo_save.setVisibility(View.VISIBLE);
					btn_vehicleinfo_back.setVisibility(View.INVISIBLE);
					btn_vehicleinfo_cancel.setVisibility(View.VISIBLE);

					et_vehicleinfo_askingprice
							.setKeyListener(originalKeyListener);
					et_vehicleinfo_askingprice.requestFocus();
					et_vehicleinfo_askingprice
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					et_vehicleinfo_askingprice.setFocusable(true);
					et_vehicleinfo_askingprice.setFocusableInTouchMode(true);

					et_vehicleinfo_mileage.setKeyListener(originalKeyListener);
					et_vehicleinfo_mileage
							.setInputType(InputType.TYPE_CLASS_NUMBER);
					et_vehicleinfo_mileage.requestFocus();
					et_vehicleinfo_mileage.setFocusable(true);
					et_vehicleinfo_mileage.setFocusableInTouchMode(true);
					
					et_vehicleinfo_title.setKeyListener(originalKeyListener);
					et_vehicleinfo_title.requestFocus();
					et_vehicleinfo_title.setFocusable(true);
					et_vehicleinfo_title.setFocusableInTouchMode(true);

					et_vehicleinfo_vin.setKeyListener(originalKeyListener);
					et_vehicleinfo_vin.requestFocus();
					et_vehicleinfo_vin.setFocusable(true);
					et_vehicleinfo_vin.setFocusableInTouchMode(true);
					
					et_vehicleinfo_condition.setClickable(true);
					et_vehicleinfo_door.setClickable(true);
					et_vehicleinfo_drivrtrain.setClickable(true);
					et_vehicleinfo_enginecylinders.setClickable(true);
					et_vehicleinfo_exteriorcolor.setClickable(true);
					et_vehicleinfo_fueltype.setClickable(true);
					et_vehicleinfo_interiorcolor.setClickable(true);
					et_vehicleinfo_transmission.setClickable(true);

				}
			});
			btn_vehicleinfo_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_vehicleinfo_back.setVisibility(View.VISIBLE);
					btn_vehicleinfo_cancel.setVisibility(View.INVISIBLE);
					btn_vehicleinfo_edit.setVisibility(View.VISIBLE);
					btn_vehicleinfo_save.setVisibility(View.INVISIBLE);
					keyboardlistener();
					addadapters();

				}
			});
			btn_vehicleinfo_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					vehicleinfo_askingprice = et_vehicleinfo_askingprice
							.getText().toString();

					vehicleinfo_mileage = et_vehicleinfo_mileage.getText()
							.toString();

					vehicleinfo_title = et_vehicleinfo_title.getText()
							.toString();

					//System.out.println("this is title" + vehicleinfo_title);

					vehicleinfo_vin = et_vehicleinfo_vin.getText().toString();

					if (vehicleinfo_title
							.equals(SellCarDetailView.sellcardetails_title)
							&& vehicleinfo_askingprice
									.equals(SellCarDetailView.sellcardetails_price)
							&& vehicleinfo_mileage
									.equals(SellCarDetailView.sellcardetails_milleagedisplay)
							&& SellCarDetailView.sellcardetails_exteriorcolor
									.equals(sp_extreiorcolor)
							&& SellCarDetailView.sellcardetails_interiorcolor
									.equals(sp_intreiorcolor)
							&& SellCarDetailView.sellcardetails_transimssion
									.equals(sp_transmission)
							&& SellCarDetailView.sellcardetails_vechiclecondition
									.equals(sp_condition)
							&& SellCarDetailView.sellcardetails_drivetrain
									.equals(sp_drivetrain)
							&& SellCarDetailView.sellcardetails_numberofCylinder
									.equals(sp_enginecyclinder)
							&& SellCarDetailView.sellcardetails_numberofdoors
									.equals(sp_doors)
							&& SellCarDetailView.sellcardetails_fuel
									.equals(sp_fuel)
							&& vehicleinfo_vin
									.equals(SellCarDetailView.sellcardetails_vin)) {

						Toast.makeText(getApplicationContext(),
								"No changes to save", Toast.LENGTH_SHORT)
								.show();
					} else {
						pDialog = new ProgressDialog(VehicleInformation.this);
						pDialog.setMessage("Please wait..");
						pDialog.setIndeterminate(true);
						pDialog.setCancelable(false);
						pDialog.show();

						MyTask myTask = new MyTask();
						myTask.execute();
						// vehicleinformationsenddata();
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
						VehicleInformation.this).create();
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
			if (fuel.equalsIgnoreCase("Unspecified")) {
				FueltypeID = "0";
			} else if (fuel.equalsIgnoreCase("Diesel")) {
				FueltypeID = "1";
			} else if (fuel.equalsIgnoreCase("E-85/Gasoline")) {
				FueltypeID = "6";
			} else if (fuel.equalsIgnoreCase("Electric")) {
				FueltypeID = "4";
			} else if (fuel.equalsIgnoreCase("Gasoline")) {
				FueltypeID = "5";
			}

			else if (fuel.equalsIgnoreCase("Gasoline Hybrid")) {
				FueltypeID = "7";
			}

			else if (fuel.equalsIgnoreCase("Hybrid")) {
				FueltypeID = "3";
			} else if (fuel.equalsIgnoreCase("Other")) {
				FueltypeID = "8";
			} else if (fuel.equalsIgnoreCase("Petrol")) {
				FueltypeID = "2";
			}

			final String SOAP_ACTION = "http://tempuri.org/UpdateCarDetails";

			final String METHOD_NAME = "UpdateCarDetails";
			final String NAMESPACE = "http://tempuri.org/";

			final String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=UpdateCarDetails";
			final String TAG = "HELLO";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			// SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			/*System.out.println("this is in soap fuel id" + FueltypeID);
			System.out.println("this is in soap ex color" + extreiorcolor);*/

			request.addProperty("UID", SellCarDetailView.sellcardetails_uid);
			request.addProperty("Year", year);
			request.addProperty("ExteriorColor", extreiorcolor);
			request.addProperty("InteriorColor", intreiorcolor);
			request.addProperty("Transmission", transmission);
			request.addProperty("DriveTrain", drive_train);
			request.addProperty("NumberOfDoors", no_doors);
			request.addProperty("MakeModelID",
					SellCarDetailView.sellcardetails_makemodelid);
			request.addProperty("BodyTypeID",
					SellCarDetailView.sellcardetails_bodyid);

			request.addProperty("CarID", SellCarDetailView.sellcardetails_carid);
			request.addProperty("Price", vehicleinfo_askingprice);
			request.addProperty("Mileage", vehicleinfo_mileage);
			request.addProperty("VIN", vehicleinfo_vin);
			request.addProperty("NumberOfCylinder", engine_cyclinder);
			request.addProperty("FueltypeID", FueltypeID);
			request.addProperty("zip", SellCarDetailView.sellcarderails_zip);
			request.addProperty("City", SellCarDetailView.sellcarderails_city);
			request.addProperty("Description",
					SellCarDetailView.sellcardetails_description);
			request.addProperty("VehicleCondition", condition);
			request.addProperty("Title", vehicleinfo_title);
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
					VehicleInformation.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Thank You, Your modifications are saved",
									Toast.LENGTH_SHORT).show();

							finish();
						}
					});
				} else if (temp1.equals("Session timed out")) {
					VehicleInformation.this.runOnUiThread(new Runnable() {
						public void run() {
							Intent in = new Intent(getApplicationContext(),
									Seller_Login.class);
							startActivity(in);
						}
					});
				} else if (temp1.equals("Failed")) {
					VehicleInformation.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Error occured,Please try again",
									Toast.LENGTH_LONG).show();
							pDialog.cancel();
						}
					});
				}

			} catch (Exception e) {
				e.printStackTrace();

				VehicleInformation.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Server Error occured,Please try again",
								Toast.LENGTH_LONG).show();
						pDialog.cancel();
					}
				});
			}

			return null;
		}

	}

	private void addadapters() {
		// TODO Auto-generated method stub
		aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
				exteriorcolor_array);

		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_exteriorcolor.setAdapter(aa);

	//	System.out.println("this is ex color" + extreiorcolor);
		programmingList = Arrays.asList(exteriorcolor_array);
		boolean result = programmingList.contains(extreiorcolor);

	//	System.out.println("Does programming Array contains Java? " + result);

		int i = programmingList.indexOf(extreiorcolor);
	//	System.out.println("this is index" + i);
		String s = String.valueOf(i);
	//	System.out.println("this is indexs" + s);

		et_vehicleinfo_exteriorcolor.setSelection(i);
		et_vehicleinfo_exteriorcolor
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						extreiorcolor = (String) et_vehicleinfo_exteriorcolor
								.getItemAtPosition(et_vehicleinfo_exteriorcolor
										.getSelectedItemPosition());
						sp_extreiorcolor = (String) et_vehicleinfo_exteriorcolor
								.getItemAtPosition(et_vehicleinfo_exteriorcolor
										.getSelectedItemPosition());
					//	System.out.println("this ex color" + extreiorcolor);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		// array adapter for interior color
		interiorcolor_adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, interior_color);

		interiorcolor_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_interiorcolor.setAdapter(interiorcolor_adapter);

		programmingList = Arrays.asList(interior_color);
		boolean result1 = programmingList.contains(intreiorcolor);

	//	System.out.println("Does programming Array contains Java? " + result1);

		int in_color = programmingList.indexOf(intreiorcolor);
	//	System.out.println("this is index" + in_color);
		String in_color_str = String.valueOf(in_color);
	//	System.out.println("this is indexs" + in_color_str);

		et_vehicleinfo_interiorcolor.setSelection(in_color);

		et_vehicleinfo_interiorcolor
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						intreiorcolor = (String) et_vehicleinfo_interiorcolor
								.getItemAtPosition(et_vehicleinfo_interiorcolor
										.getSelectedItemPosition());
						/*System.out.println("this is sp_interior"
								+ intreiorcolor);*/
						sp_intreiorcolor = (String) et_vehicleinfo_interiorcolor
								.getItemAtPosition(et_vehicleinfo_interiorcolor
										.getSelectedItemPosition());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		// array adapter for transmission
		transmission_adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, Transmission);

		transmission_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_transmission.setAdapter(transmission_adapter);

		programmingList = Arrays.asList(Transmission);
		boolean result2 = programmingList.contains(transmission);

	//	System.out.println("Does programming Array contains Java? " + result2);

		int trans = programmingList.indexOf(transmission);
	//	System.out.println("this is index" + trans);
		String trans_str = String.valueOf(trans);
	//	System.out.println("this is indexs" + trans_str);

		et_vehicleinfo_transmission.setSelection(trans);
		et_vehicleinfo_transmission
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						transmission = (String) et_vehicleinfo_transmission
								.getItemAtPosition(et_vehicleinfo_transmission
										.getSelectedItemPosition());
						/*System.out.println("this is sp tansmission "
								+ transmission);*/
						sp_transmission = (String) et_vehicleinfo_transmission
								.getItemAtPosition(et_vehicleinfo_transmission
										.getSelectedItemPosition());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		// array adapter condition
		condition_adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, Condition);

		condition_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_condition.setAdapter(condition_adapter);

		programmingList = Arrays.asList(Condition);
		boolean result3 = programmingList.contains(condition);

	//	System.out.println("Does programming Array contains Java? " + result3);

		int con = programmingList.indexOf(condition);
	//	System.out.println("this is index" + con);
		String condition_str = String.valueOf(con);
	//	System.out.println("this is indexs" + condition_str);

		et_vehicleinfo_condition.setSelection(con);
		et_vehicleinfo_condition
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						condition = (String) et_vehicleinfo_condition
								.getItemAtPosition(et_vehicleinfo_condition
										.getSelectedItemPosition());
						/*System.out.println("this is sp vehicle condition"
								+ condition);*/
						sp_condition = (String) et_vehicleinfo_condition
								.getItemAtPosition(et_vehicleinfo_condition
										.getSelectedItemPosition());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		// adapter for drive train
		drivrtrain_adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, Drive_Train);

		drivrtrain_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_drivrtrain.setAdapter(drivrtrain_adapter);

		programmingList = Arrays.asList(Drive_Train);
		boolean result4 = programmingList.contains(drive_train);

	//	System.out.println("Does programming Array contains Java? " + result4);

		int drive = programmingList.indexOf(drive_train);
	//	System.out.println("this is index" + drive);
		String drive_str = String.valueOf(drive);
	//	System.out.println("this is indexs" + drive_str);

		et_vehicleinfo_drivrtrain.setSelection(drive);
		et_vehicleinfo_drivrtrain
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						drive_train = (String) et_vehicleinfo_drivrtrain
								.getItemAtPosition(et_vehicleinfo_drivrtrain
										.getSelectedItemPosition());
						/*System.out.println("this is sp drive train"
								+ drive_train);*/
						sp_drivetrain = (String) et_vehicleinfo_drivrtrain
								.getItemAtPosition(et_vehicleinfo_drivrtrain
										.getSelectedItemPosition());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		// adapter for engine
		engine_adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, Engine_Cylinders);

		engine_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_enginecylinders.setAdapter(engine_adapter);

		programmingList = Arrays.asList(Engine_Cylinders);
		boolean result5 = programmingList.contains(engine_cyclinder);

	//	System.out.println("Does programming Array contains Java? " + result5);

		int en = programmingList.indexOf(engine_cyclinder);
	//	System.out.println("this is index" + en);
		String en_str = String.valueOf(en);
	//	System.out.println("this is indexs" + en_str);

		et_vehicleinfo_enginecylinders.setSelection(en);
		et_vehicleinfo_enginecylinders
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						engine_cyclinder = (String) et_vehicleinfo_enginecylinders
								.getItemAtPosition(et_vehicleinfo_enginecylinders
										.getSelectedItemPosition());
						/*System.out.println("this is sp engine"
								+ engine_cyclinder);*/
						sp_enginecyclinder = (String) et_vehicleinfo_enginecylinders
								.getItemAtPosition(et_vehicleinfo_enginecylinders
										.getSelectedItemPosition());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		// adapter for number of doors
		doors_adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, doors);
		doors_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_door.setAdapter(doors_adapter);

		programmingList = Arrays.asList(doors);
		boolean result6 = programmingList.contains(no_doors);

	//	System.out.println("Does programming Array contains Java? " + result6);

		int door = programmingList.indexOf(no_doors);
	//	System.out.println("this is index" + door);
		String door_str = String.valueOf(door);
	//	System.out.println("this is indexs" + door_str);

		et_vehicleinfo_door.setSelection(door);
		et_vehicleinfo_door
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						no_doors = (String) et_vehicleinfo_door
								.getItemAtPosition(et_vehicleinfo_door
										.getSelectedItemPosition());
						/*System.out.println("this is sp door" + no_doors);*/
						sp_doors = (String) et_vehicleinfo_door
								.getItemAtPosition(et_vehicleinfo_door
										.getSelectedItemPosition());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		// fuel type adapter
		fuel_adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, fuel_Types);
		fuel_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_vehicleinfo_fueltype.setAdapter(fuel_adapter);

		programmingList = Arrays.asList(fuel_Types);
		boolean result7 = programmingList.contains(fuel);

	//	System.out.println("Does programming Array contains Java? " + result7);

		int fuel_type = programmingList.indexOf(fuel);
	//	System.out.println("this is index" + fuel_type);
		String fuel_type_str = String.valueOf(fuel_type);
	//	System.out.println("this is indexs" + fuel_type_str);

		et_vehicleinfo_fueltype.setSelection(fuel_type);

		et_vehicleinfo_fueltype
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						fuel = (String) et_vehicleinfo_fueltype
								.getItemAtPosition(et_vehicleinfo_fueltype
										.getSelectedItemPosition());
						//System.out.println("this is sp fuel" + fuel);
						sp_fuel = (String) et_vehicleinfo_fueltype
								.getItemAtPosition(et_vehicleinfo_fueltype
										.getSelectedItemPosition());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void keyboardlistener() {
		// TODO Auto-generated method stub

		originalKeyListener = et_vehicleinfo_askingprice.getKeyListener();
		et_vehicleinfo_askingprice.setKeyListener(null);
		et_vehicleinfo_askingprice.setFocusable(false);
		
		originalKeyListener = et_vehicleinfo_mileage.getKeyListener();
		et_vehicleinfo_mileage.setKeyListener(null);
		et_vehicleinfo_mileage.setFocusable(false);
		
		originalKeyListener = et_vehicleinfo_title.getKeyListener();
		et_vehicleinfo_title.setKeyListener(null);
		et_vehicleinfo_title.setFocusable(false);
		
		originalKeyListener = et_vehicleinfo_vin.getKeyListener();
		et_vehicleinfo_vin.setKeyListener(null);
		et_vehicleinfo_vin.setFocusable(false);
		
		et_vehicleinfo_condition.setClickable(false);
		et_vehicleinfo_door.setClickable(false);
		et_vehicleinfo_drivrtrain.setClickable(false);
		et_vehicleinfo_enginecylinders.setClickable(false);
		et_vehicleinfo_exteriorcolor.setClickable(false);
		et_vehicleinfo_fueltype.setClickable(false);
		et_vehicleinfo_interiorcolor.setClickable(false);
		et_vehicleinfo_transmission.setClickable(false);

		if (SellCarDetailView.sellcardetails_price.equals("Emp")) {
			et_vehicleinfo_askingprice.setText("");
		} else {
			et_vehicleinfo_askingprice
					.setText(SellCarDetailView.sellcardetails_price);
		}
		if (SellCarDetailView.sellcardetails_milleagedisplay.equals("Emp")) {
			et_vehicleinfo_mileage.setText("");
		} else {
			et_vehicleinfo_mileage
					.setText(SellCarDetailView.sellcardetails_milleagedisplay);
		}
		if (SellCarDetailView.sellcardetails_title.equals("Emp")) {
			et_vehicleinfo_title.setText("");
		} else {
			et_vehicleinfo_title
					.setText(SellCarDetailView.sellcardetails_title);
		}
		if (SellCarDetailView.sellcardetails_vin.equals("Emp")) {
			et_vehicleinfo_vin.setText("");
		} else {
			et_vehicleinfo_vin.setText(SellCarDetailView.sellcardetails_vin);
		}
	}

}
