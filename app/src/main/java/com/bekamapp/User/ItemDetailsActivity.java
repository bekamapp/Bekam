package com.bekamapp.User;

import android.net.wifi.rtt.RangingRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bekamapp.R;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_item_details_activity);

        final ListView LVReviews =(ListView)findViewById(R.id.lv_reviews);

        String[] reviews = new String[]{"Product is good","Good quality","affordable price","nice"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < reviews.length; i++) {
            list.add(reviews[i]);
        }
        ArrayAdapter<String> adapterItemDetails=new ArrayAdapter<String>(this,R.layout.sample_reviews,list);
        LVReviews.setAdapter(adapterItemDetails);
    }
}
