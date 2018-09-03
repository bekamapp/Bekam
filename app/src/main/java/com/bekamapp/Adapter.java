package com.bekamapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<String> {
    String[] values;
    private final Context con;

    public Adapter(@NonNull Context context, String[] values) {
        super(context, R.layout.sample, values);
        this.con = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) con.getSystemService(con.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sample, parent, false);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_vendor_info);
        tv_info.setText(values[position]);
        ImageView ivicon = (ImageView) view.findViewById(R.id.ivvendor_icons);
        switch (position) {
            case 0:
                ivicon.setImageResource(R.drawable.ic_plus);
                break;
            case 1:
                ivicon.setImageResource(R.drawable.ic_next);
                break;
            case 2:
                ivicon.setImageResource(R.drawable.ic_edit);
                break;
            case 3:
                ivicon.setImageResource(R.drawable.ic_logout);
                break;
        }

        return view;
    }
}
