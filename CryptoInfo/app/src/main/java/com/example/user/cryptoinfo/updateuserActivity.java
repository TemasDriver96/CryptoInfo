package com.example.user.cryptoinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class updateuserActivity extends AppCompatActivity {

    private EditText newPassword;
    private Button newUpdateBtn;
    private FirebaseUser fireUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateuserpage);

        newPassword = (EditText)findViewById(R.id.changePass);
        newUpdateBtn = (Button)findViewById(R.id.setupdatesbtn);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fireUser = FirebaseAuth.getInstance().getCurrentUser();

        newUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassofUser = newPassword.getText().toString();

                if(newPassofUser.isEmpty()) {
                    Toast.makeText(updateuserActivity.this, "Password not entered", Toast.LENGTH_SHORT).show();
                } else {
                    fireUser.updatePassword(newPassofUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(updateuserActivity.this, "Your Password has been updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(updateuserActivity.this, "Your Password could not be updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
