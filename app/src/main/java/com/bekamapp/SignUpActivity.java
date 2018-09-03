package com.bekamapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bekamapp.Vendor.VendorInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button signUp_vendor, signUp_user;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up_activity);

        auth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.et_user_email);
        password = (EditText)findViewById(R.id.et_user_password);

        signUp_user = (Button) findViewById(R.id.btn_user_signUp);
        signUp_vendor = (Button) findViewById(R.id.btn_vendor_signUp);


        signUp_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();

                auth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        }
                        else{
                            Toast.makeText(getBaseContext(), "Authentication Fail.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        signUp_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vendor_email = email.getText().toString().trim();
                String vendor_password = password.getText().toString().trim();

                auth.createUserWithEmailAndPassword(vendor_email, vendor_password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(SignUpActivity.this, VendorInfoActivity.class));
                        }
                        else{
                            Toast.makeText(getBaseContext(), "Authentication Fail.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
