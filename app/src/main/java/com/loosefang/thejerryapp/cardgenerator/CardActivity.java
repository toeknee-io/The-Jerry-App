package com.loosefang.thejerryapp.cardgenerator;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.loosefang.thejerryapp.R;
import com.loosefang.thejerryapp.adapter.ImageAdapterCard;
import com.loosefang.thejerryapp.javafile.MemeImages;
import com.loosefang.thejerryapp.javafile.TakeCameraImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardActivity extends Activity implements OnItemClickListener {

	private ListView meme_img_listview;
	private static List<MemeImages> memeList;
	public InterstitialAd interstitial;
	static List<MemeImages> images;
	public static LayoutInflater inflater;
	public static Activity activity;

	public static int RESULT_LOAD_IMAGE = 1;
	public static boolean gallery = false;
	public static String cameraImagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jerrycard_activity_layout);
		findViews();
		onStartView();
	}

	private void onStartView() {
		initialiseList();
		ImageAdapterCard adapter = new ImageAdapterCard(this, memeList);
		meme_img_listview.setAdapter(adapter);
		meme_img_listview.setOnItemClickListener(this);
	}

	private void findViews() {
		initialiseList();
		/** Admob Code */
		AdView ad = (AdView) findViewById(R.id.adView);
		AdRequest request = new AdRequest.Builder().build();
		ad.loadAd(request);
		interstitial = new InterstitialAd(getBaseContext());
		interstitial.setAdUnitId(getResources().getString(R.string.admob_id));
		interstitial.loadAd(request);

		meme_img_listview = (ListView) this.findViewById(R.id.meme_img_list);
		// Admob Code

	}

	public static void initialiseList() {
		memeList = new ArrayList<MemeImages>();
		memeList.add(new MemeImages("Select From The Past",
				R.drawable.gallery_ico));
		memeList.add(new MemeImages("New From The Present", R.drawable.camera));
		memeList.add(new MemeImages("In", R.drawable.jerrycard_blue));
		memeList.add(new MemeImages("You", R.drawable.jerrycard_green));
		memeList.add(new MemeImages("Own", R.drawable.jerrycard_pink));
		memeList.add(new MemeImages("Ass", R.drawable.jerrycard_yellow));

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 == 0) {
			openGallery();
		} else if (arg2 == 1) {
			TakeCameraImage take = new TakeCameraImage(this);
			take.openCamera();
		} else {
			this.fireCreateMemeActivity("home", arg2);
		}
	}

	public void fireCreateMemeActivity(String action, int position) {
		Intent i = new Intent(this, CreateMemeActivity.class);
		i.setAction(action);
		if (action.equals("home")) {
			i.putExtra("id", memeList.get(position).getFile());
		}
		startActivity(i);

	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	public void onBackPressed() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
		super.onBackPressed();
	}

	public void openGallery() {
		CardActivity.gallery = true;
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, CardActivity.RESULT_LOAD_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("request", requestCode + "  result  " + resultCode + "  intent  "
				+ data);
		super.onActivityResult(requestCode, resultCode, data);
		if (gallery == true) {
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
					&& null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				Intent i = new Intent(CardActivity.this,
						CreateMemeActivity.class);
				i.setAction("gallery");
				i.putExtra("id", picturePath);
				startActivity(i);
			} else {
				this.finish();
				startActivity(new Intent(this, CardActivity.class));
			}
		} else if (gallery == false) {
			File file = new File(cameraImagePath);
			if (file.exists()) {
				Intent i = new Intent(CardActivity.this,
						CreateMemeActivity.class);
				i.setAction("gallery");
				i.putExtra("id", cameraImagePath);
				startActivity(i);
			} else {
				this.finish();
				startActivity(new Intent(this, CardActivity.class));
			}

		} else {
			this.finish();
			startActivity(new Intent(this, CardActivity.class));
		}
	}

}
