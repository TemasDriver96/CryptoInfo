package com.example.user.cryptoinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class topnewsdetailsActivity extends AppCompatActivity {

    WebView web;
    ProgressBar bar;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topnewsdetailspage);

        url = getIntent().getExtras().getString("url");
        bar = (ProgressBar)findViewById(R.id.topnewsLoad);
        web = (WebView)findViewById(R.id.topnewsWebView);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDisplayZoomControls(false);
        web.loadUrl(url);

        web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int prog) {
                if(prog == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    bar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
