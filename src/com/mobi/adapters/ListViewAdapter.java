package com.mobi.adapters;

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

import com.mobi.cropimage.ImageLoaders;
import com.mobi.mobicarz.R;
import com.mobi.mobicarz.SearchResultView;

public class ListViewAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoaders imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context, Context context2,
			ArrayList<HashMap<String, String>> contactList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		data = contactList;
		imageLoader = new ImageLoaders(context);
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
		// TODO Auto-generated method stub
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setMinimumFractionDigits(0);

		NumberFormat Millageformat = NumberFormat.getNumberInstance();
		Millageformat.setMinimumIntegerDigits(1);
		TextView year;
		TextView model;
		TextView make, millage, price, carid;
		ImageView img;
	//	System.out.println("this is imgurl" + SearchResultView.TAG_IMAGE);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.searchresultdisplay, parent,
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

		// Capture position and set results to the TextViews
		year.setText(resultp.get(SearchResultView.TAG_YEAR)
				 + " "
				+ (resultp.get(SearchResultView.TAG_MAKE.replace(" ", ""))
						) + " "
				+ (resultp.get(SearchResultView.TAG_model.replace(" ", ""))
						));

		/*
		 * make.setText(resultp.get(SearchResultsView.TAG_MAKE));
		 * model.setText(resultp.get(SearchResultsView.TAG_model));
		 */
		if (Millageformat.format(
				Double.parseDouble(resultp.get(SearchResultView.TAG_Mileage)))
				.equals("0")) {
			millage.setText("Millage:");
		} else {
			millage.setText("Millage: "
					+ Millageformat.format(Double.parseDouble(resultp
							.get(SearchResultView.TAG_Mileage))) + "mi");
		}
		if (resultp.get(SearchResultView.TAG_PRICE).equals("0")) {
			price.setText("Price($):");
		} else {
			price.setText("Price($): "
					+ resultp.get(SearchResultView.TAG_PRICE));
		}
		carid.setText(resultp.get(SearchResultView.TAG_CARID));

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.displayImage(resultp.get(SearchResultView.TAG_IMAGE), img);
		return itemView;
	}
}
