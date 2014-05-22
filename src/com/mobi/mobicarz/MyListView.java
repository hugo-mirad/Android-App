package com.mobi.mobicarz;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyListView extends Activity implements OnClickListener,
		OnItemClickListener {
	public boolean isOnline() {

		ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	};
	Button btn_car, btn_search, btn_preferencs, btn_mylist;
	TextView tv_mylist_gotohome;
	private ListView bookListView;
	ArrayList<String> makeAl = new ArrayList<String>();
	ArrayList<Bitmap> imageAl = new ArrayList<Bitmap>();
	ArrayList<String> PriceAl = new ArrayList<String>();
	ArrayList<String> MillageAl = new ArrayList<String>();
	ArrayList<String> ModelAl = new ArrayList<String>();
	ArrayList<String> carIDlist = new ArrayList<String>();
	ArrayList<String> YearA1 = new ArrayList<String>();
	com.mobi.adapters.MySQLiteHelper helper;
	TextView nodata;
	Cursor cursor;
	String carids;
	public ArrayList<CarInfo> carMainList = new ArrayList<CarInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(isOnline()){
		setContentView(R.layout.mylistview);

		tv_mylist_gotohome = (TextView) findViewById(R.id.mylist_gotohome);

		tv_mylist_gotohome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // TODO Auto-generated method

				Intent in = new Intent(getApplicationContext(), MainHomeScreen.class);
				startActivity(in);
			}
		});

	
		bookListView = (ListView) findViewById(R.id.list);
		nodata = (TextView) findViewById(R.id.nodatamylist);
		nodata.setVisibility(View.GONE);
		bookListView.setItemsCanFocus(false);

		btn_search = (Button) findViewById(R.id.search);
		btn_preferencs = (Button) findViewById(R.id.preference);
		btn_car = (Button) findViewById(R.id.car);
		btn_mylist=(Button)findViewById(R.id.mylist);
		btn_mylist.setBackgroundResource(R.drawable.my_list_act);

		btn_search.setOnClickListener(this);
		btn_preferencs.setOnClickListener(this);
		btn_car.setOnClickListener(this);

		helper = new com.mobi.adapters.MySQLiteHelper(getApplicationContext());
		helper.open();
		cursor = helper.getCarList();
		cursor.moveToFirst();
		if (cursor.getCount() == 0) {
			nodata.setVisibility(View.VISIBLE);
			
		} else if (cursor.getCount() > 0) {
			do {
				String make=cursor.getString(cursor
						.getColumnIndex(com.mobi.adapters.MySQLiteHelper.MAKE));
						System.out.println("this is testing make value"+make);
				makeAl.add(cursor.getString(cursor
						.getColumnIndex(com.mobi.adapters.MySQLiteHelper.MAKE)));
				
				String price=cursor.getString(cursor
						.getColumnIndex(com.mobi.adapters.MySQLiteHelper.PRICE));
				if(price.equals("0")){
					PriceAl.add("");
				}else{
					PriceAl.add(cursor.getString(cursor
							.getColumnIndex(com.mobi.adapters.MySQLiteHelper.PRICE)));
				}
		//		System.out.println("this is testing price value"+price);
				
				ModelAl.add(cursor.getString(cursor
						.getColumnIndex(com.mobi.adapters.MySQLiteHelper.Model)));
				String millage=cursor.getString(cursor
						.getColumnIndex(com.mobi.adapters.MySQLiteHelper.Millage));
				if(millage.equals("0")){
					MillageAl.add("");
				}else{
				MillageAl
						.add(cursor.getString(cursor
								.getColumnIndex(com.mobi.adapters.MySQLiteHelper.Millage)));
				}
				carIDlist.add(cursor.getString(cursor
						.getColumnIndex(com.mobi.adapters.MySQLiteHelper.CID)));
				YearA1.add(cursor.getString(cursor
						.getColumnIndex(com.mobi.adapters.MySQLiteHelper.YEAR)));
				
				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						cursor.getBlob(cursor
								.getColumnIndex(com.mobi.adapters.MySQLiteHelper.IMG)));
				
				Bitmap b = BitmapFactory.decodeStream(inputStream);
				imageAl.add(b);
			//	System.out.println("maniteja" + carIDlist);
			} while (cursor.moveToNext());
		}
		bookListView.setOnItemClickListener(this);
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

	private void getDatabasedate() {
		// TODO Auto-generated method stub

		updateList();
		// Initialize our Adapter and plug it to the ListView
		MyListADapter customAdapter = new MyListADapter(
				getApplicationContext(), makeAl, imageAl, PriceAl, MillageAl,
				ModelAl, YearA1, carIDlist);
		bookListView.setAdapter(customAdapter);


		customAdapter.notifyDataSetChanged();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		helper.close();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search:
			Intent search_in = new Intent(getApplicationContext(),
					SearchView.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(search_in);
			break;
		case R.id.car:
			Intent car_in = new Intent(getApplicationContext(),
					MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(car_in);
			break;
		case R.id.preference:
			Intent prefernce_in = new Intent(getApplicationContext(),
					PreferenceView.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(prefernce_in);
			break;
		default:
			break;
		}
	}

	public class MyListADapter extends BaseAdapter {

		ArrayList<String> makeAl = null;
		ArrayList<Bitmap> imageAl = null;
		ArrayList<String> priceAl = null;
		ArrayList<String> MillageAl = null;
		ArrayList<String> ModelAl = null;
		ArrayList<String> YearA1 = null;
		ArrayList<String> carIDlist = null;
		ArrayList<String> images, price, make, model, year, milage;
		ArrayList<String> ListView;
		Context con;
		PhotosLoader photoLoader;

		public MyListADapter(Context con, ArrayList<String> make,
				ArrayList<Bitmap> imageAl, ArrayList<String> priceAl,
				ArrayList<String> millageAl, ArrayList<String> modelAl2,
				ArrayList<String> yearA12, ArrayList<String> carIDlist2) {
			// TODO Auto-generated constructor stub
			this.con = con;
			this.MillageAl = millageAl;
			this.ModelAl = modelAl2;
			this.makeAl = make;
			this.carIDlist = carIDlist2;
			this.priceAl = priceAl;
			this.imageAl = imageAl;
			this.YearA1 = yearA12;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return makeAl.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;

			final ViewHolder h;
			if (vi == null) {
				LayoutInflater inflater = (LayoutInflater) con
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				vi = inflater.inflate(R.layout.mylistcars, null);
				h = new ViewHolder();
				h.img = (ImageView) vi.findViewById(R.id.myiv);
				h.make = (TextView) vi.findViewById(R.id.myMake);
				h.milage = (TextView) vi.findViewById(R.id.myMillage);
				// h.model = (TextView) vi.findViewById(R.id.myModel);
				h.price = (TextView) vi.findViewById(R.id.myprice);
				h.delete = (Button) vi.findViewById(R.id.btn_delete);
				vi.setTag(h);
			} else {
				h = (ViewHolder) vi.getTag();
			}

			vi.postInvalidate();
			System.out.println("imag456" + imageAl.get(position));

			h.img.setImageBitmap(imageAl.get(position));
			h.make.setText(/* "Make:"+ */
			YearA1.get(position) + " " + makeAl.get(position) + " "
					+ ModelAl.get(position));
			h.price.setText("Price($): " + priceAl.get(position));
			h.milage.setText("Mileage: " + MillageAl.get(position));
			// h.model.setText(/*"Model:"+*/ModelAl.get(position));
			h.delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// if (((CheckBox) v).isChecked()) {
					new AlertDialog.Builder(MyListView.this)
							.setTitle("Confirm Delete")
							.setIcon(R.drawable.icon)
							.setMessage(
									"Are you sure you want to delete this car?")
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface d,
												int which) {

											helper.deleteCar(carIDlist
													.get(position));
											// finish();
											Intent previewMessage = new Intent(
													getApplicationContext(),
													MyListView.class);

											startActivity(previewMessage);

										}

									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface d,
												int which) {

											// h.delete.setChecked(false);
										}

									}).create().show();

				}
				// }

			});
			return vi;
		}

		class ViewHolder {
			ImageView img;
			TextView make, model, price, milage, year;
			Button delete;

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent previewMessage = new Intent(getApplicationContext(),
				MyListCarDetailsView.class)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Bundle b = new Bundle();
		b.putInt("CAR_ID", Integer.parseInt(carIDlist.get(arg2).toString()));
		previewMessage.putExtras(b);
		startActivity(previewMessage);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getDatabasedate();
	}

	private void updateList() {
		// db.open();
		cursor.requery();
	}

}
