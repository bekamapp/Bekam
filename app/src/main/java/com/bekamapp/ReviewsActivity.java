package com.bekamapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    EditText newReview;
    Button getReviews, addReview;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reviews_activity);

        newReview = findViewById(R.id.editText);
        getReviews = findViewById(R.id.button);
        addReview = findViewById(R.id.button2);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Reviews");

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = newReview.getText().toString();

                //Insert new review to database
                String key = reference.push().getKey();  //Generate a key for each data entered to create new child with new data
                reference.child(key).setValue(review);
            }
        });

        getReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get all reviews
                reference.addValueEventListener(new ValueEventListener() {  //reference = table
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<String> list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) { //loop on all records
                            list.add(ds.getKey());
                        }
                        for (int i = 0; i < list.size(); i++) {
                            reference.child((list.get(i))).addValueEventListener(new ValueEventListener() {  //list.get(i), get each child
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String single_review = dataSnapshot.getValue(String.class);

                                    Toast.makeText(getBaseContext(), single_review, Toast.LENGTH_LONG).show();
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
        });
    }
}
