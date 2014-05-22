package com.example.shopmylifi;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.itineraire.CreationMatriceDistance;
import com.example.itineraire.CalculItineraire;

public class ItineraireActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_itineraire);

		CreationMatriceDistance creationMatrice = new CreationMatriceDistance();

		int[][] matriceD;
		matriceD = creationMatrice.calculmatrice();
		
		int[] iti = new int[matriceD.length];
		CalculItineraire.computeIti(matriceD, iti);

		Toast.makeText(getApplicationContext(), (Arrays.toString(iti)), Toast.LENGTH_SHORT).show();
	}

}
