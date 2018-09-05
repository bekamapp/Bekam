package com.bekamapp.Vendor;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekamapp.Item;
import com.bekamapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.ArrayList;

public class MyProductItemsAdapter extends ArrayAdapter<Item> {

    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference reference;

    private final ArrayList<Item> items;
    private final Context context;
    private TextView tv_itemName, tv_itemPrice;
    private ImageView iv_itemImage, MyProductItemoptionImg;
    private Item selectedItem;
    String ID, imageURI;

    public MyProductItemsAdapter(@NonNull Context context, ArrayList<Item> itemsList) {

        super(context, R.layout.myproductsample, itemsList);
        this.context = context;
        this.items = new ArrayList<>(itemsList);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.myproductsample, parent, false);

        //Get IDs
        tv_itemName = (TextView) view.findViewById(R.id.tv_my_product_item_name);
        tv_itemPrice = (TextView) view.findViewById(R.id.tv_my_product_item_price);
        iv_itemImage = (ImageView) view.findViewById(R.id.iv_my_product_item);
        MyProductItemoptionImg = (ImageView) view.findViewById(R.id.iv_my_product_item_option);

        //Set Values
        String name = items.get(position).getName(), price = items.get(position).getPrice();
        if(!name.equals(null) && !price.equals(null)) {
            tv_itemName.setText(name);
            tv_itemPrice.setText(price);
        }
        else {
            tv_itemName.setText("mafesh 7aga");
            tv_itemPrice.setText("mafesh 7aga");
        }

        selectedItem = items.get(position);
        ID = items.get(position).getImage().split("/")[0];
        imageURI = items.get(position).getImage().split("/")[1];

       storageReference.child(ID).child(imageURI).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               Glide.with(context).load(uri).into(iv_itemImage);
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
           }
       });

        //Delete Item

        MyProductItemoptionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflaterPop = popup.getMenuInflater();
                inflaterPop.inflate(R.menu.myproduct_album, popup.getMenu());
                popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
                popup.show();
            }
        });
        return view;
    }

    private void showPopupMenu(View view) {
        // inflate menu

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
                    reference.child(ID).child("Items").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                if(ds.child("name").getValue().toString() == selectedItem.getName() && ds.child("description").getValue().toString() == selectedItem.getDescription()) {
                                    ds.getRef().removeValue();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(context, "Removed ", Toast.LENGTH_LONG).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
