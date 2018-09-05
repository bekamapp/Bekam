package com.bekamapp.Vendor;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.bekamapp.Item;
import com.bekamapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProductsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ArrayList<Item> itemsList;
    ListView LVMyProductItems;
    MyProductItemsAdapter myProductItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_products_activity);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data");

        reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemsList = new ArrayList<Item>();
                for (DataSnapshot ds : dataSnapshot.child("Items").getChildren()) {
                    Item item = ds.getValue(Item.class);
                    itemsList.add(item);
                }
                setView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setView() {
        LVMyProductItems = (ListView) findViewById(R.id.lv_my_product);
        // so we should bring data from data base and add it to myProductItemsList
        myProductItemsAdapter = new MyProductItemsAdapter(this, itemsList);
        LVMyProductItems.setAdapter(myProductItemsAdapter);
    }
}
