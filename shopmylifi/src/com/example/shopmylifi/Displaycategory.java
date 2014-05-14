package com.example.shopmylifi;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import java.util.concurrent.ExecutionException;

import android.view.Window;

public class Displaycategory extends Activity {

	private ProgressDialog pDialog;
	// url to get all products list
	private static String url_all_products = "http://192.3.203.70/category.php";
	private static String url_add_products = "http://192.3.203.70/addproduct.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_liste_produits1);

		final ImageButton homeButton = (ImageButton) findViewById(R.id.liste_articles1_button_home);
		homeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Displaycategory.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		final ImageButton reglagesButton = (ImageButton) findViewById(R.id.liste_articles1_button_settings);
		reglagesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Displaycategory.this,
						ReglagesActivity.class);
				startActivity(intent);
			}
		});

		int position;
		position = getIntent().getExtras().getInt("position");
		position++;
		String strposition;
		strposition = String.valueOf(position);

		String returnstring = "";
		try {
			returnstring = new LoadAllProducts().execute(strposition).get();

		} catch (InterruptedException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final ListView listview = (ListView) findViewById(R.id.id_display_liste_articles1);

		final String[] values = returnstring.split("\"");

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			if (values[i].compareTo("Nom") == 0) {
				list.add(values[i + 2]);
			}
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				String textselected = (String) listview
						.getItemAtPosition(position);

				String ItemId;
				ItemId = "null";
				for (int i = 0; i < values.length; ++i) {
					if (values[i].compareTo(textselected) == 0) {
						ItemId = values[i + 4];
					}
				}
				String resultaddproduct;

				try {
					resultaddproduct = new Addproduct().execute("1", "1",
							ItemId).get();

				} catch (InterruptedException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String textaffichage = textselected + " ajouté la liste";
				Toast toast = Toast.makeText(getApplicationContext(),
						textaffichage, Toast.LENGTH_SHORT);
				toast.show();

			}
		});

	}

	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Displaycategory.this);
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
			InputStream is = null;
			String result = "";
			String returnString = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_type", args[0]));

			try {
				HttpClient httpclient = new DefaultHttpClient();

				String paramsString = URLEncodedUtils.format(nameValuePairs,
						"UTF-8");
				HttpGet httpGet = new HttpGet(url_all_products + "?"
						+ paramsString);
				HttpResponse response = httpclient.execute(httpGet);
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
			try {
				if (result == "null") {
					returnString = "";
					return returnString;
				}
				JSONArray jArray = new JSONArray(result);

				for (int i = 0; i < jArray.length(); i++) {

					JSONObject json_data = jArray.getJSONObject(i);
					// Affichage ID_ville et Nom_ville dans le LogCat
					Log.i("log_tag", "ID: " + json_data.getInt("Id")
							+ ", Nom: " + json_data.getString("Nom"));
					// Rï¿½sultats de la requï¿½te
					returnString += "\n\t" + jArray.getJSONObject(i);
				}
			} catch (JSONException e) {
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

	class Addproduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Displaycategory.this);
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
			InputStream is = null;
			String result = "";
			String returnString = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_liste", args[0]));
			nameValuePairs.add(new BasicNameValuePair("id_client", args[1]));
			nameValuePairs.add(new BasicNameValuePair("id_produit", args[2]));

			try {
				HttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new HttpPost(url_add_products);
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
				returnString = "";
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
