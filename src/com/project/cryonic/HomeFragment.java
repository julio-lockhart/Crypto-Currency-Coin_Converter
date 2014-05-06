/***
 * Main fragment which displays to the user all of the UI such as
 * current crypto coin, the quantity, the current currency, the exchange
 * rate. As well as other misc. info about that specific coin
 */

package com.project.cryonic;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class HomeFragment extends Fragment {

	// Variable Instances
	private GlobalRates[] gr;
	private CoinInfo[] ci;
	private static int drawerPosition;

	private TextView finalPrice;
	private TextView coinName;
	private EditText coinQtyET;
	private Spinner currencySpinner;

	private ImageView imageView;
	private TextView symbolView;
	private TextView algorithmView;
	private TextView difficultyView;
	private TextView rewardView;
	private TextView networkRateView;
	private TextView avgHashView;
	private TextView exchangeView;
	private TextView minBlockTimeView;
	private TypedArray navMenuIcons;

	private Double currencyRateBTC = -99.00;
	private Double coinRateBTC = -99.00;
	private int coinQty;
	String currentSpinner;

	Double rate;

	public HomeFragment() {
	}

	/**
	 * Every time user selects a new coin, this fragment gets populated
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the XML
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		// Get coin user selected from nav drawer
		Bundle bundle = this.getArguments();
		drawerPosition = bundle.getInt("position");

		// Initialize variables
		finalPrice = (TextView) rootView.findViewById(R.id.worthInCurrency);
		coinQtyET = (EditText) rootView.findViewById(R.id.coinQty);
		coinName = (TextView) rootView.findViewById(R.id.coinName);
		currencySpinner = (Spinner) rootView.findViewById(R.id.currencySpinner);
		imageView = (ImageView) rootView.findViewById(R.id.image);
		symbolView = (TextView) rootView.findViewById(R.id.abbrevInfo);
		algorithmView = (TextView) rootView.findViewById(R.id.algorithmInfo);
		difficultyView = (TextView) rootView.findViewById(R.id.difficultyInfo);
		rewardView = (TextView) rootView.findViewById(R.id.rewardInfo);
		networkRateView = (TextView) rootView
				.findViewById(R.id.networkhashrateInfo);
		avgHashView = (TextView) rootView.findViewById(R.id.avgHashInfo);
		exchangeView = (TextView) rootView.findViewById(R.id.exchangeInfo);
		minBlockTimeView = (TextView) rootView.findViewById(R.id.minBlockTimeInfo);

		// ASYNC task that gets all info about coins from API
		BitRateFetcher rateFetcher = new BitRateFetcher();
		rateFetcher.execute();

		// ASYNC task that gets all info about currency from API
		CoinFecther coinFecther = new CoinFecther();
		coinFecther.execute();

		// Text watcher that keeps track of amount of coins user wants to
		// convert and then updates the amount
		coinQtyET.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// Check to see if API populated the info yet, if so upodate
				if (currencyRateBTC != -99.00 && coinRateBTC != -99.00
						&& coinQtyET.length() > 0)
					updateAmount(currencyRateBTC, coinRateBTC);
				else
					finalPrice.setText("0.00");
			}
		});

		return rootView;
	}

	// Get currency info from API
	private class BitRateFetcher extends AsyncTask<Void, Void, GlobalRates[]> {
		private static final String RATE_TAG = "BitRateFetcher";
		public String BIT_PAY_SERVER = "https://bitpay.com/api/rates";

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Please Wait... Downloading Information");
			dialog.show();
		}

		@Override
		protected GlobalRates[] doInBackground(Void... params) {

			try {
				// Create an HTTP client
				HttpClient client = new DefaultHttpClient();
				HttpGet getBitRates = new HttpGet(BIT_PAY_SERVER);

				// Perform the request and check the status code
				HttpResponse bitRatesResponse = client.execute(getBitRates);

				StatusLine bitRatesStatus = bitRatesResponse.getStatusLine();

				if (bitRatesStatus.getStatusCode() == 200) {
					HttpEntity entity = bitRatesResponse.getEntity();
					InputStream content = entity.getContent();

					try {
						// Read the server response and attempt to parse it as
						// JSON
						Reader reader = new InputStreamReader(content);
						Gson gson = new Gson();

						gr = gson.fromJson(reader, GlobalRates[].class);

						content.close();
						entity.consumeContent();
					} catch (Exception ex) {
						Log.e(RATE_TAG, "Failed to parse JSON due to: " + ex);
						Toast.makeText(getActivity(),
								"Error Loading Data. Please Try Again Later",
								Toast.LENGTH_LONG).show();
					}

				} else {
					Log.e(RATE_TAG, "Server responded with status code: "
							+ bitRatesStatus.getStatusCode());
					Toast.makeText(
							getActivity(),
							"There is an error on the server side. Please try again later",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception ex) {
				Log.e(RATE_TAG, "Failed to send HTTP POST request due to: " + ex);
				// failedLoadingPosts();
			}

			return gr;
		}

		// Once info gathered, populate spinner with it
		@Override
		protected void onPostExecute(final GlobalRates[] rates) {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			// Populate spinner
			ArrayList<String> coinNames = new ArrayList<String>();
			for (int i = 0; i < gr.length; i++) {
				coinNames.add(gr[i].getName());
			}

			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(),
					android.R.layout.simple_spinner_dropdown_item, coinNames);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			currencySpinner.setAdapter(adapter);

			currencySpinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							/*
							 * currentSpinner = adapter.getItem(position)
							 * .toString();
							 */
							currencyRateBTC = gr[position].getRate();

							if (coinRateBTC != -99.00 && coinQtyET.length() > 0)
								updateAmount(coinRateBTC, currencyRateBTC);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});
		}
	}

	// Get coin info from API
	private class CoinFecther extends AsyncTask<Void, Void, CoinInfo[]> {
		private static final String COIN_TAG = "CoinFetcher";
		public String BIT_PAY_SERVER = "http://www.coinchoose.com/api.php?base=BTC";

		@Override
		protected CoinInfo[] doInBackground(Void... params) {

			try {
				// Create an HTTP client
				HttpClient client = new DefaultHttpClient();
				HttpGet getBitRates = new HttpGet(BIT_PAY_SERVER);

				// Perform the request and check the status code
				HttpResponse bitRatesResponse = client.execute(getBitRates);

				StatusLine bitRatesStatus = bitRatesResponse.getStatusLine();

				if (bitRatesStatus.getStatusCode() == 200) {
					HttpEntity entity = bitRatesResponse.getEntity();
					InputStream content = entity.getContent();

					try {
						// Read the server response and attempt to parse it as
						// JSON
						Reader reader = new InputStreamReader(content);
						Gson gson = new Gson();

						ci = gson.fromJson(reader, CoinInfo[].class);

						content.close();
						entity.consumeContent();
					} catch (Exception ex) {
						Log.e(COIN_TAG, "Failed to parse JSON due to: " + ex);
						Toast.makeText(getActivity(),
								"Error Loading Data. Please Try Again Later",
								Toast.LENGTH_LONG).show();
					}

				} else {
					Log.e(COIN_TAG, "Server responded with status code: "
							+ bitRatesStatus.getStatusCode());
					Toast.makeText(
							getActivity(),
							"There is an error on the server side. Please try again later",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception ex) {
				Log.e(COIN_TAG, "Failed to send HTTP POST request due to: " + ex);
			}
			return ci;
		}

		// After info gathered, populate nav drawer
		@Override
		protected void onPostExecute(final CoinInfo[] coinInfo) {

			navMenuIcons = getResources().obtainTypedArray(
					R.array.nav_drawer_icons);

			Drawable drawable = getResources().getDrawable(
					navMenuIcons.getResourceId(drawerPosition, -1));

			navMenuIcons.recycle();
			coinName.setText(coinInfo[drawerPosition].getName());
			imageView.setImageDrawable(drawable);
			symbolView.setText(coinInfo[drawerPosition].getSymbol());
			algorithmView.setText(coinInfo[drawerPosition].getAlgo());
			difficultyView.setText(coinInfo[drawerPosition].getDifficulty());
			rewardView.setText(coinInfo[drawerPosition].getReward());
			minBlockTimeView.setText(coinInfo[drawerPosition].getMinBlockTime());
			networkRateView.setText(coinInfo[drawerPosition].getNetworkhashrate());
			avgHashView.setText(coinInfo[drawerPosition].getAvgHash());
			
			if(coinInfo[drawerPosition].getExchange().equals("")) {
				exchangeView.setText("Not Available");
			} else {
				exchangeView.setText(coinInfo[drawerPosition].getExchange());
			}
			
			coinRateBTC = Double.parseDouble(coinInfo[drawerPosition]
					.getPrice());

			if (currencyRateBTC != -99.00 && coinQtyET.length() > 0)
				updateAmount(coinRateBTC, currencyRateBTC);
			else
				finalPrice.setText("0.00");
		}
	}

	/**
	 * Function that updates worth of coin
	 * @param coinRate		     How much coin is worth per bitcoin
	 * @param currencyRateBTC2   How much coin is worth in currency
	 */
	public void updateAmount(double coinRate, double currencyRateBTC2) {

		double finalVal = 0;

		// Gets the amount of coins from the edit text
		coinQty = Integer.parseInt(coinQtyET.getText().toString());
		finalVal = coinRate * currencyRateBTC2 * coinQty;

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(5);
		finalPrice.setText("" + nf.format(finalVal));

	}
}
