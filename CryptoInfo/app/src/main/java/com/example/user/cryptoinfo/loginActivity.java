package com.example.user.cryptoinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    private EditText userID;
    private EditText password;
    private TextView info;
    private Button login;
    private Button signUp;
    private Button facebookLogin;
    private Button twitterLogin;
    private int count = 3;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        userID = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.userPassword);
        info = (TextView)findViewById(R.id.passinfo);
        login = (Button)findViewById(R.id.loginbtn);
        signUp = (Button)findViewById(R.id.signupbtn);
        facebookLogin = (Button)findViewById(R.id.loginFB);
        twitterLogin = (Button)findViewById(R.id.loginTW);
        forgotPassword = (TextView)findViewById(R.id.fgtpass);

        info.setText("Number of attempts left: 3");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            finish();
            startActivity(new Intent(loginActivity.this, navigationActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userID.getText().toString().trim().equals("")) {
                    Toast.makeText(loginActivity.this, "Enter your E-mail", Toast.LENGTH_SHORT).show();
                } else if(password.getText().toString().trim().equals("")) {
                    Toast.makeText(loginActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                } else {
                    validation(userID.getText().toString(), password.getText().toString());
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, signupActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, forgotpasswordActivity.class));
            }
        });

    }

    private void validation(String usrId, String usrPass) {

        progressDialog.setMessage("Your Credentials are being verified");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(usrId, usrPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailVerfied();
                } else {
                    Toast.makeText(loginActivity.this, "Incorrect E-mail or Password", Toast.LENGTH_SHORT).show();
                    count--;
                    info.setText("Number of attempts left: " + count);
                    progressDialog.dismiss();
                    if(count == 0) {
                        login.setEnabled(false);
                    }
                }
            }
        });
    }

    private void checkEmailVerfied() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailverify = firebaseUser.isEmailVerified();

        if(emailverify) {
            finish();
            startActivity(new Intent(loginActivity.this, navigationActivity.class));
        } else {
            Toast.makeText(this,"Verify your E-mail", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
