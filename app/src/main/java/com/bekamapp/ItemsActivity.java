package com.bekamapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bekamapp.User.ItemDetailsActivity;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    ListView LVCategoryIteMs;
    CategoryItemsAdapter CategoryItemsAdapter;
    private TextView TV_Category_item_NAME;
    private String ItemCategoryNAMES;

    // String basiCategoryName=getIntent().getExtraString("Basic_Category_name");

    String basiCategoryName = "Fashion";

    ArrayList<CategoryItemsDetails> CategoryItemsList = new ArrayList<CategoryItemsDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_items_activity);

        LVCategoryIteMs = (ListView) findViewById(R.id.lvp);


        // here we shoule add the data from data base to CategoryItemsList;
        //
        switch (basiCategoryName) {
            case "Electronics and Home supplies":
                CategoryItemsDetails a = new CategoryItemsDetails("mobile", 10, R.drawable.ic_electronics);
                CategoryItemsList.add(a);

                a = new CategoryItemsDetails("iphone", 20, R.drawable.ic_electronics);
                CategoryItemsList.add(a);

                break;
            case "Fashion":
                CategoryItemsDetails b = new CategoryItemsDetails("mobile", 10, R.drawable.ic_shoes);
                CategoryItemsList.add(b);

                b = new CategoryItemsDetails("iphone", 20, R.drawable.ic_shoes);
                CategoryItemsList.add(b);
                break;

            case "Cars":
                CategoryItemsDetails c = new CategoryItemsDetails("mobile", 10, R.drawable.ic_cars);
                CategoryItemsList.add(c);

                c = new CategoryItemsDetails("iphone", 20, R.drawable.ic_cars);
                CategoryItemsList.add(c);
                break;

        }
        //------------------------------------------

        CategoryItemsAdapter = new CategoryItemsAdapter(this, CategoryItemsList);
        LVCategoryIteMs.setAdapter(CategoryItemsAdapter);

        LVCategoryIteMs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                TV_Category_item_NAME = (TextView) view.findViewById(R.id.tv_category_item_name);
                ItemCategoryNAMES = TV_Category_item_NAME.getText().toString();
                Intent i = new Intent(ItemsActivity.this, ItemDetailsActivity.class);
                i.putExtra("ItemID", ItemCategoryNAMES);
                startActivity(i);
            }
        });


    }
}
