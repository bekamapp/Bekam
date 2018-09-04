package com.bekamapp;


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
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CategoryItemsAdapter extends ArrayAdapter<CategoryItemsDetails> {
    private final ArrayList<CategoryItemsDetails> values;
    private final Context con;
    private TextView categoryItemName, categoryItemPrice;
    private ImageView categoryItemImg,ItemoptionImg;
    public CategoryItemsAdapter(@NonNull Context context, ArrayList<CategoryItemsDetails> values) {
        super(context,R.layout.itemsample, values);
        this.con=context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater)con.getSystemService(con.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.itemsample,parent,false);
        categoryItemName =(TextView) view.findViewById(R.id.tv_category_item_name);
        categoryItemPrice =(TextView) view.findViewById(R.id.tv_category_item_price);
        categoryItemImg =(ImageView) view.findViewById(R.id.iv_category_item);
        ItemoptionImg=(ImageView) view.findViewById(R.id.iv_category_item_option);
        categoryItemName.setText(values.get(position).getItemName());
        String ItemPrice=values.get(position).getItemPrice()+" le";
        categoryItemPrice.setText(ItemPrice);


        Glide.with(con).load(values.get(position).getItemImgURl()).into((ImageView) view.findViewById(R.id.iv_category_item));

        ItemoptionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(ItemoptionImg);
            }
        });
        return view;
    }
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(con, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_album, popup.getMenu());
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
                case R.id.action_favourite:
                    Toast.makeText(con, "added to favourite", Toast.LENGTH_LONG).show();
                    //add to favourite
                    return true;
                case R.id.action_wish:
                    Toast.makeText(con, "add to wish list", Toast.LENGTH_LONG).show();
                    //add to wishlist
                    return true;
                default:
            }
            return false;
        }
    }
}
