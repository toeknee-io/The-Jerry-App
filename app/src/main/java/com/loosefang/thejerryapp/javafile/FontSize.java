package com.loosefang.thejerryapp.javafile;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.loosefang.thejerryapp.R;
import com.loosefang.thejerryapp.cardgenerator.CreateMemeActivity;
import com.loosefang.thejerryapp.views.HandleMemeWriteBounds;
import com.loosefang.thejerryapp.views.PaintStyle;

public class FontSize extends Dialog {
	private int currentSize = 20;
	private SeekBar setSize;
	Activity activity;

	public FontSize(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.loosefang.thejerryapp.R.layout.font_size);

		setSize = (SeekBar) findViewById(R.id.seekbar);
		setSize.setMax(75);
		setSize.setProgress(currentSize);
		setSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {					
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				PaintStyle.FONT_SIZE =progress;
				setFontSize();
			}
		});

	}

	private void setFontSize() {
		CreateMemeActivity.handleMemeWriteBounds = new HandleMemeWriteBounds(
				CreateMemeActivity.meme_image, CreateMemeActivity.font_style);
		CreateMemeActivity.ALTERED_BITMAP = CreateMemeActivity.handleMemeWriteBounds
				.generateImage(CreateMemeActivity.TOP_TEXT);
//						CreateMemeActivity.BOTTOM_TEXT);
		CreateMemeActivity.meme_generated_img
				.setImageBitmap(CreateMemeActivity.ALTERED_BITMAP);
	}
}
