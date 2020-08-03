package com.example.user.cryptoinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class navigationActivity extends AppCompatActivity {

    private Button topNews;
    private Button recentNews;
    private Button exchangeRate;
    private Button bookmarks;
    private FirebaseAuth firebaseAuth;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationpage);

        topNews = (Button)findViewById(R.id.topnewsbtn);
        recentNews = (Button)findViewById(R.id.recentnewsbtn);
        exchangeRate = (Button)findViewById(R.id.exchangeratebtn);
        bookmarks = (Button)findViewById(R.id.bookmarksbtn);

        firebaseAuth = FirebaseAuth.getInstance();

        topNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigationActivity.this, topnewsActivity.class));
            }
        });

        recentNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigationActivity.this, recentnewsActivity.class));
            }
        });

        exchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigationActivity.this, exchangerateActivity.class));
            }
        });

        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigationActivity.this, bookmarksActivity.class));
            }
        });
    }

    public void shareText() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Sharing from my mobile application CryptoInfo";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logoutMenu) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(navigationActivity.this, loginActivity.class));

        } if(item.getItemId() == R.id.profileMenu) {
            startActivity(new Intent(navigationActivity.this, userActivity.class));

        } if(item.getItemId() == R.id.shareMenu) {
            shareText();
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
