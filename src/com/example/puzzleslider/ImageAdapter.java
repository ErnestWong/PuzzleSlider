package com.example.puzzleslider;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Bitmap> imagetiles;
	private int imagex, imagey;
	public int emptyTile;

	/**
	 * initializes fields
	 * @param c
	 * @param image
	 * @param emptytile
	 */
	public ImageAdapter(Context c, ArrayList<Bitmap> image, int emptytile) {
		context = c;
		imagetiles = image;
		if (image == null) {
			String TAG = "ImageadapterNull";
			Log.d(TAG, TAG);
		}
		imagex = image.get(0).getWidth();
		imagey = image.get(0).getHeight();
		this.emptyTile = emptytile;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imagetiles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imagetiles.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	/**
	 * sets the view for every tile; sets visibility of empty tile to none
	 */
	@Override
	public View getView(int position, View smallView, ViewGroup fullImage) {
		// TODO Auto-generated method stub
		ImageView image;
		if (smallView == null) {
			image = new ImageView(context);
			image.setLayoutParams(new GridView.LayoutParams(imagex, imagex));
			image.setPadding(0, 5, 0, 5);
		} else {
			image = (ImageView) smallView;
		}

		image.setImageBitmap(imagetiles.get(position));
		if (position == emptyTile)
			image.setVisibility(View.INVISIBLE);
		return image;
	}

}
