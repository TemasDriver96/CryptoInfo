package com.example.user.cryptoinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userActivity extends AppCompatActivity {

    private TextView newusersName;
    private TextView newusersEmail;
    private Button updateInformation;
    private FirebaseAuth updateFirebaseAuth;
    private FirebaseDatabase updateFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);

        newusersName = (TextView)findViewById(R.id.newUsersName);
        newusersEmail = (TextView)findViewById(R.id.newUsersEmail);
        updateInformation = (Button)findViewById(R.id.updatesbtn);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userActivity.this, updateuserActivity.class));
            }
        });

        updateFirebaseAuth = FirebaseAuth.getInstance();
        updateFirebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseRef = updateFirebaseDatabase.getReference(updateFirebaseAuth.getUid());

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo newuserInfo = dataSnapshot.getValue(userInfo.class);
                newusersName.setText(newuserInfo.getUserInfoName());
                newusersEmail.setText(newuserInfo.getUserInfoEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(userActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
