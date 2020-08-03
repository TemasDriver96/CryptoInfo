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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class recentnewsActivity extends AppCompatActivity {

    String SOURCE = "crypto-coins-news";
    String APIKEY = "039d1407cfd64a1ab35c07533996c068";
    ListView recentnewslist;
    ProgressBar newprogressbar;

    ArrayList<HashMap<String, String>> newdata = new ArrayList<HashMap<String, String>>();
    static final String TITLE = "title";
    static final String AUTHOR = "author";
    static final String DESCRIPTION = "description";
    static final String URL = "url";
    static final String URLTOIMAGE = "urlToImage";
    static final String PUBLISHEDDATE = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recentnewspage);
        
        recentnewslist = (ListView)findViewById(R.id.recentnewsList);
        newprogressbar = (ProgressBar)findViewById(R.id.recentnewsBar);
        recentnewslist.setEmptyView(newprogressbar);

        if(netChecker.networkAvailability(getApplicationContext())) {
            newdownloadNews newtask = new newdownloadNews();
            newtask.execute();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    class newdownloadNews extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String y = "";
            String newurlpamars = "";
            y = netChecker.exeGet("https://newsapi.org/v2/everything?sources="+SOURCE+"&apiKey=" +APIKEY, newurlpamars);
            return y;
        }

        @Override
        protected void onPostExecute(String y) {
            if(y.length()>10) {
                try {
                    JSONObject newjsonRes = new JSONObject(y);
                    JSONArray newjsonAr = newjsonRes.optJSONArray("articles");

                    for(int r = 0; r < newjsonAr.length(); r++) {
                        JSONObject newjsonObj = newjsonAr.getJSONObject(r);
                        HashMap<String, String> n = new HashMap<String, String>();

                        n.put(TITLE, newjsonObj.optString(TITLE).toString());
                        n.put(AUTHOR, newjsonObj.optString(AUTHOR).toString());
                        n.put(DESCRIPTION, newjsonObj.optString(DESCRIPTION).toString());
                        n.put(URL, newjsonObj.optString(URL).toString());
                        n.put(URLTOIMAGE, newjsonObj.optString(URLTOIMAGE).toString());
                        n.put(PUBLISHEDDATE, newjsonObj.optString(PUBLISHEDDATE).toString());

                        newdata.add(n);
                    }
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                RecentNews newadapt = new RecentNews(recentnewsActivity.this, newdata);
                recentnewslist.setAdapter(newadapt);
                
                recentnewslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent newintent = new Intent(recentnewsActivity.this, recentnewsdetailsActivity.class);
                        newintent.putExtra("url", newdata.get(+position).get(URL));
                        startActivity(newintent);
                    }
                });

                recentnewslist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        HashMap myData = newdata.get(position);
                        String shareTite = myData.get(TITLE).toString();
                        String shareAuthor = myData.get(AUTHOR).toString();
                        String shareURL = myData.get(URL).toString();

                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBodyText = "Look at this news "+shareTite+" "+shareURL+" by "+shareAuthor;
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
