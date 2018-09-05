package com.bekamapp.User;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bekamapp.R;

public class UserIconAdapter extends ArrayAdapter<String> {
    String[] values;
    private final Context con;

    public UserIconAdapter(@NonNull Context context, String[] values) {
        super(context, R.layout.user_sample, values);
        this.con = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) con.getSystemService(con.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_sample, parent, false);
        TextView tv_user_info = (TextView) view.findViewById(R.id.tv_user_info);
        tv_user_info.setText(values[position]);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_user_icons);
        switch (position) {
            case 0:
                iv_icon.setImageResource(R.drawable.ic_edit);
                break;
            case 1:
                iv_icon.setImageResource(R.drawable.ic_logout);
                break;

        }
        return view;
    }

}
