package com.bekamapp.Vendor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekamapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyProductItemsAdapter extends ArrayAdapter<MyProductItemsDetails>{
    private final  ArrayList<MyProductItemsDetails> MyProductValues;
    private final Context context;
    private TextView myProductItemName, MyProductItemPrice;
    private ImageView MyproductItemImg, MyProductItemoptionImg;
    public MyProductItemsAdapter(@NonNull Context context, ArrayList<MyProductItemsDetails> values) {
        super(context,R.layout.myproductsample, values);
        this.context =context;
        this.MyProductValues = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.myproductsample,parent,false);
        myProductItemName =(TextView) view.findViewById(R.id.tv_my_product_item_name);
        MyProductItemPrice =(TextView) view.findViewById(R.id.tv_my_product_item_price);
        MyproductItemImg =(ImageView) view.findViewById(R.id.iv_my_product_item);
        MyProductItemoptionImg =(ImageView) view.findViewById(R.id.iv_my_product_item_option);
        myProductItemName.setText(MyProductValues.get(position).getItemName());
        String ItemPrice= MyProductValues.get(position).getItemPrice()+" le";
        MyProductItemPrice.setText(ItemPrice);


        Glide.with(context).load(MyProductValues.get(position).getItemImgURl()).into((ImageView) view.findViewById(R.id.iv_my_product_item));

        MyProductItemoptionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(MyProductItemoptionImg);
            }
        });
        return view;
    }
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.myproduct_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_remove:
                    // remove this product from data base
                    Toast.makeText(context, "Removed ", Toast.LENGTH_LONG).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
