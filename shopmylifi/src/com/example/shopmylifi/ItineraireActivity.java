package com.example.shopmylifi;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.itineraire.CreationMatriceDistance;
import com.example.itineraire.CalculItineraire;

public class ItineraireActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_itineraire);

		final ImageButton homeButton = (ImageButton) findViewById(R.id.itineraire_button_home);
		homeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItineraireActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		final ImageButton reglagesButton = (ImageButton) findViewById(R.id.itineraire_button_settings);
		reglagesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItineraireActivity.this,
						ReglagesActivity.class);
				startActivity(intent);

			}
		});

		CreationMatriceDistance creationMatrice = new CreationMatriceDistance();

		int[][] matriceD;
		matriceD = creationMatrice.calculmatrice();

		int[] iti = new int[matriceD.length];
		CalculItineraire.computeIti(matriceD, iti);

		if (isPackageExisted("com.example.lifilibrarydemo_image")) {
			PackageManager pm = getPackageManager();
			Intent intent = pm
					.getLaunchIntentForPackage("com.example.lifilibrarydemo_image");
			startActivity(intent);
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), "Application OLEDCOMM non installée", Toast.LENGTH_LONG);
			toast.show();

		}
	}

	public boolean isPackageExisted(String targetPackage) {
		List<ApplicationInfo> packages;
		PackageManager pm;
		pm = getPackageManager();
		packages = pm.getInstalledApplications(0);
		for (ApplicationInfo packageInfo : packages) {
			if (packageInfo.packageName.equals(targetPackage))
				return true;
		}
		return false;
	}
}
