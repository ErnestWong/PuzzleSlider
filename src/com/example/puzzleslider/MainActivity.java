package com.example.puzzleslider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnTouchListener {

	private Context context;
	private ImageView imgView;
	private int SPLIT_TILES;
	private int emptyIndex;
	private ArrayList<Bitmap> imagetiles;
	private ArrayList<Bitmap> correctImage;

	private float initialx, initialy, finalx, finaly;
	private GridView gridimage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();

		imgView = (ImageView) findViewById(R.id.source_image);

		// set initial values of split tiles and empty index
		SPLIT_TILES = 3;
		emptyIndex = 8;

		// fill up bitmap arraylist
		imagetiles = splitImg(imgView, SPLIT_TILES, SPLIT_TILES);
		gridimage = (GridView) findViewById(R.id.grid_view);

		// display grid image to screen and register touch listener
		gridimage.setAdapter(new ImageAdapter(this, imagetiles, emptyIndex));
		gridimage.setNumColumns(SPLIT_TILES);
		gridimage.setOnTouchListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		String TAG = "action bar";
		Log.d(TAG, "action bar");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * called when button is clicked to split into 3x3 grid
	 * 
	 * @param v
	 */
	public void buttonClick3(View v) {
		SPLIT_TILES = 3;
		emptyIndex = 8;
		// fill up imagetile arraylist with modified parameters and display grid
		// on screen
		imagetiles = splitImg(imgView, SPLIT_TILES, SPLIT_TILES);
		gridimage.setAdapter(new ImageAdapter(this, imagetiles, emptyIndex));
		gridimage.setNumColumns(SPLIT_TILES);
	}

	/**
	 * called when button is clicked to split into 4x4 grid
	 * 
	 * @param v
	 */
	public void buttonClick4(View v) {
		SPLIT_TILES = 4;
		emptyIndex = 15;
		// fill up imagetile arraylist with modified parameters and display grid
		// on screen
		imagetiles = splitImg(imgView, SPLIT_TILES, SPLIT_TILES);
		gridimage.setAdapter(new ImageAdapter(this, imagetiles, emptyIndex));
		gridimage.setNumColumns(SPLIT_TILES);
	}

	/**
	 * called when button is clicked to split into 5x5 grid
	 * 
	 * @param v
	 */
	public void buttonClick5(View v) {
		SPLIT_TILES = 5;
		emptyIndex = 24;
		// fill up imagetile arraylist with modified parameters and display grid
		// on screen
		imagetiles = splitImg(imgView, SPLIT_TILES, SPLIT_TILES);
		gridimage.setAdapter(new ImageAdapter(this, imagetiles, emptyIndex));
		gridimage.setNumColumns(SPLIT_TILES);
	}

	/**
	 * shuffles tiles and re-displays grid to screen
	 * 
	 * @param v
	 */
	public void buttonShuffle(View v) {
		shuffleTiles(imagetiles);
		gridimage.setAdapter(new ImageAdapter(this, imagetiles, emptyIndex));
		gridimage.setNumColumns(SPLIT_TILES);
	}

	public void onSelectImage(View v) {

	}

	/**
	 * splits ImageView to arraylist of bitmap tiles
	 * 
	 * @param source
	 * @param xPortions
	 * @param yPortions
	 * @return
	 */
	public ArrayList<Bitmap> splitImg(ImageView source, int xPortions,
			int yPortions) {
		// Getting the scaled bitmap of the source image

		BitmapDrawable mydrawable = (BitmapDrawable) source.getDrawable();

		Bitmap bitmap = mydrawable.getBitmap();

		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);

		String TAG = "bitmapsize";
		Log.d(TAG, bitmap.getWidth() + ", " + bitmap.getHeight());

		int xDimens = 500 / xPortions;
		int yDimens = 500 / yPortions;

		int yCount = 0;

		ArrayList<Bitmap> rectBitmap = new ArrayList<Bitmap>();

		for (int row = 0; row < xPortions; row++) {
			int xCount = 0;
			for (int col = 0; col < yPortions; col++) {
				Bitmap tile = Bitmap.createBitmap(scaledBitmap, xCount, yCount,
						xDimens, yDimens);
				rectBitmap.add(tile);

				xCount += xDimens;
			}
			yCount += yDimens;
		}
		return rectBitmap;

	}

	/**
	 * event listener for when screen is touched
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		int action = event.getAction();
		String DEBUG_TAG = "touch action";

		switch (action) {

		case (MotionEvent.ACTION_DOWN):
			// detected when user presses down on the screen
			initialx = event.getX();
			initialy = event.getY();
			Log.d(DEBUG_TAG, "Action was DOWN: " + initialx + ", " + initialy);
			// if(nextTileEmpty(rect, currentx, currenty - 1)) moveTile();
			return true;

		case (MotionEvent.ACTION_MOVE):
			// Log.d(DEBUG_TAG, "Action was MOVE");
			return true;

		case (MotionEvent.ACTION_UP):
			// detected when user lifts finger from screen
			finalx = event.getX();
			finaly = event.getY();

			// check direction of swipe
			findDirection(initialx, initialy, finalx, finaly);

			// re-draw grid
			gridimage.setAdapter(new ImageAdapter(context, imagetiles,
					emptyIndex));
			gridimage.setNumColumns(SPLIT_TILES);

			checkSolvedPuzzle();
			Log.d(DEBUG_TAG, "Action was UP: " + finalx + ", " + finaly);
			return true;

		case (MotionEvent.ACTION_CANCEL):
			Log.d(DEBUG_TAG, "Action was CANCEL");
			return true;

		case (MotionEvent.ACTION_OUTSIDE):
			Log.d(DEBUG_TAG, "Movement occurred outside bounds "
					+ "of current screen element");
			return true;
		default:
			return true;
		}
	}

	/**
	 * checks which direction the swipe is, then checks if the action is a legal
	 * one (i.e.swipe will not put empty index out of the grid). if action is
	 * legal, then swap the empty index is target index
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void findDirection(float x1, float y1, float x2, float y2) {

		String TAG = "direction";
		int direction;
		if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
			// horizontal swipe
			if (x2 > x1) {
				// right swipe
				Log.d(TAG, "right");
				direction = -1;
				int targetIndex = emptyIndex + direction;
				// ensures empty index is not in the leftmost side of grid
				if (emptyIndex % SPLIT_TILES != 0) {
					Collections.swap(imagetiles, emptyIndex, targetIndex);
					emptyIndex = targetIndex;
				}

			} else {
				// left swipe
				Log.d(TAG, "left");
				direction = 1;
				int targetIndex = emptyIndex + direction;
				// ensures empty index is not on the rightmost side of grid
				if ((emptyIndex + 1) % SPLIT_TILES != 0) {
					Collections.swap(imagetiles, emptyIndex, targetIndex);
					emptyIndex = targetIndex;
				}
			}
		} else {
			// vertical swipe
			if (y2 > y1) {
				// down swipe
				Log.d(TAG, "down");
				direction = -SPLIT_TILES;
				int targetIndex = emptyIndex + direction;
				// ensures empty index is not on the top side of grid
				for (int i = 0; i < SPLIT_TILES; i++) {
					if (emptyIndex == i)
						return;
				}
				Collections.swap(imagetiles, emptyIndex, targetIndex);
				emptyIndex = targetIndex;
				String TAG1 = "target";
				Log.d(TAG1, targetIndex + " " + emptyIndex);

			} else {
				// up swipe
				Log.d(TAG, "up");
				direction = SPLIT_TILES;
				int targetIndex = emptyIndex + direction;
				// ensures empty index is not on bottom of grid
				for (int i = SPLIT_TILES * SPLIT_TILES - SPLIT_TILES; i < SPLIT_TILES
						* SPLIT_TILES; i++) {
					if (emptyIndex == i)
						return;
				}
				Collections.swap(imagetiles, emptyIndex, targetIndex);
				emptyIndex = targetIndex;
			}
		}

	}

	/**
	 * randomly swap tiles x times (where x=number of tiles in the grid)
	 * 
	 * @param tiles
	 */
	public void shuffleTiles(ArrayList<Bitmap> tiles) {

		correctImage = tiles;

		Random rand = new Random();
		for (int i = 0; i < SPLIT_TILES * SPLIT_TILES; i++) {
			int index1 = rand.nextInt(SPLIT_TILES * SPLIT_TILES);
			int index2 = rand.nextInt(SPLIT_TILES * SPLIT_TILES);
			Collections.swap(tiles, index1, index2);
		}
	}

	/**
	 * checks whether puzzle is solved by comparing current arraylist with
	 * correct arraylist
	 */
	public void checkSolvedPuzzle() {
		boolean solved = false;

		if (correctImage == null)
			return;
		String TAG1 = "length";
		Log.d(TAG1, imagetiles.size() + " " + correctImage.size());
		if (imagetiles.size() != correctImage.size()) {
			String TAG = "ArrayBound";
			Log.d(TAG, "Mismatched bounds");
			return;
		}

		for (int i = 0; i < imagetiles.size(); i++) {
			if (imagetiles.get(i) != correctImage.get(i)) {
				String TAG2 = "NotEqual";
				Log.d(TAG2, "notequal");
				return;
			}
		}
		solved = true;
		String TAG = "Solved";
		Log.d(TAG, "Congratulations!");
	}
}
