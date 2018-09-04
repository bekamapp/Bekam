package com.bekamapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bekamapp.User.MyProfileActivityUser;
import com.bekamapp.Vendor.MyProfileActivityVendor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
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

        auth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                //If not logged in, log in first
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    //Get reference to database, to check if it's a vendor or user, to open his account
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Data");
                    reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String id = dataSnapshot.child("id").getValue().toString();
                            Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();

                            //ID = 1 -> Vendor
                            if (id.equals("1")) {
                                Toast.makeText(MainActivity.this, "Vendor", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), MyProfileActivityVendor.class));
                            }
                            //ID = 0 -> User
                            else if (id.equals("0")) {
                                Toast.makeText(MainActivity.this, "USER", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), MyProfileActivityUser.class));
                            }
                            else
                                Toast.makeText(MainActivity.this, "7aga Tanya!   " + dataSnapshot.child("id").getValue().toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
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
