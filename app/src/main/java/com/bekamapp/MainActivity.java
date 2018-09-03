package com.bekamapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bekamapp.Vendor.MyProfileActivityVendor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageButton logout, home, account;
    private ListView listViewCategories;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.btn_logout);
        home = findViewById(R.id.btn_home);
        account = findViewById(R.id.btn_account);

        categories = new ArrayList<Category>();
        categories.add(new Category("c01", "Electronics and Home supplies", R.drawable.ic_electronics, BigDecimal.valueOf(100)));
        categories.add(new Category("c02", "Fashion", R.drawable.ic_fashion, BigDecimal.valueOf(100)));
        categories.add(new Category("c03", "Cars", R.drawable.ic_cars, BigDecimal.valueOf(100)));


        listViewCategories = (ListView) findViewById(R.id.listviewCategory);
        listViewCategories.setAdapter(new CategoryListAdapter(MainActivity.this, categories));

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

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = categories.get(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", category);

                //Open Items of certain categories
                Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }
}
