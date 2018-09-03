package com.bekamapp.Vendor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.bekamapp.R;

public class MyProductsActivity extends AppCompatActivity {


    ListView LVMyProductItems;
    MyProductItemsAdapter myProductItemsAdapter;




    ArrayList<MyProductItemsDetails> myProductItemsList =new ArrayList<MyProductItemsDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_products_activity);

        LVMyProductItems =(ListView) findViewById(R.id.lv_my_product);
        // this function prepare myProductItemsList with static data for test
        prepareTestDataMyproduct();
        // so we should bring data from data base and add it to myProductItemsList
        myProductItemsAdapter =  new MyProductItemsAdapter(this,myProductItemsList);
        LVMyProductItems.setAdapter(myProductItemsAdapter);

    }



    private void prepareTestDataMyproduct() {
        MyProductItemsDetails a = new MyProductItemsDetails("shirt", 10, R.drawable.ic_fashion);
        myProductItemsList.add(a);

        a = new MyProductItemsDetails("heels",20, R.drawable.ic_fashion);
        myProductItemsList.add(a);


        MyProductItemsDetails b = new MyProductItemsDetails("mobile", 10,R.drawable.ic_shoes);
        myProductItemsList.add(b);

        b = new MyProductItemsDetails("choo",20, R.drawable.ic_cars);
        myProductItemsList.add(b);


        MyProductItemsDetails c = new MyProductItemsDetails("jacket", 10,R.drawable.ic_cars);
        myProductItemsList.add(c);

        c = new MyProductItemsDetails("bag",20, R.drawable.ic_electronics);
        myProductItemsList.add(c);
    }
    //------------------------------------------
}
