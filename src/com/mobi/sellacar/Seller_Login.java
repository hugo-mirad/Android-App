package com.mobi.sellacar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.JSONParser;
import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class Seller_Login extends Activity {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	public static final int TABLET_MIN_DP_WEIGHT = 450;
	EditText et_sell_username, et_sell_pwd;
	Button btn_sell_login;
	TextView tv_sell_makenewone;
	String sell_username, sell_pwd;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String url;
	JSONArray PerformLoginMobileResult = null;
	String AASuccess;
	public static String UID, SessionID;
	ProgressDialog pDialog;
	LinearLayout ll_login, ll_loading;
	public Context Context;
	static String seller_name;
	TextView tv_home,tv_sellmakeonenow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		if (isOnline()) {

			setContentView(R.layout.activity_main1);

			et_sell_username = (EditText) findViewById(R.id.et_sellusername);
			et_sell_pwd = (EditText) findViewById(R.id.et_sellpwd);
			btn_sell_login = (Button) findViewById(R.id.btn_selllogin);
			tv_sell_makenewone = (TextView) findViewById(R.id.tv_sellmakeonenow);
			tv_sellmakeonenow=(TextView)findViewById(R.id.tv_sellmakeonenow);
			
		//	TextView myText = (TextView) findViewById(R.id.custom_font_text); 
           Typeface font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLTPro-Roman.otf"); 
			/*et_sell_username.setTypeface(font);
			et_sell_pwd.setTypeface(font);*/
			btn_sell_login.setTypeface(font);
		//	tv_sellmakeonenow.setTypeface(font);

			ll_login = (LinearLayout) findViewById(R.id.ll_login);
			tv_home = (TextView) findViewById(R.id.tv_login_home);

			tv_home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent in = new Intent(getApplicationContext(),
							MainHomeScreen.class);
					startActivity(in);
				}
			});

			btn_sell_login.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					pDialog = new ProgressDialog(Seller_Login.this);

					pDialog.setCancelable(false);

					new MyAsyncTask().execute(url);

				}
			});

			tv_sell_makenewone.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub

					Intent in = new Intent(getApplicationContext(),
							Seller_Registration.class);

					startActivity(in);
					return false;
				}
			});
		} else {
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(
						Seller_Login.this).create();
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
			// TODO Auto-generated method stub
			//et_sell_username.setText("tes8985693686");
			//et_sell_pwd.setText("123456");
			sell_username = et_sell_username.getText().toString().trim()
					.replaceAll(" ", "%20");
			sell_pwd = et_sell_pwd.getText().toString().trim()
					.replaceAll(" ", "%20");

			if (sell_username.equals("")) {
				Seller_Login.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Please enter User name", Toast.LENGTH_LONG)
								.show();
					}
				});
			} else if (sell_pwd.equals("")) {

				Seller_Login.this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Please enter Password", Toast.LENGTH_LONG)
								.show();
					}
				});

			} else {
				// url="http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/PerformLoginMobile/"
				url = "http://www.unitedcarexchange.com/MobileService/ServiceMobile.svc/PerformLoginMobile/"
						+ sell_username
						+ "/"
						+ sell_pwd
						+ "/"
						+ Uid
						+ "/"
						+ Uno + "/".replaceAll(" ", "%20");
				JSONParser jParser = new JSONParser();

				// getting JSON string from URL
				JSONObject json = jParser.getJSONFromUrl(url);
				try {
					// Getting Array of Contacts
					PerformLoginMobileResult = json
							.getJSONArray("PerformLoginMobileResult");
					JSONObject c = PerformLoginMobileResult.getJSONObject(0);
					AASuccess = c.getString("AASuccess");
					UID = c.getString("UID");
					SessionID = c.getString("SessionID");
					seller_name = c.getString("Name");
					/*System.out.println("this is login testing" + AASuccess);
					System.out.println("this is login testing" + UID);*/
					if (AASuccess.equals("User Existed")) {

						Intent in = new Intent(getApplicationContext(),
								Sell_Home.class);
						startActivity(in);

					} else {
						Seller_Login.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(),
										"Invalid Username or Password",
										Toast.LENGTH_LONG).show();

								pDialog.dismiss();
							}
						});
					}

				} catch (JSONException e) {
					e.printStackTrace();
					pDialog.cancel();
					Seller_Login.this.runOnUiThread(new Runnable() {
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

		protected void onPostExecute() {

			pDialog.dismiss();

		}
	}
}
