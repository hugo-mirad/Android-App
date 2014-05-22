package com.mobi.sellacar;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class SellerRegistration_Info extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	String seller_carid, url, session_id;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	JSONArray contacts = null;
	String reg_Address, reg_AltEmail, reg_AltPhone, reg_BusinessName,
			reg_CarIDs, reg_City, reg_Name, reg_PhoneNumber, reg_StateCode,
			reg_StateID, reg_UId, reg_UserID, reg_UserName, reg_Zip, reg_state;
	EditText et_reg_comapnyname, et_reg_altphone, et_reg_altemailid,
			et_reg_city, et_reg_address, et_reg_zipcode;
	TextView et_reg_name, et_reg_phoneno, et_reg_emailid;
	Spinner sp_reg_statename;
	Button btn_reg_edit, btn_reg_back, btn_reg_save, btn_cancel;

	String StateCode;
	private KeyListener originalKeyListener;
	String update_reg_name, update_reg_address, update_reg_city,
			update_reg_stateid, update_reg_zip, update_reg_phone,
			update_reg_uid, update_reg_businessname, update_reg_altemail,
			update_reg_altphoneno, update_reg_email;
	String[] items = { "UN", "AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT",
			"DC", "DE", "FL", "FM", "GA", "GU", "HI", "IA", "ID", "IL", "IN",
			"KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO", "MP",
			"MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK",
			"ON", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT",
			"VA", "VI", "VT", "WA", "WV", "WY", "nil" };
	String[] stateids = { "0", "2", "1", "5", "3", "4", "6", "7", "8", "10",
			"9", "12", "11", "13", "14", "15", "18", "59", "16", "17", "19",
			"20", "21", "25", "24", "22", "23", "26", "27", "29", "39", "28",
			"30", "37", "38", "31", "33", "34", "35", "32", "36", "40", "41",
			"60", "42", "44", "45", "43", "46", "47", "48", "49", "50", "51",
			"54", "53", "52", "55", "57", "56", "58", "nil" };
	int position, index1, index2;
	String StateID, success;
	TextView tv_reginfo_state;
	ProgressDialog pDialog;
	String zipresult;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	String regEx = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
			+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
			+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
			+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isOnline()) {
			setContentView(R.layout.registration_info);

			seller_carid = Seller_Login.UID;
			session_id = Seller_Login.SessionID;
		//	System.out.println("this is car id" + seller_carid);
			// StateCode = Sell_Home.reg_StateCode;

			tv_reginfo_state = (TextView) findViewById(R.id.tv_reginfo_statename);
			et_reg_name = (TextView) findViewById(R.id.et_reg_name);
			
			et_reg_comapnyname = (EditText) findViewById(R.id.et_reg_companyname);
			originalKeyListener = et_reg_comapnyname.getKeyListener();
			et_reg_comapnyname.setKeyListener(null);
			et_reg_phoneno = (TextView) findViewById(R.id.et_reg_phoneno);
			
			et_reg_phoneno.setKeyListener(null);
			et_reg_altphone = (EditText) findViewById(R.id.et_reg_altphone);
			originalKeyListener = et_reg_altphone.getKeyListener();
			et_reg_altphone.setKeyListener(null);
			et_reg_emailid = (TextView) findViewById(R.id.et_reg_emailid);
			
			et_reg_emailid.setKeyListener(null);
			et_reg_altemailid = (EditText) findViewById(R.id.et_reg_altemailid);
			originalKeyListener = et_reg_altemailid.getKeyListener();
			et_reg_altemailid.setKeyListener(null);
			et_reg_city = (EditText) findViewById(R.id.et_reg_city);
			originalKeyListener = et_reg_city.getKeyListener();
			et_reg_city.setKeyListener(null);
			et_reg_address = (EditText) findViewById(R.id.et_reg_address);
			originalKeyListener = et_reg_address.getKeyListener();
			et_reg_address.setKeyListener(null);
			et_reg_zipcode = (EditText) findViewById(R.id.et_reg_zipcode);
			originalKeyListener = et_reg_zipcode.getKeyListener();
			et_reg_zipcode.setKeyListener(null);

			sp_reg_statename = (Spinner) findViewById(R.id.sp_reg_state);

			btn_reg_edit = (Button) findViewById(R.id.btn_registration_edit);
			btn_reg_save = (Button) findViewById(R.id.btn_registration_save);
			btn_cancel = (Button) findViewById(R.id.btn_canel);
			btn_reg_back = (Button) findViewById(R.id.btn_back);

			loaddata();

			et_reg_name.setText(reg_Name);
			et_reg_comapnyname.setText(reg_BusinessName);
			et_reg_phoneno.setText(reg_PhoneNumber);
			et_reg_altphone.setText(reg_AltPhone);
			et_reg_emailid.setText(reg_UserName);
			et_reg_altemailid.setText(reg_AltEmail);
			et_reg_city.setText(reg_City);
			et_reg_address.setText(reg_Address);
			et_reg_zipcode.setText(reg_Zip);

			ArrayAdapter aa = new ArrayAdapter(this,
					android.R.layout.simple_spinner_item, items);

			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp_reg_statename.setAdapter(aa);

			reg_StateID = reg_StateCode;// reg_state;
			tv_reginfo_state.setText(reg_StateID);

			List<String> programmingList3 = Arrays.asList(items);
			boolean result = programmingList3.contains(reg_StateID);

			/*System.out.println("Does programming Array contains Java? "
					+ result);*/

			int index3 = programmingList3.indexOf(reg_StateID);
		//	System.out.println("this is index" + index3);
			sp_reg_statename.setSelection(index3);

			sp_reg_statename
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long id) {

							VehicleList.stateselected = (String) sp_reg_statename
									.getItemAtPosition(sp_reg_statename
											.getSelectedItemPosition());
							/*System.out.println("this is state id"
									+ VehicleList.stateselected);*/
							CallService();

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					btn_reg_edit.setVisibility(View.VISIBLE);
					btn_reg_save.setVisibility(View.INVISIBLE);
					btn_reg_back.setVisibility(View.VISIBLE);
					btn_cancel.setVisibility(View.INVISIBLE);

					originalKeyListener = et_reg_comapnyname.getKeyListener();
					et_reg_comapnyname.setKeyListener(null);
					et_reg_comapnyname.setFocusable(false);

					originalKeyListener = et_reg_altphone.getKeyListener();
					et_reg_altphone.setKeyListener(null);
					et_reg_altphone.setFocusable(false);

					originalKeyListener = et_reg_altemailid.getKeyListener();
					et_reg_altemailid.setKeyListener(null);
					et_reg_altemailid.setFocusable(false);
				
					originalKeyListener = et_reg_city.getKeyListener();
					et_reg_city.setKeyListener(null);
					et_reg_city.setFocusable(false);
					
					originalKeyListener = et_reg_address.getKeyListener();
					et_reg_address.setKeyListener(null);
					et_reg_address.setFocusable(false);
					
					originalKeyListener = et_reg_zipcode.getKeyListener();
					et_reg_zipcode.setKeyListener(null);
					et_reg_zipcode.setFocusable(false);
					
					et_reg_name.setText(reg_Name);
					et_reg_comapnyname.setText(reg_BusinessName);
					et_reg_phoneno.setText(reg_PhoneNumber);
					et_reg_altphone.setText(reg_AltPhone);
					et_reg_emailid.setText(reg_UserName);
					et_reg_altemailid.setText(reg_AltEmail);
					et_reg_city.setText(reg_City);
					et_reg_address.setText(reg_Address);
					et_reg_zipcode.setText(reg_Zip);

				}
			});
			btn_reg_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			btn_reg_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					tv_reginfo_state.setVisibility(View.GONE);
					sp_reg_statename.setVisibility(View.VISIBLE);
					btn_reg_edit.setVisibility(View.INVISIBLE);
					btn_reg_save.setVisibility(View.VISIBLE);
					btn_reg_back.setVisibility(View.INVISIBLE);
					btn_cancel.setVisibility(View.VISIBLE);

					et_reg_comapnyname.setKeyListener(originalKeyListener);
					et_reg_comapnyname.requestFocus();
					et_reg_comapnyname.setFocusable(true);
					et_reg_comapnyname.setFocusableInTouchMode(true);

					et_reg_altphone.setKeyListener(originalKeyListener);
					et_reg_altphone.requestFocus();
					et_reg_altphone.setInputType(InputType.TYPE_CLASS_NUMBER);
					et_reg_altphone.setFocusable(true);
					et_reg_altphone.setFocusableInTouchMode(true);

					et_reg_altemailid.setKeyListener(originalKeyListener);
					et_reg_altemailid.requestFocus();
					et_reg_altemailid.setFocusable(true);
					et_reg_altemailid.setFocusableInTouchMode(true);
					
					et_reg_city.setKeyListener(originalKeyListener);
					et_reg_city.requestFocus();
					et_reg_city.setFocusable(true);
					et_reg_city.setFocusableInTouchMode(true);
					
					et_reg_address.setKeyListener(originalKeyListener);
					et_reg_address.requestFocus();
					et_reg_address.setFocusable(true);
					et_reg_address.setFocusableInTouchMode(true);
					
					et_reg_zipcode.setKeyListener(originalKeyListener);
					et_reg_zipcode.setInputType(InputType.TYPE_CLASS_NUMBER);
					et_reg_zipcode.setFocusable(true);
					et_reg_zipcode.setFocusableInTouchMode(true);
					
					et_reg_address.requestFocus();
					sp_reg_statename.setClickable(true);
					
				}
			});
			btn_reg_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pDialog = new ProgressDialog(SellerRegistration_Info.this);
				
					pDialog.setMessage("Please wait..");
					
					pDialog.setIndeterminate(true);
					pDialog.setCancelable(false);
					pDialog.show();

					new MyAsyncTask().execute(url);
				}
			});
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						SellerRegistration_Info.this).create();
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

	class MyAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			update_reg_name = et_reg_name.getText().toString();
		//	System.out.println("this is update name" + update_reg_name);
			update_reg_address = et_reg_address.getText().toString();
		//	System.out.println("add" + update_reg_address);
			update_reg_city = et_reg_city.getText().toString();
		//	System.out.println("city" + update_reg_city);
			update_reg_email = et_reg_emailid.getText().toString().trim();

			update_reg_zip = et_reg_zipcode.getText().toString();
			update_reg_phone = et_reg_phoneno.getText().toString();
			update_reg_businessname = et_reg_comapnyname.getText().toString();
			update_reg_altemail = et_reg_altemailid.getText().toString();
			update_reg_altphoneno = et_reg_altphone.getText().toString();

			Matcher matcherObj = Pattern.compile(regEx).matcher(
					update_reg_altemail);
			if (update_reg_altphoneno.length() < 10) {
				SellerRegistration_Info.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Please enter valid Phone number",
								Toast.LENGTH_SHORT).show();
						pDialog.dismiss();
					}
				});
			} else if (!matcherObj.matches()) {
				SellerRegistration_Info.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Please enter valid Alt Email Id",
								Toast.LENGTH_SHORT).show();
						// finish();

						pDialog.dismiss();
					}
				});
			} else if (update_reg_zip.length() < 5) {
				SellerRegistration_Info.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Please enter valid zip code",
								Toast.LENGTH_SHORT).show();
						// finish();

						pDialog.dismiss();
					}
				});
			}

			else if (update_reg_altemail.equals(update_reg_email)) {
				Toast.makeText(
						getApplicationContext(),
						"Email ID exists. Provide an Alt Email ID which is differnet from existing mail ID ",
						Toast.LENGTH_SHORT).show();
				pDialog.dismiss();
			} else if (update_reg_zip.length() == 5) {
				checkzip();
				if (zipresult.equals("true")) {
					final String SOAP_ACTION = "http://tempuri.org/UpdateUserRegistration";

					final String METHOD_NAME = "UpdateUserRegistration ";
					final String NAMESPACE = "http://tempuri.org/";

					final String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=UpdateUserRegistration";
					final String TAG = "HELLO";

					SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
					request.addProperty("name", update_reg_name);
					request.addProperty("address", update_reg_address);
					request.addProperty("city", update_reg_city);
					request.addProperty("stateID", StateID);
					request.addProperty("zip", update_reg_zip);
					request.addProperty("phone", update_reg_phone);
					request.addProperty("UID", reg_UId);
					request.addProperty("businessName", update_reg_businessname);
					request.addProperty("altEmail", update_reg_altemail);
					request.addProperty("altPhone", update_reg_altphoneno);
					request.addProperty("AuthenticationID", Uid);
					request.addProperty("CustomerID", Uno);
					request.addProperty("SessionID", session_id);

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

						SoapObject response = (SoapObject) soapEnvelope
								.getResponse();
						String temp1 = response.toString();
						
						Log.v("TAG", temp1);
						for (int i = 0; i < ((SoapObject) response)
								.getPropertyCount(); i++) {
							Object root = ((SoapObject) response)
									.getProperty(i);
							if (root instanceof SoapObject) {
								success = ((SoapObject) root).getProperty(
										"AASuccess").toString();
								//System.out.println("tanstran" + success);
							}
						}

						if (success.equals("Success")) {
							SellerRegistration_Info.this
									.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(
													getApplicationContext(),
													"Thank You, Your modifications are saved",
													Toast.LENGTH_SHORT).show();
											finish();

											pDialog.dismiss();
										}
									});
						} else if (temp1.equals("Session timed out")) {
							SellerRegistration_Info.this
									.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											Intent in = new Intent(
													getApplicationContext(),
													Seller_Login.class);
											startActivity(in);
										}
									});

						} else if (temp1.equals("Failed")) {
							SellerRegistration_Info.this
									.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											Toast.makeText(
													getApplicationContext(),
													"Error occured,Please try again",
													Toast.LENGTH_LONG).show();
										}
									});

						}

					} catch (Exception e) {
						e.printStackTrace();
						pDialog.cancel();
						SellerRegistration_Info.this
								.runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(
												getApplicationContext(),
												"Server Error occured,Please try again",
												Toast.LENGTH_LONG).show();
									}
								});
					}

				} else {
					SellerRegistration_Info.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(),
									"Invalid zip code", Toast.LENGTH_LONG)
									.show();
							pDialog.dismiss();
						}
					});
				}
			}
			return null;

		}

		protected void onPostExecute() {

			pDialog.dismiss();

		}
	}

	public void checkzip() {
		// TODO Auto-generated method stub
	//	System.out.println("this is checking zip code");
		final String SOAP_ACTION = "http://tempuri.org/CheckZips";

		final String METHOD_NAME = "CheckZips";
		final String NAMESPACE = "http://tempuri.org/";

		final String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=CheckZips";
		final String TAG = "HELLO";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("zipId", update_reg_zip);
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
			SoapPrimitive response = (SoapPrimitive) soapEnvelope.getResponse();
			
			zipresult = response.toString();
			//System.out.println("this is boolean value" + zipresult);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void CallService() {
		// TODO Auto-generated method stub

		if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("UN")) {
			StateID = "0";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("AK")) {
			StateID = "2";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("AL")) {
			StateID = "1";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("AR")) {
			StateID = "5";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("AS")) {
			StateID = "3";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("AZ")) {
			StateID = "4";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("CA")) {
			StateID = "6";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("CO")) {
			StateID = "7";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("CT")) {
			StateID = "8";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("DC")) {
			StateID = "10";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("DE")) {
			StateID = "9";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("FL")) {
			StateID = "12";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("FM")) {
			StateID = "11";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("GA")) {
			StateID = "13";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("GU")) {
			StateID = "14";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("HI")) {
			StateID = "15";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("IA")) {
			StateID = "18";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("ID")) {
			StateID = "59";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("IL")) {
			StateID = "16";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("IN")) {
			StateID = "17";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("KS")) {
			StateID = "19";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("KY")) {
			StateID = "20";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("LA")) {
			StateID = "21";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MA")) {
			StateID = "25";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MD")) {
			StateID = "24";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("ME")) {
			StateID = "22";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MH")) {
			StateID = "23";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MI")) {
			StateID = "26";
		}

		else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MN")) {
			StateID = "27";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MO")) {
			StateID = "29";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MP")) {
			StateID = "39";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MS")) {
			StateID = "28";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("MT")) {
			StateID = "30";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("NC")) {
			StateID = "37";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("ND")) {
			StateID = "38";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("NE")) {
			StateID = "31";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("NH")) {
			StateID = "33";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("NJ")) {
			StateID = "34";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("NN")) {
			StateID = "35";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("NV")) {
			StateID = "32";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("NY")) {
			StateID = "36";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("OH")) {
			StateID = "40";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("OK")) {
			StateID = "41";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("ON")) {
			StateID = "60";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("OR")) {
			StateID = "42";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("PA")) {
			StateID = "44";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("PR")) {
			StateID = "45";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("PW")) {
			StateID = "43";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("RI")) {
			StateID = "46";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("SC")) {
			StateID = "47";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("SD")) {
			StateID = "48";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("TN")) {
			StateID = "49";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("TX")) {
			StateID = "50";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("UT")) {
			StateID = "51";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("VA")) {
			StateID = "54";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("VI")) {
			StateID = "53";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("VT")) {
			StateID = "52";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("WA")) {
			StateID = "55";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("WI")) {
			StateID = "57";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("WV")) {
			StateID = "56";
		} else if (sp_reg_statename.getSelectedItem().toString()
				.equalsIgnoreCase("WY")) {
			StateID = "58";
		}
		//System.out.println("this is my state id" + StateID);
	}

	private void loaddata() {
		// TODO Auto-generated method stub
		url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/GetUserRegistrationDetailsByID/"
				+ seller_carid + "/" + Uid + "/" + Uno + "/" + session_id + "";
		
		JSONParser jParser = new JSONParser();

		
		JSONObject json1 = jParser.getJSONFromUrl(url);
		
		try {
			// Getting Array of Contacts
			contacts = json1
					.getJSONArray("GetUserRegistrationDetailsByIDResult");
			for (int i = 0; i < contacts.length(); i++) {
				JSONObject c = contacts.getJSONObject(i);
				String sucess = c.getString("AASucess");
				if (sucess.equals("Success")) {
					reg_AltEmail = c.getString("AltEmail");
					reg_Address = c.getString("Address");
					reg_AltPhone = c.getString("AltPhone");
					reg_BusinessName = c.getString("BusinessName");
					reg_CarIDs = c.getString("CarIDs");
					reg_City = c.getString("City");
					reg_Name = c.getString("Name");
					reg_PhoneNumber = c.getString("PhoneNumber");
					reg_StateCode = c.getString("StateCode");
					reg_StateID = c.getString("StateID");
					reg_UId = c.getString("UId");
					reg_UserID = c.getString("UserID");
					reg_UserName = c.getString("UserName");
					reg_Zip = c.getString("Zip");
					reg_state = c.getString("_state");
				//	System.out.println("this is alt" + reg_StateCode);
				} else if (sucess.equals("Session timed out")) {
					Intent in = new Intent(getApplicationContext(),
							Seller_Login.class);
					startActivity(in);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
