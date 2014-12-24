package com.loosefang.thejerryapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loosefang.thejerryapp.R;
import com.loosefang.thejerryapp.javafile.MemeImages;

import java.util.List;

@SuppressLint("InflateParams")
public class ImageAdapterCard extends BaseAdapter {

	public static LayoutInflater inflater;
	public Activity activity;
	public List<MemeImages> images;
	
	public ImageAdapterCard(Activity act,List<MemeImages> imgs) {
		inflater=LayoutInflater.from(act);
		this.images=imgs;
		this.activity=act;
	}
	
	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		return images.get(position);
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){			
			convertView = inflater.inflate(R.layout.activity_list_items, parent, false);
		}

		TextView img_tag=(TextView)convertView.findViewById(R.id.txt_activity_tag);
		img_tag.setText(images.get(position).getName());
		ImageView img=(ImageView)convertView.findViewById(R.id.img_activity_icon);				
				
		img.setImageBitmap(scaleImage(images.get(position).getFile(), 40, activity));
 		return convertView;

	}

	public  Bitmap scaleImage(int id, float width, Context c) {
		try {

			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(c.getResources(),id,options);
			options.inSampleSize = calculateInSampleSize(options, (int) width,
					(int) width);
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeResource(c.getResources(),id,options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// bitmap.recycle();
		return null;
	}
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

}
