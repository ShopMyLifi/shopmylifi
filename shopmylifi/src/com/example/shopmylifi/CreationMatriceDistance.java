package com.example.shopmylifi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;





import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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


public class CreationMatriceDistance extends Activity {
	
	
	private ProgressDialog pDialog;
	private static String url_get_position = "http://192.3.203.70/getposition.php";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_liste_produits1);

	
		

		
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
		
		Toast toast = Toast.makeText(getApplicationContext(),
				resultstring, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
		final ListView listview = (ListView) findViewById(R.id.id_display_liste_articles1);
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
		
		int testdistance = calculdistance(43,6,46,7,76);
		String testdist = String.valueOf(testdistance);
		
		Toast toast2 = Toast.makeText(getApplicationContext(),
				testdist, Toast.LENGTH_SHORT);
		toast2.setGravity(Gravity.BOTTOM, 0, 0);
		toast2.show();
		
	} 
	
	public int calculdistance(int x,int y,int w,int z, int largeur) {
		int distance=0;
		if (y==z) {
			distance = Math.abs(w-x); 
		}
		else {
			if (Math.abs(y-z)==1){
				int trajet1=Math.abs(69-x)+2+Math.abs(69-w);
				int trajet2=Math.abs(40-x)+2+Math.abs(40-w);
				distance=Math.min(trajet1, trajet2);			
			}
			
			if (((x<largeur/2) && (w>largeur/2)) || ((x>largeur/2)&&(w<largeur/2))) {
			distance = Math.abs(w-x); 
			distance = distance+Math.abs(y-z);
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
			pDialog = new ProgressDialog(CreationMatriceDistance.this);
			pDialog.setMessage("Chargement de la liste en cours...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
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
			// Parse les donn�es JSON
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
