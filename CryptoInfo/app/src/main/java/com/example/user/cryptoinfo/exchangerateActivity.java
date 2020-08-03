package com.example.user.cryptoinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class exchangerateActivity extends AppCompatActivity {

    String APIKEY = "2D332684-441F-48D3-894D-59135DABB479";
    ListView exchangeratelist;
    ProgressBar exchangeratebar;
    EditText exchangeratesearch;

    ArrayList<HashMap<String, String>> exdata = new ArrayList<HashMap<String, String>>();
    static final String COIN = "asset_id_quote";
    static final String RATE = "rate";
    static final String MYPREFERENCES = "myprefs";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchangeratepage);

        exchangeratesearch = (EditText) findViewById(R.id.exchangerateSearch);
        exchangeratelist = (ListView)findViewById(R.id.exchangerateList);
        exchangeratebar = (ProgressBar)findViewById(R.id.exchangerateBar);
        exchangeratelist.setEmptyView(exchangeratebar);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        //this checks if the internet is available
        if(netChecker.networkAvailability(getApplicationContext())) {
            newdownloadExchange extask = new newdownloadExchange();
            extask.execute();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    class newdownloadExchange extends AsyncTask<String, Void, String> {

        //Invokes UI thread before the task is executed showing a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //performs long background computations
        protected String doInBackground(String... args) {
            String f = "";
            String exurlpamars = "";
            f = netChecker.exeGet("https://rest.coinapi.io/v1/exchangerate/GBP?apikey=" +APIKEY, exurlpamars);
            return f;
        }

        //all of the background computation here as parameters
        @Override
        protected void onPostExecute(String f) {
            if(f.length()>10) {
                try {
                    JSONObject exjsonRes = new JSONObject(f);
                    JSONArray exjsonAr = exjsonRes.optJSONArray("rates");

                    for(int a = 0; a < exjsonAr.length(); a++) {
                        JSONObject exjsonObj = exjsonAr.getJSONObject(a);
                        HashMap<String, String> o = new HashMap<String, String>();

                        o.put(COIN, exjsonObj.optString(COIN).toString());
                        o.put(RATE, exjsonObj.optString(RATE).toString());

                        exdata.add(o);
                    }
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                final ExchangeRate exadapt = new ExchangeRate(exchangerateActivity.this, exdata);
                exchangeratelist.setAdapter(exadapt);

                exchangeratelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap myData = exdata.get(position);
                        String shareCoin = myData.get(COIN).toString();
                        String shareRate = myData.get(RATE).toString();

                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBodyText = "Look at the current rate of "+shareCoin+" compared to £1.00- " +shareRate;
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                        startActivity(Intent.createChooser(intent, "Choose sharing method"));
                    }
                });

                //this listener will look at saving data using shared preferences
                exchangeratelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap myData = exdata.get(position);
                        String saveCoin = myData.get(COIN).toString();
                        String saveRate = myData.get(RATE).toString();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("coin", saveCoin);
                        editor.putString("rate", saveRate);
                        editor.commit();

                        Toast.makeText(exchangerateActivity.this,"Exchange Rate Information Saved", Toast.LENGTH_LONG).show();
                        return true;
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "No Rates Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}