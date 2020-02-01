package com.barisalgul.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tvUsd, tvTry, tvGbp, tvJpy, tvBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        tvUsd = findViewById(R.id.tvUSD);
        tvTry = findViewById(R.id.tvTRY);
        tvGbp = findViewById(R.id.tvGBP);
        tvJpy = findViewById(R.id.tvJPY);
        tvBase = findViewById(R.id.tvBase);
    }

    public void getRates(View view) {

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=bcb9f1663e9441960eb755df7a3a9544";
            downloadData.execute(url);

        } catch (Exception e) {

        }

    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0) {
                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return result;
            } catch (Exception e) {

                return e.getLocalizedMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                tvBase.setText("Base Currency: " + jsonObject.getString("base"));

                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);
                tvTry.setText("1 " + jsonObject.getString("base") + ": " + jsonObject1.getString("TRY") + " TRY");
                tvUsd.setText("1 " + jsonObject.getString("base") + ": " + jsonObject1.getString("USD") + " USD");
                tvGbp.setText("1 " + jsonObject.getString("base") + ": " + jsonObject1.getString("GBP") + " GBP");
                tvJpy.setText("1 " + jsonObject.getString("base") + ": " + jsonObject1.getString("JPY") + " JPY");



            } catch (Exception e) {

            }
        }
    }
}
