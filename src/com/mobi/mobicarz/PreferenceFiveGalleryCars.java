package com.mobi.mobicarz;

import java.util.ArrayList;

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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class PreferenceFiveGalleryCars extends Activity{
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};
	TextView tv_gallerycars;
	private ImageView selectedImageView;

	private ImageView leftArrowImageView;

	private ImageView rightArrowImageView;

	private Gallery gallery;

	private int selectedImagePosition = 0;

	ArrayList<String> mystringArray;

	public String[] myRemoteImages;
	Button back;
	private com.mobi.adapters.ViewGalleryAdapter galImageAdapter;
	Button btn_car, btn_search, btn_preferencs, btn_mylist;
	String features_carid;
	TextView tv_gallerycarno;
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(isOnline()){
	//	System.out.println("this is gallery view");
		setContentView(R.layout.gallerylayout);
		back = (Button) findViewById(R.id.backhome);
		tv_gallerycars = (TextView) findViewById(R.id.tv_gallerycars);
		tv_gallerycarno=(TextView)findViewById(R.id.tv_gallerycarno);
		String year=PreferenceCarDetailViewFive.year1;
		String make=PreferenceCarDetailViewFive.make1;
		String model=PreferenceCarDetailViewFive.model1;
		mystringArray = getIntent().getStringArrayListExtra("cars");
		Log.i("detailsarray", "" + mystringArray);
	 i = mystringArray.size();
	//	System.out.println("this is no of cars" + i);
		//tv_gallerycars.setText("Total images : " + i);
		tv_gallerycars.setText(year+" "+make+" "+model);
		
		
		leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
		rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
		gallery = (Gallery) findViewById(R.id.gallery);
		btn_car=(Button)findViewById(R.id.car);
		btn_mylist=(Button)findViewById(R.id.mylist);
		btn_preferencs=(Button)findViewById(R.id.preference);
		btn_search=(Button)findViewById(R.id.search);

		features_carid = CarDetailView.car_id;
		btn_preferencs.setBackgroundResource(R.drawable.preference_act);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				/*Intent in =new Intent(getApplicationContext(),PreferenceCarDetailViewFive.class);
				startActivity(in);*/
			}
		});
		btn_car.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent car_in = new Intent(getApplicationContext(),
						MainActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				car_in.putExtra("CAR_ID", features_carid);
				startActivity(car_in);
			}
		});
		btn_mylist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mylist_in = new Intent(getApplicationContext(),
						MyListView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mylist_in);
			}
		});
		btn_preferencs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent preferenc_in = new Intent(getApplicationContext(),
						PreferenceView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(preferenc_in);
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent search_in = new Intent(getApplicationContext(),
						PreferenceView.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(search_in);
			}
		});

		leftArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition > 0) {
					--selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);
			}
		});

		rightArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition < mystringArray.size() - 1) {
					++selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);

			}
		});
		

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				selectedImagePosition = pos;
				//Toast.makeText(GalleryCars.this, "Your selected position = " + pos, Toast.LENGTH_SHORT).show();
				int k= pos+1;
				tv_gallerycarno.setText (k+ "of "+ i);
				if (selectedImagePosition > 0
						&& selectedImagePosition < mystringArray.size() - 1) {

					/*leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_disabled));
					rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_disabled));*/
					leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_enabled));
					rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_enabled));

				} else if (selectedImagePosition == 0) {

					/*leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_enabled));*/
					leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_disabled));

				} else if (selectedImagePosition == mystringArray.size() - 1) {

					/*rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_enabled));*/
					rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_disabled));
				}
				
			}

			

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
			

		});

		galImageAdapter = new com.mobi.adapters.ViewGalleryAdapter(this,
				mystringArray);

		gallery.setAdapter(galImageAdapter);
		gallery.postInvalidate();
		gallery.setSpacing(10);
		gallery.setPadding(20, 20, 20, 20);

		if (mystringArray.size() > 0) {

			gallery.setSelection(selectedImagePosition, false);

		}

		if (mystringArray.size() == 1) {

			rightArrowImageView.setImageDrawable(getResources().getDrawable(
					R.drawable.arrow_right_enabled));
		}
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

}
