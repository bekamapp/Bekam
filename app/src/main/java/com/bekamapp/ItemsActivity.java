package com.bekamapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bekamapp.User.ItemDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity{

    FirebaseDatabase database;
    DatabaseReference reference;
//    FirebaseUser firebaseUser;
    ArrayList<Item> itemsList;

    ListView LVCategoryIteMs;
    CategoryItemsAdapter CategoryItemsAdapter;
    private TextView TV_Category_item_NAME;
    private String ItemCategoryNAMES;

    String categoryName;

    ArrayList<CategoryItemsDetails> CategoryItemsList = new ArrayList<CategoryItemsDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_items_activity);

        Category category = (Category) getIntent().getSerializableExtra("Category");

//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data");
        itemsList = new ArrayList<Item>();

        switch (category.getName()) {
            case "Cars":
                reference.addValueEventListener(new ValueEventListener() { //Table "Data"
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds_user : dataSnapshot.getChildren()) { //All users
                            //If vendor and category = Cars
                            if (ds_user.child("id").getValue().toString().equals("1") && ds_user.child("category").getValue(String.class).equals("Cars")) {
                                for (DataSnapshot ds_items : ds_user.child("Items").getChildren()) { //Get items of category cars
                                    Item item = ds_items.getValue(Item.class);
                                    itemsList.add(item);
                                }
                            }
                        }
                        setView();
//                        Toast.makeText(ItemsActivity.this, "Size: " + itemsList.size() + "\n" + itemsList.get(0).getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case "Fashion":
                reference.addValueEventListener(new ValueEventListener() { //Table "Data"
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds_user : dataSnapshot.getChildren()) { //All users
                            //If vendor and category = Cars
                            if (ds_user.child("id").getValue().toString().equals("1") && ds_user.child("category").getValue(String.class).equals("Fashion")) {
                                for (DataSnapshot ds_items : ds_user.child("Items").getChildren()) { //Get items of category Fashion
                                    Item item = ds_items.getValue(Item.class);
                                    itemsList.add(item);
                                }
                            }
                        }
                        setView();
//                        Toast.makeText(ItemsActivity.this, "Size: " + itemsList.size() + "\n" + itemsList.get(0).getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case "Home Supplies and Electronics":
                reference.addValueEventListener(new ValueEventListener() { //Table "Data"
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds_user : dataSnapshot.getChildren()) { //All users
                            //If vendor and category = Cars
                            if (ds_user.child("id").getValue().toString().equals("1") && ds_user.child("category").getValue(String.class).equals("Home Supplies and Electronics")) {
                                for (DataSnapshot ds_items : ds_user.child("Items").getChildren()) { //Get items of category Electronics
                                    Item item = ds_items.getValue(Item.class);
                                    itemsList.add(item);
                                }
                            }
                        }
                        setView();
//                        Toast.makeText(ItemsActivity.this, "Size: " + itemsList.size() + "\n" + itemsList.get(0).getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

        }
    }
    private void setView() {
        LVCategoryIteMs = (ListView) findViewById(R.id.lvp);
        CategoryItemsAdapter = new CategoryItemsAdapter(this, itemsList);
        LVCategoryIteMs.setAdapter(CategoryItemsAdapter);


        LVCategoryIteMs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                TV_Category_item_NAME = (TextView) view.findViewById(R.id.tv_category_item_name);
                ItemCategoryNAMES = TV_Category_item_NAME.getText().toString();
                Intent intent = new Intent(ItemsActivity.this, ItemDetailsActivity.class);
                intent.putExtra("Item", itemsList.get(position));
                startActivity(intent);
            }
        });
    }
}
