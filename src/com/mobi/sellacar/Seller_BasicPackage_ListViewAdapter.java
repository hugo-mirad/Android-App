package com.mobi.sellacar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobi.mobicarz.R;

public class Seller_BasicPackage_ListViewAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	/*public ListViewAdapter(Context context, Sell_Home sell_Home,
			ArrayList<HashMap<String, String>> contactList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		data = contactList;
		imageLoader = new ImageLoader(context);
	}
*/
	public Seller_BasicPackage_ListViewAdapter(Context context,
			Seller_Package_Basic seller_Package_Basic,
			ArrayList<HashMap<String, String>> contactList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		data = contactList;
		imageLoader = new ImageLoader(context);
	}

	public Seller_BasicPackage_ListViewAdapter(Context context,
			Seller_Package_Standard seller_Package_Standard,
			ArrayList<HashMap<String, String>> contactList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		data = contactList;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup parent) {
		// TODO Auto-generated method stub'
		NumberFormat Millageformat = NumberFormat.getNumberInstance();
		Millageformat.setMinimumIntegerDigits(1);
		TextView year;
		TextView model;
		TextView make, millage, price, carid;
		ImageView img;
	//	System.out.println("this is imgurl" + Sell_Home.TAG_IMAGE);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.sellhome_display, parent,
				false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		year = (TextView) itemView.findViewById(R.id.tv_selldisplay_year);

		/*make = (TextView) itemView.findViewById(R.id.tv_selldisplay_make);
		model = (TextView) itemView.findViewById(R.id.tv_selldisplay_model);*/
		millage = (TextView) itemView.findViewById(R.id.tv_selldisplay_mileage);
		price = (TextView) itemView.findViewById(R.id.tv_selldisplay_price);
		carid = (TextView) itemView.findViewById(R.id.tv_selldisplay_carid);

		// Locate the ImageView in listview_item.xml
		img = (ImageView) itemView.findViewById(R.id.sellhomedisplay_img);

		
		year.setText(resultp.get(Seller_Package_Basic.TAG_YEAR)+" "+resultp.get(Seller_Package_Basic.TAG_model)+" "+resultp.get(Seller_Package_Basic.TAG_MAKE));

		/*make.setText(resultp.get(Seller_Package_Basic.TAG_model));
		model.setText(resultp.get(Seller_Package_Basic.TAG_MAKE));*/
		millage.setText("Millage: "+Millageformat.format(Double.parseDouble(resultp
				.get(Seller_Package_Basic.TAG_Mileage)))+"Mi");
		price.setText("price($): "+resultp.get(Seller_Package_Basic.TAG_PRICE));
		carid.setText(resultp.get(Seller_Package_Basic.TAG_CARID));

	
		imageLoader.DisplayImage(resultp.get(Seller_Package_Basic.TAG_IMAGE), img);
		return itemView;
	}

}

