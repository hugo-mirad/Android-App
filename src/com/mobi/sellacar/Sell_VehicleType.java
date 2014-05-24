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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class Sell_VehicleType extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	TextView et_sellvehicletype_make, et_sellvehicletype_model,
			et_sellvehicletype_year;
	Spinner sp_sellvehicletype_bodystyle;

	String make, model, year, bodystyleid;
	String[] items = { "Unspecified", "4WD/SUVs", "Boat", "Car", "Cargo Van",
			"Commercial Vehicles", "Convertible", "Coupe", "Crew Cab Pickup",
			"Crossovers", "Extended Cab Pickup", "Extended Van", "Green Cars",
			"Hatchback", "Hybrids", "Luxury Cars", "Minivan", "Motorcycles",
			"Other", "Passenger Van", "People Mover", "Pickup Trucks",
			"Regular Cab Pickup", "RVs", "Sedan", "Sports Cars", "SUV",
			"Tractor", "Truck", "Ute/Pick-Up", "Van", "Wagon" };

	String[] items_id = { "0", "1", "29", "2", "19", "26", "3", "4", "22", "5",
			"23", "30", "6", "7", "8", "9", "10", "27", "21", "11", "12", "13",
			"24", "28", "14", "16", "15", "31", "17", "18", "25", "20" };
	String vehicle_body, statename, StateID, fuelname, FueltypeID;
	Button sellvehicle_edit, sellvehicle_save, sellvehicle_back,
			sellvehicle_cancel;
	String car_id, url;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String UID;
	String[] stateitems = { "UN", "AK", "AL", "AR", "AS", "AZ", "CA", "CO",
			"CT", "DC", "DE", "FL", "FM", "GA", "GU", "HI", "IA", "ID", "IL",
			"IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO",
			"MP", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH",
			"OK", "ON", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX",
			"UT", "VA", "VI", "VT", "WA", "WV", "WY", "nil" };
	TextView tv_sellvehicle_year, tv_sellvehicle_model, tv_sellvehicle_make;
	int pre_bodystyleid;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.sell_vehicletype);

			et_sellvehicletype_make = (TextView) findViewById(R.id.et_sellvehicletype_make);
			et_sellvehicletype_model = (TextView) findViewById(R.id.et_sellvehicletype_model);
			et_sellvehicletype_year = (TextView) findViewById(R.id.et_sellvehicletype_year);
			sp_sellvehicletype_bodystyle = (Spinner) findViewById(R.id.sp_sellvehicletype_bodystyle);
			tv_sellvehicle_year = (TextView) findViewById(R.id.tv_sellcarvehicletype_year);
			tv_sellvehicle_model = (TextView) findViewById(R.id.tv_sellcarvehicletype_model);
			tv_sellvehicle_make = (TextView) findViewById(R.id.tv_sellcarvehicletype_make);

			make = SellCarDetailView.sellcardetails_make;
			model = SellCarDetailView.sellcardetails_model;
			year = SellCarDetailView.sellcardetails_year;
			bodystyleid = SellCarDetailView.sellcardetails_bodytypeid;
			statename = "UN";// SellCarDetailView.sellcardetails_StateID;
			fuelname = SellCarDetailView.sellcardetails_fuel;
			/*System.out.println("this is fuelname" + fuelname);
			System.out.println("this is body style" + bodystyleid);*/
			sellvehicle_edit = (Button) findViewById(R.id.btn_sellcarvehicle_edit);
			sellvehicle_save = (Button) findViewById(R.id.btn_sellcarvehicle_save);
			sellvehicle_back = (Button) findViewById(R.id.btn_sellcarvehicleedit_back);
			sellvehicle_cancel = (Button) findViewById(R.id.btn_sellcarvehicleedit_cancel);
			if(make.equals("0")&&model.equals("0")&&year.equals("0")){
				Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
			}else{
			et_sellvehicletype_make.setText(make);
			et_sellvehicletype_model.setText(model);
			et_sellvehicletype_year.setText(year);
			tv_sellvehicle_make.setText(make);
			tv_sellvehicle_model.setText(model);
			tv_sellvehicle_year.setText(year);
			}

			ArrayAdapter aa = new ArrayAdapter(this,
					android.R.layout.simple_spinner_item, items);

			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_sellvehicletype_bodystyle.setAdapter(aa);

			List<String> programmingList3 = Arrays.asList(items);
			boolean result = programmingList3.contains(bodystyleid);

			/*System.out.println("Does programming Array contains Java? "
					+ result);*/

			pre_bodystyleid = programmingList3.indexOf(bodystyleid);
		//	System.out.println("this is index" + pre_bodystyleid);
			String s = String.valueOf(pre_bodystyleid);
		//	System.out.println("this is indexs" + s);

			sp_sellvehicletype_bodystyle.setSelection(pre_bodystyleid);

			sp_sellvehicletype_bodystyle
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							VehicleList.vehiclebodystyleselected = (String) sp_sellvehicletype_bodystyle
									.getItemAtPosition(sp_sellvehicletype_bodystyle
											.getSelectedItemPosition());
							CallService();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
			sellvehicle_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					sellvehicle_edit.setVisibility(View.INVISIBLE);
					sellvehicle_save.setVisibility(View.VISIBLE);
					sellvehicle_back.setVisibility(View.INVISIBLE);
					sellvehicle_cancel.setVisibility(View.VISIBLE);

					sp_sellvehicletype_bodystyle.setClickable(true);
				}
			});
			sellvehicle_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (VehicleList.vehiclebodystyleselected
							.equals(bodystyleid)) {
						Toast.makeText(getApplicationContext(),
								"No changes to save", Toast.LENGTH_SHORT)
								.show();
					} else {
						pDialog = new ProgressDialog(Sell_VehicleType.this);
						pDialog.setMessage("Please wait..");
						pDialog.setIndeterminate(true);
						pDialog.setCancelable(false);
						pDialog.show();

						MyTask myTask = new MyTask();
						myTask.execute();

					}
				}

			});
			sellvehicle_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			sellvehicle_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					sellvehicle_edit.setVisibility(View.VISIBLE);
					sellvehicle_save.setVisibility(View.INVISIBLE);
					sellvehicle_back.setVisibility(View.VISIBLE);
					sellvehicle_cancel.setVisibility(View.INVISIBLE);
					sp_sellvehicletype_bodystyle.setSelection(pre_bodystyleid);
					sp_sellvehicletype_bodystyle.setClickable(false);
				}
			});
			// state id
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
			// fuel
			if (fuelname.equalsIgnoreCase("Unspecified")) {
				FueltypeID = "0";
			} else if (fuelname.equalsIgnoreCase("Diesel")) {
				FueltypeID = "1";
			} else if (fuelname.equalsIgnoreCase("E-85/Gasoline")) {
				FueltypeID = "6";
			} else if (fuelname.equalsIgnoreCase("Electric")) {
				FueltypeID = "4";
			} else if (fuelname.equalsIgnoreCase("Gasoline")) {
				FueltypeID = "5";
			}

			else if (fuelname.equalsIgnoreCase("Gasoline Hybrid")) {
				FueltypeID = "7";
			}

			else if (fuelname.equalsIgnoreCase("Hybrid")) {
				FueltypeID = "3";
			} else if (fuelname.equalsIgnoreCase("Other")) {
				FueltypeID = "8";
			} else if (fuelname.equalsIgnoreCase("Petrol")) {
				FueltypeID = "2";
			}
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						Sell_VehicleType.this).create();
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
			//System.out.println("this is soap method" + vehicle_body);
			final String SOAP_ACTION = "http://tempuri.org/UpdateCarDetails";

			final String METHOD_NAME = "UpdateCarDetails";
			final String NAMESPACE = "http://tempuri.org/";

			final String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=UpdateCarDetails";
			final String TAG = "HELLO";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("UID", SellCarDetailView.sellcardetails_uid);
			/*System.out.println("this is uid"
					+ SellCarDetailView.sellcardetails_uid);*/
			request.addProperty("Year", year);
			//System.out.println("this is yer" + year);
			request.addProperty("ExteriorColor",
					SellCarDetailView.sellcardetails_exteriorcolor);
			/*System.out.println("this is ex color"
					+ SellCarDetailView.sellcardetails_exteriorcolor);*/
			request.addProperty("InteriorColor",
					SellCarDetailView.sellcardetails_interiorcolor);
			/*System.out.println("this is in color"
					+ SellCarDetailView.sellcardetails_interiorcolor);*/
			request.addProperty("Transmission",
					SellCarDetailView.sellcardetails_transimssion);
			/*System.out.println("this is trna"
					+ SellCarDetailView.sellcardetails_transimssion);*/
			request.addProperty("DriveTrain",
					SellCarDetailView.sellcardetails_drivetrain);
			/*System.out.println("this is drive"
					+ SellCarDetailView.sellcardetails_drivetrain);*/
			request.addProperty("NumberOfDoors",
					SellCarDetailView.sellcardetails_numberofdoors);
			/*System.out.println("this is no of doors"
					+ SellCarDetailView.sellcardetails_numberofdoors);*/
			request.addProperty("MakeModelID",
					SellCarDetailView.sellcardetails_makemodelid);
			/*System.out.println("this is makemodelid"
					+ SellCarDetailView.sellcardetails_makemodelid);*/
			request.addProperty("BodyTypeID", vehicle_body);
			//System.out.println("this is body id" + vehicle_body);
			request.addProperty("CarID", SellCarDetailView.sellcardetails_carid);
			/*System.out.println("this is car id"
					+ SellCarDetailView.sellcardetails_carid);*/
			request.addProperty("Price", SellCarDetailView.sellcardetails_price);
			/*System.out.println("this is price"
					+ SellCarDetailView.sellcardetails_price);*/
			request.addProperty("Mileage",
					SellCarDetailView.sellcardetails_milleagedisplay);
			/*System.out.println("this is mi"
					+ SellCarDetailView.sellcardetails_milleage);*/
			request.addProperty("VIN", SellCarDetailView.sellcardetails_vin);
			/*System.out.println("this is vin"
					+ SellCarDetailView.sellcardetails_vin);*/
			request.addProperty("NumberOfCylinder",
					SellCarDetailView.sellcardetails_numberofCylinder);
			/*System.out.println("this si no of cy"
					+ SellCarDetailView.sellcardetails_numberofCylinder);*/
			request.addProperty("FueltypeID",
					SellCarDetailView.sellcardetails_FueltypeID);
			/*System.out.println("this is fuel id"
					+ SellCarDetailView.sellcardetails_FueltypeID);*/
			request.addProperty("zip", SellCarDetailView.sellcarderails_zip);
			/*System.out.println("this is zip"
					+ SellCarDetailView.sellcarderails_zip);*/
			request.addProperty("City", SellCarDetailView.sellcarderails_city);
			/*System.out.println("this is city"
					+ SellCarDetailView.sellcarderails_city);*/
			request.addProperty("Description",
					SellCarDetailView.sellcardetails_description);
			/*System.out.println("this is des"
					+ SellCarDetailView.sellcardetails_description);*/
			request.addProperty("VehicleCondition",
					SellCarDetailView.sellcardetails_vechiclecondition);
			/*System.out.println("this is veh con"
					+ SellCarDetailView.sellcardetails_vechiclecondition);*/
			request.addProperty("Title", SellCarDetailView.sellcardetails_title);
			/*System.out.println("this is title"
					+ SellCarDetailView.sellcardetails_title);*/
			request.addProperty("StateID", StateID);
			//System.out.println("this is stateid" + StateID);
			request.addProperty("AuthenticationID", Uid);
			request.addProperty("CustomerID", Uno);
			request.addProperty("SessionID", Seller_Login.SessionID);
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(request);
			HttpTransportSE aht = new HttpTransportSE(URL);
		//	System.out.println("this is soap url" + aht);
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
					Sell_VehicleType.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Thank You, Your modifications are saved",
									Toast.LENGTH_SHORT).show();

							finish();
							pDialog.dismiss();
						}
					});

				} else if (temp1.equals("Session timed out")) {
					Sell_VehicleType.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(
									getApplicationContext(),
									"Your session is timed out, please login again",
									Toast.LENGTH_SHORT).show();
							Intent in = new Intent(getApplicationContext(),
									Seller_Login.class);
							startActivity(in);
						}
					});
				} else if (temp1.equals("Failed")) {
					Sell_VehicleType.this.runOnUiThread(new Runnable() {
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
				Sell_VehicleType.this.runOnUiThread(new Runnable() {
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

	private void CallService() {
		// TODO Auto-generated method stub
		if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Unspecified")) {
			vehicle_body = "0";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("4WD/SUVs")) {
			vehicle_body = "1";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Boat")) {
			vehicle_body = "29";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Car")) {
			vehicle_body = "2";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Cargo Van")) {
			vehicle_body = "19";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Commercial Vehicles")) {
			vehicle_body = "26";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Convertible")) {
			vehicle_body = "3";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Coupe")) {
			vehicle_body = "4";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Crew Cab Pickup")) {
			vehicle_body = "22";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Crossovers")) {
			vehicle_body = "5";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Extended Cab Pickup")) {
			vehicle_body = "23";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Extended Van")) {
			vehicle_body = "30";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Green Cars")) {
			vehicle_body = "6";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Hatchback")) {
			vehicle_body = "7";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Hybrids")) {
			vehicle_body = "8";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Luxury Cars")) {
			vehicle_body = "9";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Minivan")) {
			vehicle_body = "10";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Motorcycles")) {
			vehicle_body = "27";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Other")) {
			vehicle_body = "21";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Passenger Van")) {
			vehicle_body = "11";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("People Mover")) {
			vehicle_body = "12";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Pickup Trucks")) {
			vehicle_body = "13";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Regular Cab Pickup")) {
			vehicle_body = "24";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("RVs")) {
			vehicle_body = "28";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Sedan")) {
			vehicle_body = "14";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Sports Cars")) {
			vehicle_body = "16";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("SUV")) {
			vehicle_body = "15";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Tractor")) {
			vehicle_body = "31";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Truck")) {
			vehicle_body = "17";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Ute/Pick-Up")) {
			vehicle_body = "18";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Van")) {
			vehicle_body = "25";
		} else if (sp_sellvehicletype_bodystyle.getSelectedItem().toString()
				.equalsIgnoreCase("Wagon")) {
			vehicle_body = "20";
		}

		//System.out.println("this is vehicle body" + vehicle_body);
	}

}
