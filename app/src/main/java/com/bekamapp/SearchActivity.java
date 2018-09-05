package com.bekamapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekamapp.User.ItemDetailsActivity;
import com.bekamapp.Vendor.MyProductItemsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView LVSearchResult;
    MyProductItemsAdapter searchItemsAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseDatabase DB;
    String searchValue;
    ArrayList<Item> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_activity);

        searchValue = getIntent().getStringExtra("searchValue");

        auth = FirebaseAuth.getInstance();
        itemsList = new ArrayList<Item>();
        DB = FirebaseDatabase.getInstance();
        databaseReference = DB.getReference("Data");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> vendorIDs = new ArrayList<String>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ID = String.valueOf(ds.getKey());
                    vendorIDs.add(ID);
                }

                for (final int[] i = {0}; i[0] < vendorIDs.size(); i[0]++) {
                    databaseReference.child(vendorIDs.get(i[0])).child("Items").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Item item = dataSnapshot.child(ds.getKey()).getValue(Item.class);
                                String itemName = item.getName().toLowerCase();
                                String searchItem = searchValue.toLowerCase();
                                if (itemName.equals(searchItem) || itemName.contains(searchItem) || searchItem.contains(itemName)) {
                                    itemsList.add(item);
                                }
                            }
                            setView();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setView() {
        LVSearchResult = (ListView) findViewById(R.id.lv_search_result);
        searchItemsAdapter = new MyProductItemsAdapter(this, itemsList);
        LVSearchResult.setAdapter(searchItemsAdapter);

        LVSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                Intent intent = new Intent(SearchActivity.this, ItemDetailsActivity.class);
                intent.putExtra("Item", itemsList.get(position));
                startActivity(intent);
            }
        });
    }
}
