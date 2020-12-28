package com.com.meyshop.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.com.meyshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductsDetailsActivity extends AppCompatActivity {

    Button btn_buyclothes;
    LinearLayout btn_back;

    DatabaseReference reference;
    TextView judul, nama_product, harga_product, bahan, kualitas, kelebihan, descripton;
    ImageView header_product_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        Bundle bundle = getIntent().getExtras();
        final String jenis_product_baru = bundle.getString("jenis_product");

        btn_buyclothes = findViewById(R.id.btn_buyclothes);
        judul = findViewById(R.id.title_product);
        nama_product = findViewById(R.id.nama_product);
        harga_product = findViewById(R.id.harga_product);
        bahan = findViewById(R.id.bahan);
        kualitas = findViewById(R.id.kualitas);
        kelebihan = findViewById(R.id.kelebihan);
        descripton = findViewById(R.id.description_products);
        header_product_details = findViewById(R.id.header_product_details);
        btn_back = findViewById(R.id.btn_kembalikehome);

        //mengambl data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Products").child(jenis_product_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                judul.setText(dataSnapshot.child("jenis_products").getValue().toString());
                nama_product.setText(dataSnapshot.child("nama_products").getValue().toString());
                harga_product.setText("Rp. "+dataSnapshot.child("harga_products").getValue().toString());
                bahan.setText(dataSnapshot.child("bahan_products").getValue().toString());
                kualitas.setText(dataSnapshot.child("kualitas_products").getValue().toString());
                kelebihan.setText(dataSnapshot.child("kelebihan_products").getValue().toString());
                descripton.setText(dataSnapshot.child("desc_products").getValue().toString());
                Picasso.with(ProductsDetailsActivity.this).load(dataSnapshot.child("url_thumbnail").getValue().toString())
                        .centerCrop().fit().into(header_product_details);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_buyclothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoclothescheckout = new Intent(ProductsDetailsActivity.this, ProductsCheckoutActivity.class);
                gotoclothescheckout.putExtra("jenis_product", jenis_product_baru);
                startActivity(gotoclothescheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
