package com.mobi.mobicarz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


public class PhotosLoader extends AsyncTask<String, Void, Bitmap>{

	 public static final String TAG="PhotosLoader";
		private String url;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		private final WeakReference<ImageView> imageViewReference;
		
		public PhotosLoader(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}
		

		


	
		
		protected Bitmap doInBackground(String... params) 
		{
			url = params[0];
			try 
			{
				return BitmapFactory.decodeStream(new URL(url.replace("Emp", "").replace(" ", "%20")).openConnection()
						.getInputStream());
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
				return null;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return null;
			}
			catch (OutOfMemoryError e) 
			{
				System.gc();
				Log.d(TAG,"######Out of In ASYNTASK::::::::::::::::::::::::::");
				return null;
			}

		}

		protected void onPostExecute(Bitmap result) {
		

			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				

				if (imageView != null) 
				{ 
            	imageView.setImageBitmap(result);
					
				}

			}
		}

		protected void onPreExecute() {
			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					
					
					imageView.setImageResource(R.drawable.loading9);
					
				}
			}
		}
}
