package com.bekamapp;


import android.content.Context;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CategoryItemsAdapter extends ArrayAdapter<Item> {

    FirebaseStorage storage;
    StorageReference storageReference;

    private final ArrayList<Item> items;
    private final Context context;
    private TextView tv_itemName, tv_itemPrice;
    private ImageView iv_itemImage;

    public CategoryItemsAdapter(@NonNull Context context, ArrayList<Item> itemsList) {
        super(context, R.layout.itemsample, itemsList);
        this.context = context;
        this.items = new ArrayList<>(itemsList);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.itemsample, parent, false);

        //Get IDs
        tv_itemName = (TextView) view.findViewById(R.id.tv_category_item_name);
        tv_itemPrice = (TextView) view.findViewById(R.id.tv_category_item_price);
        iv_itemImage = (ImageView) view.findViewById(R.id.iv_category_item);

        //Set Values
        String name = items.get(position).getName(), price = items.get(position).getPrice();
        if (!name.equals(null) && !price.equals(null)) {
            tv_itemName.setText(name);
            tv_itemPrice.setText(price);
        }
        else {
            tv_itemName.setText("mafesh 7aga");
            tv_itemPrice.setText("mafesh 7aga");
        }

        String ID = items.get(position).getImage().split("/")[0];
        String imageURI = items.get(position).getImage().split("/")[1];

        storageReference.child(ID).child(imageURI).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(iv_itemImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed on loading images", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

