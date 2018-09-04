package com.bekamapp.Vendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bekamapp.Adapter;
import com.bekamapp.LoginActivity;
import com.bekamapp.MainActivity;
import com.bekamapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class MyProfileActivityVendor extends AppCompatActivity {

    FirebaseAuth auth;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If not logged in.
//        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() == null) {
//            startActivity(new Intent(MyProfileActivityVendor.this, LoginActivity.class));
//            finish();
//        }

        setContentView(R.layout.layout_my_profile_activity_vendor);

        listView =(ListView)findViewById(R.id.lv_ven_profile);
        String []content={"Add Product","My Products","Edit My Profile","LogOut"};
        Adapter adapter = new Adapter(this, content);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        startActivity(new Intent(MyProfileActivityVendor.this, AddProductActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MyProfileActivityVendor.this, MyProductsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MyProfileActivityVendor.this, EditProfileActivityVendor.class)); //hn3'er el profile elly hytft7,, ana 3mlah dlw2ty ay 7aga
                        break;
                    case 3:
                        auth = FirebaseAuth.getInstance();
                        auth.signOut();
                        startActivity(new Intent(MyProfileActivityVendor.this, MainActivity.class));
                        break;
                }
            }
        });
    }
}
