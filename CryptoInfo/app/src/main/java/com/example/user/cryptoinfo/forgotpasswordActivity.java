package com.example.user.cryptoinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button forgotPass;
    private FirebaseAuth firebasefgtAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpasswordpage);

        email = (EditText)findViewById(R.id.passfgt);
        forgotPass = (Button)findViewById(R.id.passfgtbtn);
        firebasefgtAuth = FirebaseAuth.getInstance();

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userem = email.getText().toString().trim();

                if(userem.isEmpty()) {
                    Toast.makeText(forgotpasswordActivity.this, "Enter a valid E-mail", Toast.LENGTH_SHORT).show();
                } else {
                    firebasefgtAuth.sendPasswordResetEmail(userem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(forgotpasswordActivity.this, "Reset E-mail sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotpasswordActivity.this, loginActivity.class));
                            } else {
                                Toast.makeText(forgotpasswordActivity.this,"Your E-mail does not exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
