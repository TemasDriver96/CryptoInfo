package com.example.user.cryptoinfo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class topnewsActivity extends AppCompatActivity {

    String SOURCE = "crypto-coins-news";
    String APIKEY = "039d1407cfd64a1ab35c07533996c068";
    ListView topnewslist;
    ProgressBar progressbar;

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String URL = "url";
    static final String URLTOIMAGE = "urlToImage";
    static final String PUBLISHEDDATE = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topnewspage);

        topnewslist = (ListView) findViewById(R.id.topnewsList);
        progressbar = (ProgressBar)findViewById(R.id.topnewsBar);
        topnewslist.setEmptyView(progressbar);

        if(netChecker.networkAvailability(getApplicationContext())) {
            downloadNews newsTask = new downloadNews();
            newsTask.execute();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    class downloadNews extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        protected String doInBackground(String... args) {
            String x = "";
            String urlpamars = "";
            x = netChecker.exeGet("https://newsapi.org/v2/top-headlines?sources="+SOURCE+"&apiKey=" +APIKEY, urlpamars);
            return x;
        }

        @Override
        protected void onPostExecute(String x) {
            if(x.length()>10) {
                try {
                    JSONObject jsonRes = new JSONObject(x);
                    JSONArray jsonAr = jsonRes.optJSONArray("articles");

                    for ( int q = 0; q < jsonAr.length(); q++) {
                        JSONObject jsonObj = jsonAr.getJSONObject(q);
                        HashMap<String, String> m = new HashMap<String, String>();

                        m.put(TITLE, jsonObj.optString(TITLE).toString());
                        m.put(DESCRIPTION, jsonObj.optString(DESCRIPTION).toString());
                        m.put(URL, jsonObj.optString(URL).toString());
                        m.put(URLTOIMAGE, jsonObj.optString(URLTOIMAGE).toString());
                        m.put(PUBLISHEDDATE, jsonObj.optString(PUBLISHEDDATE).toString());

                        data.add(m);
                    }
                } catch(JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                TopNews adapt = new TopNews(topnewsActivity.this, data);
                topnewslist.setAdapter(adapt);

                topnewslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent intent = new Intent(topnewsActivity.this, topnewsdetailsActivity.class);
                        intent.putExtra("url", data.get(+position).get(URL));
                        startActivity(intent);
                    }
                });

                topnewslist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        HashMap myData = data.get(position);
                        String shareTite = myData.get(TITLE).toString();
                        String shareURL = myData.get(URL).toString();

                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBodyText = "Look at this news " +shareTite+" "+shareURL;
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                        startActivity(Intent.createChooser(intent, "Choose sharing method"));

                        return true;
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "No News Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
