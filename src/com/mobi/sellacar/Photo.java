package com.mobi.sellacar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.mobicarz.MainHomeScreen;
import com.mobi.mobicarz.R;

public class Photo extends Activity{
	Button back, btn_photoupload_uploading;
	ImageView img_photo, testimg;

	private static final int SOAP_VERSION = SoapEnvelope.VER11;
	InputStream is = null;
	byte[] array1,bytearray;
	Bitmap thumbnail;
	private static int RESULT_LOAD_IMAGE = 1;
	private static final int CAMERA_PIC_REQUEST = 2500;
	ByteArrayOutputStream stream;
	// InputStream is=null;
	byte[] array;
	static byte[] data, img_byte;
	int bi, a2;
	String picturePath;
	String strBase64, encImage, temp;
	String Uid = "ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654";
	String Uno = MainHomeScreen.CustomerID;
	String temp_base="";
	ProgressDialog pDialog;
	TextView tv_photoheading;
	Button btn_photo_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoupload);
		btn_photo_back=(Button)findViewById(R.id.uploadback_button1);
		img_photo = (ImageView) findViewById(R.id.img_uploadphoto);
		back = (Button) findViewById(R.id.uploadback_button1);
		testimg = (ImageView) findViewById(R.id.testimg);
		tv_photoheading=(TextView)findViewById(R.id.show_content);
		String make = SellCarDetailView.sellcardetails_make;
		String model = SellCarDetailView.sellcardetails_model;
		String year = SellCarDetailView.sellcardetails_year;
		tv_photoheading.setText(year+" "+make+" "+model);
//		img_photo.setBackgroundResource(R.drawable.carimages1);
		btn_photoupload_uploading = (Button) findViewById(R.id.btn_photouploading);

		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(i, RESULT_LOAD_IMAGE);
		btn_photoupload_uploading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				uploadphoto();
			}
		});
		btn_photo_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent in=new Intent(getApplicationContext(),Vehiclephotos_Upload.class);
			startActivity(in);
			}
		});
	}
	

	protected void uploadphoto() {
		pDialog = new ProgressDialog(Photo.this);
		pDialog.setMessage("Please wait..");
		pDialog.setIndeterminate(true);
		pDialog.setCancelable(false);
		pDialog.show();

		
		MyTask myTask = new MyTask();
		myTask.execute();
		
	
		
		
	}

	public class MyTask extends AsyncTask<String, Integer, String> {
		protected String doInBackground(String... arg0) {
		
			
			String SOAP_ACTION = "http://tempuri.org/UploadPictureByCarIDFromAndroid";
			String METHOD_NAME = "UploadPictureByCarIDFromAndroid";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://www.unitedcarexchange.com/MobileService/CarService.asmx?op=UploadPictureByCarIDFromAndroid";
			String TAG = "HELLO";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			// temp_base ="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";
		 temp_base=Base64.encodeToString(byteArray,Base64.DEFAULT);
	///	temp_base=com.uce.sellacar.Base64.encodeBytes(byteArray);
			 int mm=temp_base.length();
			 /*System.out.println("this is base 64 string"+temp_base+mm);
			 System.out.println("this is base 64 string length"+mm);*/
			
		//	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("CarID", SellCarDetailView.sellcardetails_carid);//55954
			/*System.out.println("this is photo"
					+ SellCarDetailView.sellcardetails_carid);*/
			request.addProperty("make", SellCarDetailView.sellcardetails_make);//Acura
			/*System.out.println("this is make photo"
					+ SellCarDetailView.sellcardetails_make);*/
			request.addProperty("model", SellCarDetailView.sellcardetails_model);//2.2CL
			/*System.out.println("this is model photo"
					+ SellCarDetailView.sellcardetails_model);*/
			request.addProperty("year", SellCarDetailView.sellcardetails_year);//2013
			/*System.out.println("thsi is year photo"
					+ SellCarDetailView.sellcardetails_year);*/
			request.addProperty("UserID", SellCarDetailView.sellcardetails_uid);//6387
			/*System.out.println("this is userid"
					+ SellCarDetailView.sellcardetails_uid);*/

	//		request.addProperty("pic","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");
			request.addProperty("pic",temp_base);
			//System.out.println("this is piccontent" +temp_base);

			request.addProperty("AuthenticationID", Uid);//ds3lkDFD1F5fFGnf2daFs45REsd6re54yb0sc654
			request.addProperty("CustomerID", Uno);//123456
			request.addProperty("SessionID", Seller_Login.SessionID);
			//System.out.println("this is session photo" + Seller_Login.SessionID);
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// new MarshalBase64().register(soapEnvelope);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(request);
			HttpTransportSE aht = new HttpTransportSE(URL);
			
			try {
				Log.v(TAG, "this is login test 24");
				aht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				aht.debug = true;
				aht.call(SOAP_ACTION, soapEnvelope);
				SoapPrimitive result1 = (SoapPrimitive) soapEnvelope
						.getResponse();
				// SoapObject result1 = (SoapObject)soapEnvelope.getResponse();
				String soap_res = result1.toString();
				//System.out.println("soap response" + soap_res);
				if (soap_res.equals("Success")) {

					Photo.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Thank You, Your modifications are saved",
									Toast.LENGTH_SHORT).show();
							pDialog.dismiss();
							Intent intent=new Intent();  
		                    //intent.putExtra("MESSAGE",message);  
		                      
		                    setResult(2,intent); 
							

							finish();
						//	pDialog.dismiss();
						}
					});

				} else if (soap_res.equals("Session timed out")) {
					Photo.this.runOnUiThread(new Runnable() {
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
				} else if (soap_res.equals("Failed")) {
					Photo.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Error occured,Please try again",
									Toast.LENGTH_LONG).show();
							pDialog.dismiss();

						}
					});
				}

			} catch (Exception e) {
				e.printStackTrace();
				pDialog.cancel();
				Photo.this.runOnUiThread(new Runnable() {
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			BitmapFactory.Options options = new BitmapFactory.Options();
			/*options.inPreferredConfig = Bitmap.Config.ARGB_8888;
					options.inDither = false;*/
	//	options.inSampleSize =4;
//		/options.inPurgeable=true;
			try {
				thumbnail = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, options);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			img_photo.setImageBitmap(thumbnail);
		

		} else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK
				&& null != data) {
			Bitmap image = (Bitmap) data.getExtras().get("data");
			// ImageView imageview = (ImageView) findViewById(R.id.ImageView01);
			img_photo.setImageBitmap(image);
		} else{
			Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_LONG).show();
			Intent in=new Intent(getApplicationContext(),Vehiclephotos_Upload.class);
			startActivity(in);
		}
	}
	
	
	
}
