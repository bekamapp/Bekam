package com.bekamapp.Vendor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bekamapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivityVendor extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    VendorDataFirebase vendor;
    Button applyChanges;
    EditText name, phone, location, category;
    CheckBox sat, sun, mon, tue, wed, thurs, fri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_profile_activity_vendor);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data");
        vendor = new VendorDataFirebase();

        name = findViewById(R.id.et_edit_vendor_name);
        phone = findViewById(R.id.et_edit_vendor_phone);
        location = findViewById(R.id.et_edit_vendor_loc);
        category = findViewById(R.id.et_edit_vendor_category);
        applyChanges = findViewById(R.id.btn_vendor_applyChanges);

        sat = findViewById(R.id.checkbox_edit_vendor_sat);
        sun = findViewById(R.id.checkbox_edit_vendor_sun);
        mon = findViewById(R.id.checkbox_edit_vendor_mon);
        tue = findViewById(R.id.checkbox_edit_vendor_tue);
        wed = findViewById(R.id.checkbox_edit_vendor_wed);
        thurs = findViewById(R.id.checkbox_edit_vendor_thurs);
        fri = findViewById(R.id.checkbox_edit_vendor_fri);

        //Working Hours
        final String[] workingHours = {"", ""};
        //get the spinner from the xml.
        Spinner dropdownFrom = findViewById(R.id.spinner_edit_vendor_WH_From);
        Spinner dropdownTo = findViewById(R.id.spinner_edit_vendor_WH_To);
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


        //To get data from database
        reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vendor = dataSnapshot.getValue(VendorDataFirebase.class);
                Toast.makeText(getBaseContext(), vendor.getName(), Toast.LENGTH_LONG).show();
                name.setText(vendor.getName());
                phone.setText(vendor.getPhone());
                location.setText(vendor.getLocation());
                category.setText(vendor.getCategory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendor.setID(1);
                if (name.getText().toString().equals("") || phone.getText().toString().equals("") || location.getText().toString().equals("") || getDays().isEmpty()){
                    Toast.makeText(EditProfileActivityVendor.this, "Please fill in all data.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), EditProfileActivityVendor.class));
                }
                else {
                    vendor.setName(name.getText().toString().trim());
                    vendor.setPhone(phone.getText().toString().trim());
                    vendor.setLocation(location.getText().toString().trim());
                    vendor.setWorkingHours(getDays(), workingHours[0], workingHours[1]);
                    vendor.setCategory(vendor.getCategory());
                    reference.child(firebaseUser.getUid()).setValue(vendor);
                }
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
