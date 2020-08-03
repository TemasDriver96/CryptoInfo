package com.example.user.cryptoinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class bookmarksActivity extends AppCompatActivity {

    private TextView savingCoin;
    private TextView savingRate;
    String newCoin;
    String newRate;
    static final String MYPREFERENCES = "myprefs";

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarkspage);

        savingCoin = (TextView)findViewById(R.id.savedCoin);
        savingRate = (TextView)findViewById(R.id.savedRate);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        newCoin = sharedPreferences.getString("coin", "");
        newRate = sharedPreferences.getString("rate", "");

        savingCoin.setText("GBP - " +newCoin);
        savingRate.setText(newRate);

    }

}
