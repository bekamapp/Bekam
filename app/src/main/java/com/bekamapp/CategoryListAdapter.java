package com.bekamapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryListAdapter extends ArrayAdapter<Category> {
    private Context cust_context;
    private List<Category> cust_categories;

    public CategoryListAdapter(Context context, List<Category> categories) {
        super(context, R.layout.layout_category_list_adapter, categories);
        this.cust_context = context;
        this.cust_categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) cust_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_category_list_adapter, parent, false);
        ImageView imageViewPhoto = (ImageView) view.findViewById(R.id.iv_category_img);
        imageViewPhoto.setImageResource(cust_categories.get(position).getPhoto());

        TextView textViewPrice = (TextView) view.findViewById(R.id.tv_category_name);
        textViewPrice.setText(String.valueOf(cust_categories.get(position).getName()));

        return view;
    }
}
