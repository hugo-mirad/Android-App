package com.mobi.adapters;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobi.cropimage.ImageLoaders;
import com.mobi.mobicarz.R;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<com.mobi.mobicarz.CarInfo> data = null;
	private static LayoutInflater inflater = null;
	public ImageLoaders imageLoader;
	final String domainName = "http://www.unitedcarexchange.com/";

	public LazyAdapter(Activity myAsy, ArrayList<com.mobi.mobicarz.CarInfo> result) {
		activity = myAsy;
		data = result;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoaders(activity.getApplicationContext());
		// MainActivity.MAX_PAGE =data.get(0).getPageCount();
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setMinimumFractionDigits(0);
		if (convertView == null)

			vi = inflater.inflate(R.layout.imageitem, null);

		TextView price = (TextView) vi.findViewById(R.id.Price1);
		

		TextView make = (TextView) vi.findViewById(R.id.make);
		TextView model = (TextView) vi.findViewById(R.id.lazy_model);
		TextView year = (TextView) vi.findViewById(R.id.lazy_year);
		// TextView model=(TextView)vi.findViewById(R.id.model);
		ImageView image = (ImageView) vi.findViewById(R.id.image);
		// price.setText("$"+String.format("%.2f",
		// data.get(position).getPrice()));
		price.setText(format.format(Double.parseDouble(data.get(position)
				.getPrice())));

		make.setText(data.get(position).getMake());
		// model.setText(data.get(position).get_Model());
		//System.out.println("this is model tyep" + model);
		year.setText(data.get(position).getYearOfMake());
		model.setText(data.get(position).getModel());

		// pric.setText(ourForm.format(data.get(position).getPrice()));

		imageLoader.displayImage(
				domainName + data.get(position).getThumbnail(), image);

		return vi;

	}

}