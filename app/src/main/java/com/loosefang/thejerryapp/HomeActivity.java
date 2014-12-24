package com.loosefang.thejerryapp;

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
import com.loosefang.thejerryapp.adapter.ImageAdapter;
import com.loosefang.thejerryapp.cardgenerator.CardActivity;
import com.loosefang.thejerryapp.cardgenerator.CreateMemeActivity;
import com.loosefang.thejerryapp.translator.TranslatorActivity;
import com.loosefang.thejerryapp.javafile.MainActivityList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity implements OnItemClickListener {

	private ListView activity_listview;
	private static List<MainActivityList> activityList;

	public InterstitialAd interstitial;
	public static List<MainActivityList> images;
	public static LayoutInflater inflater;
	public static Activity activity;
	
	public static int RESULT_LOAD_IMAGE = 1;
	public static boolean gallery = false;
	public static String cameraImagePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialiseList();
		setContentView(R.layout.main_activity_layout);
		findViews();
		onStartView();
	}
	
	private void onStartView() {
		initialiseList();
		ImageAdapter adapter = new ImageAdapter(this,activityList);
		activity_listview.setAdapter(adapter);
		activity_listview.setOnItemClickListener(this);
		
	}
	
	private void findViews(){
		initialiseList();
		/**Admob Code*/
		 AdView ad=(AdView)findViewById(R.id.adView);
		 AdRequest request = new AdRequest.Builder().build();
            ad.loadAd(request);
	        interstitial=new InterstitialAd(getBaseContext());
            interstitial.setAdUnitId("pub-2817753321940136");
	        interstitial.loadAd(request);
		
		activity_listview=(ListView)this.findViewById(R.id.activity_list);
		// Admob Code		
		
	}
	public static List<MainActivityList> initialiseList() {
		activityList=new ArrayList<MainActivityList>();
		activityList.add(new MainActivityList("Jerry Card Generator",R.drawable.jerrycard_blue));
		activityList.add(new MainActivityList("Jerry Translator", R.drawable.jerrycard_green));
		return activityList;
				
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg2==0){		
			openCardGenerator();
		}		
		else if(arg2 == 1){		
			openTranslator();
		}else{
			this.fireCardActivity("home", arg2);			
		}	
	}
	
	public void fireCardActivity(String action,int position){
		Intent i=new Intent(this, CreateMemeActivity.class);
		i.setAction(action);
/*		
		if(action.equals("home")){
			i.putExtra("id", activityList.get(position));
		}
*/		
		startActivity(i);
		
	}
		
	@Override
	protected void onStart() {
		
		super.onStart();
	}
	@Override
	public void onBackPressed() {
		if(interstitial.isLoaded()){
			interstitial.show();
		}
		super.onBackPressed();
	}
	
	public void openCardGenerator() {
	 	 Intent intent = new Intent(this, CardActivity.class);
    	 startActivity(intent);
	}
	
	public void openTranslator() {
	 	 Intent intent = new Intent(this, TranslatorActivity.class);
	 	 startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("request", requestCode+"  result  "+resultCode+"  intent  "+data);
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
				Intent i = new Intent(HomeActivity.this, CreateMemeActivity.class);
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
				Intent i = new Intent(HomeActivity.this, CreateMemeActivity.class);
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