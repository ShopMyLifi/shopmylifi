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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

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

import com.example.shopmylifi.Displaycategory.Addproduct;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.view.Window;

public class DisplayListeCourse extends Activity {

	private ProgressDialog pDialog;
	// url to get all products list
	private static String url_all_products = "http://192.3.203.70/clientlist.php";
	private static String url_empty_list = "http://192.3.203.70/emptylist.php";
	private static String url_delete_product = "http://192.3.203.70/deleteproduct.php";

	private String resultstring;

	public String getString() {
		return resultstring;
	}

	public void setString(String resultstring) {
		this.resultstring = resultstring;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display_liste_course);

		final ImageButton homeButton = (ImageButton) findViewById(R.id.liste_course_button_home);
		homeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayListeCourse.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		final ImageButton ArticlesMagasinButton = (ImageButton) findViewById(R.id.liste_course_button_ad);
		ArticlesMagasinButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayListeCourse.this,
						ListeProduits1Activity.class);
				startActivityForResult(intent, 100);
			}
		});

		final ImageButton reglagesButton = (ImageButton) findViewById(R.id.liste_course_button_settings);
		reglagesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayListeCourse.this,
						ReglagesActivity.class);
				startActivity(intent);

			}
		});

		String resultat = "";
		try {
			resultat = new LoadAllProducts().execute().get();

		} catch (InterruptedException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setString(resultat);

		final ListView listview = (ListView) findViewById(R.id.id_display_liste_course);
		registerForContextMenu(listview);

		final ArrayList<String> list = new ArrayList<String>();

		if (resultat != "") {
			String[] values = resultat.split("\"");
			for (int i = 0; i < values.length; ++i) {
				if (values[i].compareTo("Nom") == 0) {
					list.add(values[i + 2]);
				}
			}
		} else {
			list.add("Votre liste est vide");
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
			pDialog = new ProgressDialog(DisplayListeCourse.this);
			pDialog.setMessage("Chargement de la liste en cours...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			InputStream is = null;
			String result = "";
			String returnString = "";
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id", "type"));

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url_all_products);
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
			// Parse les donnees JSON
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
							+ ", Type: " + json_data.getString("Nom"));
					// Resultats de la requete
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

	class Deletelist extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DisplayListeCourse.this);
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
				HttpPost httppost = new HttpPost(url_empty_list);
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

	class Deleteproduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DisplayListeCourse.this);
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
			nameValuePairs.add(new BasicNameValuePair("Id", args[2]));

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url_delete_product);
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode != 100) {
			// if result code 100 is received
			// means user edited/deleted product
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.id_display_liste_course) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu_listecourses_longclick, menu);

		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.option_listeCourses_deleteArticle:
			// supprimer article

			int pos = (int) info.id;
			String itemdelete = String.valueOf(3 + pos * 8);
			String resultdeletelist = "";

			try {
				resultdeletelist = new Deleteproduct().execute("1", "1",
						itemdelete).get();

			} catch (InterruptedException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.option_listeCourses_deleteListe:
			// supprimer liste
			String resultdeletelist2;
			try {
				resultdeletelist2 = new Deletelist().execute("1", "1").get();

			} catch (InterruptedException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = getIntent();
			finish();
			startActivity(intent);

			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);

	}
}
