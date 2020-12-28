package com.com.meyshop.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.com.meyshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyOrderDetailsActivity extends AppCompatActivity {

    TextView nama_product, total_harga, jumlah_product, order_number, waktu_order, note;
    DatabaseReference reference;
    LinearLayout btn_kembali;
    Button btn_ratingnow;
    ImageView myorder_image;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String password_key = "passwordkey", email_key = "emailkey";
    String username_key_new, password_key_new, email_key_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);

        getUsernameLocal();
        Bundle bundle = getIntent().getExtras();
        final String my_order = bundle.getString("nama_order");

        nama_product = findViewById(R.id.myorder_name);
        total_harga = findViewById(R.id.myorder_price_total);
        jumlah_product = findViewById(R.id.myorder_total);
        order_number = findViewById(R.id.myorder_number);
        waktu_order = findViewById(R.id.myorder_time);
        btn_kembali = findViewById(R.id.btn_kembalikemyprofil);
        btn_ratingnow = findViewById(R.id.btn_ratingnow);
        myorder_image = findViewById(R.id.myorder_image);


        reference = FirebaseDatabase.getInstance().getReference().child("MyOrders").child(username_key_new).child(my_order);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.with(MyOrderDetailsActivity.this).load(dataSnapshot.child("url_thumbnail").
                        getValue().toString()).centerCrop().fit().into(myorder_image);
                nama_product.setText(dataSnapshot.child("nama_product").getValue().toString());
                total_harga.setText(dataSnapshot.child("total_harga").getValue().toString());
                jumlah_product.setText("x"+dataSnapshot.child("jumlah_product").getValue().toString());
                order_number.setText(my_order);
                waktu_order.setText(dataSnapshot.child("waktu_order").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_ratingnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Terimakasih", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyOrderDetailsActivity.this, HomeActivity.class);
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
