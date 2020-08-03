package com.example.user.cryptoinfo;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupActivity extends AppCompatActivity {

    private EditText userSignUpID;
    private EditText userPassword;
    private EditText userEmail;
    private Button userSignUp;
    private TextView alreadyLogedin;
    private FirebaseAuth fireAuth;


    String n;
    String p;
    String e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        setUIViews();

        fireAuth = FirebaseAuth.getInstance();

        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    fireAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                emailVerify();
                            }else {
                                Toast.makeText(signupActivity.this, "Registration was not complete", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        alreadyLogedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupActivity.this, loginActivity.class));
                finish();
            }
        });
    }

    private void setUIViews() {
        userSignUpID = (EditText)findViewById(R.id.signupName);
        userPassword = (EditText)findViewById(R.id.signupPass);
        userEmail = (EditText)findViewById(R.id.signupEmail);
        userSignUp = (Button)findViewById(R.id.regbtn);
        alreadyLogedin = (TextView)findViewById(R.id.usrlogedin);
    }

    private Boolean validate() {
        Boolean result = false;

        n = userSignUpID.getText().toString();
        p = userPassword.getText().toString();
        e = userEmail.getText().toString();

        if(n.isEmpty()) {
            Toast.makeText(this, "Sign Up Not Complete: Missing Name", Toast.LENGTH_SHORT).show();
        } else if(p.isEmpty()) {
            Toast.makeText(this, "Sign Up Not Complete: Missing Password", Toast.LENGTH_SHORT).show();
        } else if(e.isEmpty()) {
            Toast.makeText(this, "Sign Up Not Complete: Missing E-mail", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    private void emailVerify() {
        FirebaseUser firebaseUser = fireAuth.getCurrentUser();
        if(firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        sendDataOfUser();
                        Toast.makeText(signupActivity.this, "Verify E-mail to complete Registration", Toast.LENGTH_SHORT).show();
                        fireAuth.signOut();
                        startActivity(new Intent(signupActivity.this, loginActivity.class));
                    } else {
                        Toast.makeText(signupActivity.this,"Verification has not been sent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendDataOfUser() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(fireAuth.getUid());
        userInfo uInfo = new userInfo(n, e);
        ref.setValue(uInfo);
    }
}
