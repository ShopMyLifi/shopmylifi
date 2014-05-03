package com.example.shopmylifi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.shopmylifi.DisplayListeCourse.LoadAllProducts;

import android.view.Window;

public class ListeProduits1Activity extends Activity {

	private ProgressDialog pDialog;
	
    private static String url_all_products = "http://192.3.203.70/mysql.php";  // url to get all products list
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_liste_produits1);

		final ImageButton homeButton = (ImageButton) findViewById(R.id.liste_articles1_button_home);
		homeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ListeProduits1Activity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		final ImageButton reglagesButton = (ImageButton) findViewById(R.id.liste_articles1_button_settings);
		reglagesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ListeProduits1Activity.this,
						ReglagesActivity.class);
				startActivity(intent);
			}
		});

		
		String resultstring="";
		
		try {
		 resultstring = new LoadAllProducts().execute().get();
			
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		final ListView listview = (ListView) findViewById(R.id.id_display_liste_articles1);
		String[] values = resultstring.split("\"");
		

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			if (values[i].compareTo("type")==0) {
				list.add(values[i+2]);
			}
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

	}
	
	class LoadAllProducts extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListeProduits1Activity.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            InputStream is = null;
            String result = "";
            String returnString = "";
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		nameValuePairs.add(new BasicNameValuePair("id","type"));
           
            try{
            	        HttpClient httpclient = new DefaultHttpClient();
            		    HttpPost httppost = new HttpPost(url_all_products);
            		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            		    HttpResponse response = httpclient.execute(httppost);
            	        HttpEntity entity = response.getEntity();
            		    is = entity.getContent();
            		 
            	        }catch(Exception e){
            	            Log.e("log_tag", "Error in http connection " + e.toString());
            	    }
            
            try{
    			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
    			StringBuilder sb = new StringBuilder();
    			String line = null;
    			while ((line = reader.readLine()) != null) {
    				sb.append(line + "\n");
    			}
    			is.close();
    			result=sb.toString();
    		}catch(Exception e){
    			Log.e("log_tag", "Error converting result " + e.toString());
    		}
    		// Parse les donn�es JSON
    		try{
    			JSONArray jArray = new JSONArray(result);
    			for(int i=0;i<jArray.length();i++){
    				JSONObject json_data = jArray.getJSONObject(i);
    				// Affichage ID_ville et Nom_ville dans le LogCat
    				Log.i("log_tag","ID: "+json_data.getInt("Id")+
    						", Type: "+json_data.getString("type")
    				);
    				// R�sultats de la requ�te
    				returnString += "\n\t" + jArray.getJSONObject(i); 
    			}
    		}catch(JSONException e){
    			Log.e("log_tag", "Error parsing data " + e.toString());
    		}
    		
    		return returnString; 
 
            
               
        }
        
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
                
 
        }
 }

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

}
