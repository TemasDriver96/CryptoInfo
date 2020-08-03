package com.example.user.cryptoinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashpage);
        getSupportActionBar().hide();
        launchLogo launch = new launchLogo();
        launch.start();
    }

    private class launchLogo extends Thread {
        public void run() {
            try {
                sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(splashActivity.this, loginActivity.class));
            finish();
        }
    }
}
