package com.example.user.cryptoinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class recentnewsdetailsActivity extends AppCompatActivity {

    WebView newweb;
    ProgressBar newbar;
    String newurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recentnewsdetailspage);

        newurl = getIntent().getExtras().getString("url");
        newbar = (ProgressBar)findViewById(R.id.recentnewsLoad);
        newweb = (WebView)findViewById(R.id.recentnewsWebView);
        newweb.getSettings().setBuiltInZoomControls(true);
        newweb.getSettings().setDisplayZoomControls(false);
        newweb.loadUrl(newurl);

        newweb.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView newview, int newprog) {
                if(newprog == 100) {
                    newbar.setVisibility(View.GONE);
                } else {
                    newbar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
