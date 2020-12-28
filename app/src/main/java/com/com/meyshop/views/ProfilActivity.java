package com.com.meyshop.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.com.meyshop.R;
import com.com.meyshop.model.MyOrders;
import com.com.meyshop.viewmodel.MyOrderRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {
    LinearLayout myclothesdetails;
    Button btn_editprofile, btn_signout, btn_back;
    TextView nama, kelamin;
    ImageView foto;

    DatabaseReference reference, reference2;
    RecyclerView recyclerView;
    ArrayList<MyOrders> list_orders;
    MyOrderRecyclerAdapter myOrderRecyclerAdapter;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String password_key = "passwordkey", email_key = "emailkey";
    String username_key_new, password_key_new, email_key_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        myclothesdetails = findViewById(R.id.myclothesdetails);
        btn_editprofile = findViewById(R.id.btn_editprofile);
        btn_signout = findViewById(R.id.btn_signout);
        foto = findViewById(R.id.photo_profil_user);
        nama = findViewById(R.id.nama_profil);
        kelamin = findViewById(R.id.kelamin);
        recyclerView = findViewById(R.id.mylistorders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_orders = new ArrayList<MyOrders>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                kelamin.setText(dataSnapshot.child("jenis_kelamin").getValue().toString());
                Picasso.with(ProfilActivity.this).load(dataSnapshot.child("url_photo_profile").
                        getValue().toString()).centerCrop().fit().into(foto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoeditprofile = new Intent(ProfilActivity.this, EditProfileActivity.class);
                startActivity(gotoeditprofile);
            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("MyOrders").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MyOrders data = dataSnapshot1.getValue(MyOrders.class);
                    list_orders.add(data);
                }
                myOrderRecyclerAdapter = new MyOrderRecyclerAdapter(ProfilActivity.this, list_orders);
                recyclerView.setAdapter(myOrderRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menghapus semua data yang tersimpan diusername local
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.putString(password_key, null);
                editor.putString(email_key, null);
                editor.apply();

                //pindah ke signin activity
                Intent intent = new Intent(ProfilActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        password_key_new = sharedPreferences.getString(password_key, "");
        email_key_new = sharedPreferences.getString(email_key, "");
    }

}
