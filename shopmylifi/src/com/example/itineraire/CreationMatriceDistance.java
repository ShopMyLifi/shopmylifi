package com.example.itineraire;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;







import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.shopmylifi.R;
import com.example.shopmylifi.R.id;
import com.example.shopmylifi.R.layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class CreationMatriceDistance {
	
	
	private ProgressDialog pDialog;
	private static String url_get_position = "http://192.3.203.70/getposition.php";


	public void calculmatrice() {
		
	
		String resultstring="";
		
		try {
		 resultstring = new getposition().execute("1","1").get();
			
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//final ListView listview = (ListView) findViewById(R.id.id_display_liste_articles1);
		String[] values = resultstring.split("\"");
		
		ArrayList<String> association = new ArrayList<String>();
		
		for (int i = 0; i < values.length; ++i) {
			if (values[i].compareTo("Id")==0) {
				association.add(values[i+2]);
			}
		}
		
		
		

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			if (values[i].compareTo("type")==0) {
				list.add(values[i+2]);
			}
		}
		
		int[][] matrice=new int[10][10];
		
		for (int i = 0; i< association.size(); ++i) { //value of id
			
			int x = Integer.valueOf(values[12*i+7]);
			int y = Integer.valueOf(values[12*i+11]);
			
			for (int j = 0; j<association.size(); j++) {
				
				int w = Integer.valueOf(values[12*j+7]);
				int z = Integer.valueOf(values[12*j+11]);
				matrice[i][j]=calculdistance(x,y,w,z,76);
				
			}
		}
		
		
		
		
		
		
		Log.d("this is my deep array", "deep arr: " + Arrays.deepToString(matrice));
		
	} 
	
	public int calculdistance(int x,int y,int w,int z, int largeur) {
		int distance=0;
		if (y==z) { //si même hauteur de rayon
			distance = Math.abs(w-x); 
		}
		else {
			if (Math.abs(y-z)==1){ //si même rayon mais face opposés
				if ((x>largeur/2) && (w>largeur/2)) { //si côté droit magasin
				int trajet1=Math.abs(69-x)+2+Math.abs(69-w);
				int trajet2=Math.abs(40-x)+2+Math.abs(40-w);
				distance=Math.min(trajet1, trajet2);
				} else {
					if ((x<largeur/2) && (w<largeur/2)) { //côté gauche du magasin
						int trajet3=Math.abs(35-x)+2+Math.abs(35-w);
						int trajet4=Math.abs(5-x)+2+Math.abs(5-w);
						distance=Math.min(trajet3, trajet4);
					} else { //si du côté gauche au côté droit ou inverse
						distance = Math.abs(z-x)+2;
					}
					
				}
			} else {
				if (((x<largeur/2) && (w>largeur/2)) || ((x>largeur/2)&&(w<largeur/2))) { //si pas même côté et pas même hauteur
				distance = Math.abs(w-x); 
				distance = distance+Math.abs(y-z);
				} 
				if ((x>largeur/2) && (w>largeur/2)) { //si même côté magasin mais pas même hauteur côté droit
					int trajet5=Math.abs(69-x)+Math.abs(y-z)+Math.abs(69-w);
					int trajet6=Math.abs(40-x)+Math.abs(y-z)+Math.abs(40-w);
					distance=Math.min(trajet5, trajet6);
				}
				if ((x<largeur/2)&&(w<largeur/2)){ //même côté magasin côté gauche 
					int trajet7=Math.abs(35-x)+Math.abs(y-z)+Math.abs(35-w);
					int trajet8=Math.abs(5-x)+Math.abs(y-z)+Math.abs(5-w);
					distance=Math.min(trajet7, trajet8);	
				}
			}
		}
		
		return distance;
	}
	
	
	
	class getposition extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			pDialog = new ProgressDialog(CreationMatriceDistance.this);
//			pDialog.setMessage("Chargement de la liste en cours...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(false);
//			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			InputStream is = null;
			String result = "";
			String returnString = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_liste", args[0]));
			nameValuePairs.add(new BasicNameValuePair("id_client", args[1]));

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url_get_position);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();

			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
			}
			// Parse les donnï¿½es JSON
			if (result == "null") {
				returnString = "pas de reponse";
				return returnString;
			} else {
				returnString = result;
			}

			return returnString;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread

		}
	}
	

}
