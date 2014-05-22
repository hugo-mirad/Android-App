package com.mobi.mobicarz;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends Activity{
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};

	EditText phone;
	EditText email, firstname, lastname, message, city, price;
	Button submit, cancel;
	WifiManager wim;
	String strEmailAddress, emails, firstnames, lastnames, messages, phonenum;
	String Make, Model, Price, year, cid, Email;
	InetAddress ownIP;
	String BuyerEmail, BuyerCity, BuyerPhone, BuyerFirstName, BuyerLastName,
			BuyerComments, IpAddress, BuyerPrice;
	String regEx = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
			+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
			+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
			+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
	String sellernum,toemail,CustomerID;
	String AuthenticationID="ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(isOnline()){
			setContentView(R.layout.testemail);
			man();
			CustomerID = MainHomeScreen.CustomerID;
			Intent intent = getIntent();
			Make = intent.getExtras().getString("Make");
			Model = intent.getExtras().getString("Model");
			Price = intent.getExtras().getString("Price");
			year = intent.getExtras().getString("Year");
			cid = intent.getExtras().getString("Cid");
			sellernum = intent.getExtras().getString("Phone");
			toemail=intent.getExtras().getString("Tomail");
			System.out.println("this is email"+toemail);

			email = (EditText) findViewById(R.id.email);
			

			lastname = (EditText) findViewById(R.id.lastname);
			firstname = (EditText) findViewById(R.id.firstname);
			city = (EditText) findViewById(R.id.message);
			message = (EditText) findViewById(R.id.editText2);
			price = (EditText) findViewById(R.id.editText1);
			cancel = (Button) findViewById(R.id.back);
			phone = (EditText) findViewById(R.id.phone);

			IpAddress = ownIP.getHostAddress();

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();

				}

			});
			submit = (Button) findViewById(R.id.submit);
			submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BuyerComments = message.getText().toString().replace("%20", " ").trim();
					
					BuyerPrice = price.getText().toString().trim();
					
					
					BuyerPhone = phone.getText().toString().trim();
					BuyerFirstName = firstname.getText().toString().trim();
					
					BuyerLastName = lastname.getText().toString().trim();
					
					BuyerCity = city.getText().toString().trim();
					
					if (email.length() == 0) {
						System.out.println("this is checking email");
						
						System.out.println("email checking");
						strEmailAddress = "info@unitedcarexchange.com";
						// strEmailAddress="info@unitedcarexchange.com";
					} else {
						strEmailAddress = email.getText().toString().trim();
						/* strEmailAddress="info@unitedcarexchange.com"; */
					}
					phonenum = phone.getText().toString().trim();
					Matcher matcherObj = Pattern.compile(regEx).matcher(
							strEmailAddress);
					if (phonenum.length() == 0) {
						Toast.makeText(getApplicationContext(),
								"Enter valid phone number", Toast.LENGTH_LONG)
								.show();
					} else if (phonenum.length() < 10) {
						Toast.makeText(getApplicationContext(),
								"Enter valid phone number", Toast.LENGTH_LONG)
								.show();
					} else if (matcherObj.matches()
							|| strEmailAddress.equalsIgnoreCase("")) {
						
						String SOAP_ACTION = "http://tempuri.org/SaveBuyerRequestMobile";
						String METHOD_NAME = "SaveBuyerRequestMobile ";
						String NAMESPACE = "http://tempuri.org/";
						String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=SaveBuyerRequestMobile";
						String TAG = "HELLO";
						SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
						

						request.addProperty("BuyerEmail", strEmailAddress);
						request.addProperty("BuyerCity",BuyerCity);
						request.addProperty("BuyerPhone",phonenum);
						request.addProperty("BuyerFirstName",BuyerFirstName);
						request.addProperty("BuyerLastName",BuyerLastName);
						request.addProperty("BuyerComments",BuyerComments);
						request.addProperty("IpAddress",IpAddress);
						request.addProperty("Sellerphone",sellernum);
						request.addProperty("Sellerprice",Price.replace("$", "").replace(",", ""));
						request.addProperty("Carid",cid);
						request.addProperty("sYear",year);
						request.addProperty("Make",Make);
						request.addProperty("Model",Model);
						request.addProperty("price",BuyerPrice);
						request.addProperty("ToEmail",toemail);
						request.addProperty("AuthenticationID",AuthenticationID);
						request.addProperty("CustomerID",CustomerID);
						try {
							SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
									SoapEnvelope.VER11);
							envelope.dotNet = true;

							envelope.setOutputSoapObject(request);

							AndroidHttpTransport aht = new AndroidHttpTransport(URL);

							aht.call(SOAP_ACTION, envelope);

							
						 SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
							String temp1 = response.toString();
							// bank.setText(temp1);
							Log.v("TAG", temp1);
							if(temp1.equals("true")){
								Toast.makeText(getApplicationContext(),
										"Thank you,your email has been sent.",
										Toast.LENGTH_LONG).show();
								finish();
							}
							
						}catch(Exception e){
							e.printStackTrace();
							
									Toast.makeText(getApplicationContext(),
											"Server Error occured,Please try again",
											Toast.LENGTH_LONG).show();
								
						}
						} else {

						Toast.makeText(getApplicationContext(),
								"Invalid Email:", Toast.LENGTH_LONG).show();
						email.setText("");

					}

				}

			});
		}else{
			try {

				AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
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
	

	private void man() {
		// TODO Auto-generated method stub
		try {
			ownIP = InetAddress.getLocalHost();
			System.out.println("IP of my Android := " + ownIP.getHostAddress());
			
		} catch (Exception e) {
			System.out.println("Exception caught =" + e.getMessage());
		}

	}

	class MyAsycall extends
			AsyncTask<String, ArrayList<CarInfo>, ArrayList<CarInfo>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected ArrayList<CarInfo> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getcallData(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<CarInfo> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}

	}

	ArrayList<CarInfo> getcallData(String url) {
		InputStream is = null;
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url.replace(" ", "%20"));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MyHandler myHandler = null;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			myHandler = new MyHandler();
			parser.parse(is, myHandler);
			is.close();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myHandler.carList;

	}

}
