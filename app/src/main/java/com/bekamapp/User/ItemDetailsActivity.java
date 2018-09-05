package com.bekamapp.User;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.rtt.RangingRequest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekamapp.Item;
import com.bekamapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    TextView name, price, description, location, vendor, phone, workingHours;
    //    EditText editReview;
    //    Button addReview;
    ImageView image;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_item_details_activity);

        item = (Item) getIntent().getSerializableExtra("Item");

        name = findViewById(R.id.tv_product_name);
        price = findViewById(R.id.tv_product_price);
        description = findViewById(R.id.tv_product_des);
        vendor = findViewById(R.id.tv_product_ven);
        location = findViewById(R.id.tv_product_loc);
        phone = findViewById(R.id.tv_product_phone);
        workingHours = findViewById(R.id.tv_product_WH);
//        editReview = findViewById(R.id.et_newReview);
//        addReview = findViewById(R.id.btn_addReview);
        image = findViewById(R.id.iv_product);

        name.setText(item.getName());
        price.setText(item.getPrice());
        description.setText(item.getDescription());

        String ID = item.getImage().split("/")[0];
        String imageURI = item.getImage().split("/")[1];

        //Get Location and Vendor
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        reference.child(ID).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vendor.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.child(ID).child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                location.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.child(ID).child("phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phone.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.child(ID).child("workingHours").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String days = dataSnapshot.child("workingDays").getValue().toString();
                days = days.substring(1, days.length() - 2);
                workingHours.setText(days);
                workingHours.append(". From " + dataSnapshot.child("from").getValue(String.class));
                workingHours.append(" to " + dataSnapshot.child("to").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        storageReference.child(ID).child(imageURI).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ItemDetailsActivity.this).load(uri).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ItemDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

//        //If vendor, hide add review section
//        reference.child(firebaseUser.getUid()).child("id").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue().toString().equals("1")) {
//                    editReview.setVisibility(View.GONE);
//                    addReview.setVisibility(View.GONE);
//                } else {
//                    editReview.setVisibility(View.VISIBLE);
//                    addReview.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        final ListView LVReviews = (ListView) findViewById(R.id.lv_reviews);
//
//        String[] reviews = new String[]{"Product is good", "Good quality", "affordable price", "nice"};
//        final ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < reviews.length; i++) {
//            list.add(reviews[i]);
//        }
//        ArrayAdapter<String> adapterItemDetails = new ArrayAdapter<String>(this, R.layout.sample_reviews, list);
//        LVReviews.setAdapter(adapterItemDetails);
    }
}
