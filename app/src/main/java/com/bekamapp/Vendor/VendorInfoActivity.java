package com.bekamapp.Vendor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bekamapp.LoginActivity;
import com.bekamapp.MainActivity;
import com.bekamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VendorInfoActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    VendorDataFirebase vendor;
    Button register;
    EditText name, phone, location;
    CheckBox sat, sun, mon, tue, wed, thurs, fri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vendor_info_activity);

        Toast.makeText(getBaseContext(), "HELLO", Toast.LENGTH_LONG).show();

        name = findViewById(R.id.et_reg_name);
        phone = findViewById(R.id.et_reg_phone);
        location = findViewById(R.id.et_reg_loc);
        register = findViewById(R.id.btn_vendor_signUp);

        sat = findViewById(R.id.checkbox_sat);
        sun = findViewById(R.id.checkbox_sun);
        mon = findViewById(R.id.checkbox_mon);
        tue = findViewById(R.id.checkbox_tue);
        wed = findViewById(R.id.checkbox_wed);
        thurs = findViewById(R.id.checkbox_thurs);
        fri = findViewById(R.id.checkbox_fri);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data");
        vendor = new VendorDataFirebase();

        //Working Hours
        final String[] workingHours = {"", ""};
        //get the spinner from the xml.
        Spinner dropdownFrom = findViewById(R.id.spinner_WH_From);
        Spinner dropdownTo = findViewById(R.id.spinner_WH_To);
        //create a list of items for the spinner.
        String[] items = new String[]{"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00", "1", "2", "3", "4", "5", "6", "7"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdownFrom.setAdapter(adapter);
        dropdownTo.setAdapter(adapter);

        dropdownFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workingHours[0] = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropdownTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workingHours[1] = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Category
        final String[] selectedCategory = {""};
        //get the spinner from the xml.
        Spinner category = findViewById(R.id.spinner_Category);
        //create a list of items for the spinner.
        String[] categories = new String[]{"Home Supplies and Electronics", "Cars", "Fashion"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> category_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        //set the spinners adapter to the previously created one.
        category.setAdapter(category_adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory[0] = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //When the vendor press submit info button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save what he wrote into variables
                String vendor_name = name.getText().toString().trim();
                String vendor_phone = phone.getText().toString().trim();
                String vendor_location = location.getText().toString().trim();

                //To add data to database
                Toast.makeText(getBaseContext(), "Created user: " + firebaseUser.getUid(), Toast.LENGTH_LONG).show();
                vendor.setID(1);
                vendor.setName(vendor_name);
                vendor.setPhone(vendor_phone);
                vendor.setLocation(vendor_location);
                vendor.setWorkingHours(getDays(), workingHours[0], workingHours[1]);
                vendor.setCategory(selectedCategory[0]);
                reference.child(firebaseUser.getUid()).setValue(vendor);

                startActivity(new Intent(VendorInfoActivity.this, MainActivity.class));

//                //To get data from database
//                reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        VendorDataFirebase vendor_data = dataSnapshot.getValue(VendorDataFirebase.class);
//                        Toast.makeText(getBaseContext(), vendor_data.getName(), Toast.LENGTH_LONG).show();
//                        //txt_hi.setText(vendor_data.getName());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
    }

    List<String> getDays() {
        List<String> selectedDays = new ArrayList<>();
        if (sat.isChecked())
            selectedDays.add(sat.getText().toString());
        if (sun.isChecked())
            selectedDays.add(sun.getText().toString());
        if (mon.isChecked())
            selectedDays.add(mon.getText().toString());
        if (tue.isChecked())
            selectedDays.add(tue.getText().toString());
        if (wed.isChecked())
            selectedDays.add(wed.getText().toString());
        if (thurs.isChecked())
            selectedDays.add(thurs.getText().toString());
        if (fri.isChecked())
            selectedDays.add(fri.getText().toString());

        return selectedDays;
    }
}
