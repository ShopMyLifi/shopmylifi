package com.example.shopmylifi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ItineraireActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_liste_produits1);
		
	}
	
	
	

}
