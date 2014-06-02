package com.example.shopmylifi;

import com.ortiz.touch.TouchImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class CarteActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_carte);

		final ImageButton homeButton = (ImageButton) findViewById(R.id.carte_button_home);
		homeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CarteActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		final ImageButton reglagesButton = (ImageButton) findViewById(R.id.carte_button_settings);
		reglagesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CarteActivity.this,
						ReglagesActivity.class);
				startActivity(intent);

			}
		});
		
	}
	

}
