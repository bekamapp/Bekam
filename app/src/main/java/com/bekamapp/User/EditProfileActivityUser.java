package com.bekamapp.User;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bekamapp.LoginActivity;
import com.bekamapp.R;
import com.bekamapp.Vendor.EditProfileActivityVendor;
import com.bekamapp.Vendor.VendorDataFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivityUser extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    UserDataFirebase user;
    EditText email, password, oldPassword;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_profile_activity_user);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data");
        user = new UserDataFirebase(0);

        email = findViewById(R.id.et_edit_user_email);
        password = findViewById(R.id.et_edit_user_password);
        oldPassword = findViewById(R.id.et_edit_user_oldpassword);
        apply = findViewById(R.id.btn_apply);

        reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email.setText(firebaseUser.getEmail());
                Toast.makeText(EditProfileActivityUser.this, firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals("") || oldPassword.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivityUser.this, "Please enter a password.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), EditProfileActivityUser.class));
                }
                else
                {
                    Toast.makeText(EditProfileActivityUser.this, password.getText().toString().trim(), Toast.LENGTH_LONG).show();

                    String user_password = oldPassword.getText().toString().trim();
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), user_password);

                    // Prompt the user to re-provide their sign-in credentials

                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.updatePassword(password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(EditProfileActivityUser.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(EditProfileActivityUser.this, "Password NOT updated", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(EditProfileActivityUser.this, "Error auth failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }
            }
        });
    }
}
