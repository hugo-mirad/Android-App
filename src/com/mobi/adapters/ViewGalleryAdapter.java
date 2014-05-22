package com.mobi.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mobi.cropimage.ImageLoaders;
import com.mobi.mobicarz.R;

public class ViewGalleryAdapter extends BaseAdapter{

	private LayoutInflater inflater = null;
	private ArrayList<String> images;
	private ImageLoaders imgLoader;
	private Context context;
	
	
	
	public ViewGalleryAdapter(Context ctx, ArrayList<String> images) {
		this.context = ctx;
		inflater = LayoutInflater.from(context);
		//images.clear();
		this.images = images;
		//System.out.println("thsi is no image path"+images.toString());
		imgLoader = new ImageLoaders(context);
		imgLoader.clearCache();
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		//images.clear();
		View vi = convertView;
		if(vi == null) {
			vi = inflater.inflate(R.layout.rowgallery, null);
		} else {
			vi = convertView;
		}
		 
		ImageView imageView = (ImageView)vi.findViewById(R.id.image);
		//TextView tv_image=(TextView)vi.findViewById(R.id.tv_imageno);
		imageView.setImageDrawable(null);
     	imgLoader.displayImage(images.get(position), imageView);
     //	System.out.println(images.get(position));
     	int k=position+1;
//     	/tv_image.setText(images.get(position));
	//System.out.println("this is no of image"+position);

		return vi;
		
	}
}
