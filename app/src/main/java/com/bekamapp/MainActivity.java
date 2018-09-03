package com.bekamapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bekamapp.User.ItemDetailsActivity;
import com.bekamapp.User.MyProfileActivityUser;
import com.bekamapp.Vendor.MyProfileActivityVendor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageButton logout, home, account;
    Button cat5;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.btn_logout);
        home = findViewById(R.id.btn_home);
        account = findViewById(R.id.btn_account);
        cat5 = findViewById(R.id.btn_cat5);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                auth.signOut(); //byrg3 mn el main activity ll login
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MyProfileActivityVendor.class));
            }
        });

        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ItemDetailsActivity.class));
            }
        });
    }
}
