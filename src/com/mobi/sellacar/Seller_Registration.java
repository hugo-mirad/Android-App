package com.mobi.sellacar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class Seller_Registration extends Activity {
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

	EditText et_seller_registration_firstname, et_seller_registration_lastname,
			et_seller_registration_email, et_seller_registration_confirmemail,
			et_seller_registration_phone;
	String reg_firstname, reg_lastname, reg_email, reg_confirmemail, reg_phone;
	Button btn_seller_registration,btn_reg_back;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String url, res;
	JSONObject SendMobileRegistrationRequestResult = null;
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
			setContentView(R.layout.seller_registration);
			et_seller_registration_firstname = (EditText) findViewById(R.id.tv_seller_registration_firstname);
			et_seller_registration_lastname = (EditText) findViewById(R.id.et_seller_registration_lastname);
			et_seller_registration_email = (EditText) findViewById(R.id.et_seller_registration_email);
			et_seller_registration_confirmemail = (EditText) findViewById(R.id.et_seller_registration_confirmemail);
			et_seller_registration_phone = (EditText) findViewById(R.id.et_seller_registration_phone);
			btn_seller_registration = (Button) findViewById(R.id.btn_seller_register);
			btn_reg_back=(Button)findViewById(R.id.reg_back);
			btn_reg_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in=new Intent(getApplicationContext(),Seller_Login.class);
					startActivity(in);
					
				}
			});

			register();
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						Seller_Registration.this).create();
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

	

	private void register() {
		// TODO Auto-generated method stub
		btn_seller_registration.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				reg_firstname = et_seller_registration_firstname.getText()
						.toString().trim();
				
			//	System.out.println("this is reg name" + reg_firstname);
				reg_lastname = et_seller_registration_lastname.getText()
						.toString().trim();
				reg_email = et_seller_registration_email.getText().toString()
						.trim();
				reg_confirmemail = et_seller_registration_confirmemail
						.getText().toString().trim();
				reg_phone = et_seller_registration_phone.getText().toString()
						.trim();
				Matcher matcherObj = Pattern.compile(regEx).matcher(reg_email);
				Matcher matcherObj1 = Pattern.compile(regEx).matcher(
						reg_confirmemail);

				if (reg_firstname.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Please enter first name", Toast.LENGTH_SHORT)
							.show();
				} else if (reg_lastname.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Please enter last name", Toast.LENGTH_SHORT)
							.show();
				}
				 else if (reg_email.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Please enter email ", Toast.LENGTH_SHORT).show();
				} else if (!matcherObj.matches()) {
					Toast.makeText(getApplicationContext(),
							"Email is invalid ", Toast.LENGTH_SHORT).show();
				} else if (reg_confirmemail.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Please enter confirm email ", Toast.LENGTH_SHORT)
							.show();
				} else if (!matcherObj1.matches()) {
					Toast.makeText(getApplicationContext(),
							"Confirm email  is invalid ", Toast.LENGTH_SHORT)
							.show();
				} else if (!(reg_email.equals(reg_confirmemail))) {
					Toast.makeText(getApplicationContext(),
							"Email and confirm email must be same ",
							Toast.LENGTH_SHORT).show();
				} else if (reg_phone.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Please enter phone number", Toast.LENGTH_SHORT)
							.show();
				} else if (reg_phone.length() < 10) {
					Toast.makeText(getApplicationContext(),
							"Phone number must be valid ", Toast.LENGTH_SHORT)
							.show();
				} else {
					url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/SendMobileRegistrationRequest/"
							+ reg_firstname + reg_lastname + "/"

							+ reg_email + "/"

							+ reg_phone + "/" + Uid + "/" + Uno + "/";
					JSONParser jParser = new JSONParser();

					// getting JSON string from URL
					JSONObject json = jParser.getJSONFromUrl(url);
					//System.out.println("this is json obj" + json);
					try {
						res = json
								.getString("SendMobileRegistrationRequestResult");
					//	System.out.println("this is res" + res);
						if (res.equals("true")) {
							Toast.makeText(
									getApplicationContext(),
									"Thank You, Our service representive will contact you shortly",
									Toast.LENGTH_LONG).show();
							Intent in = new Intent(getApplicationContext(),
									Seller_Login.class);
							startActivity(in);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
								Toast.makeText(getApplicationContext(),
										"Server Error occured,Please try again",
										Toast.LENGTH_LONG).show();
							
					}
				}

			}
		});
	}

}
