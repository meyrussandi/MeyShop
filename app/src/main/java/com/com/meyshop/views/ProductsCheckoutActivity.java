package com.com.meyshop.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class ProductsCheckoutActivity extends AppCompatActivity {
    Button btn_buyClothes, btn_mines, btn_plus;
    LinearLayout btn_kembali;
    TextView textjumlahClothes, textMybalance, textTotalHarga;
    TextView namaproduct, hargaProducts, ketentuan;
    Integer nilaiJumlahClothes = 1;
    Integer myBalance = 0;
    Integer nilaiTotalHarga = 0;
    Integer nilaiHargaBarang = 0;
    String gambar = "";
    ImageView ic_noticeuang, gambar_checkout_product;
    DatabaseReference reference, reference2, reference3;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String password_key = "passwordkey", email_key = "emailkey";
    String username_key_new, password_key_new, email_key_new;

    //data untuk order barang
    Integer no_order = new Random().nextInt();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String waktu_order = simpleDateFormat.format(calendar.getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_checkout);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String jenis_product_baru = bundle.getString("jenis_product");

        btn_mines = findViewById(R.id.btn_min);
        btn_plus = findViewById(R.id.btn_plus);
        textjumlahClothes = findViewById(R.id.jumlah_barang);
        textMybalance = findViewById(R.id.textMyBalance);
        textTotalHarga = findViewById(R.id.textTotalHarga);
        btn_buyClothes = findViewById(R.id.btn_buyclothes);
        btn_kembali = findViewById(R.id.btn_kembali);
        ic_noticeuang = findViewById(R.id.ic_noticeuang);
        gambar_checkout_product = findViewById(R.id.gambar_checkout_product);
        namaproduct = findViewById(R.id.nama_product);
        hargaProducts = findViewById(R.id.harga_product);
        ketentuan = findViewById(R.id.ketentuan);

        //set beberapa komponen
        textjumlahClothes.setText(nilaiJumlahClothes.toString());

        //default , hide btn_minus
        btn_mines.animate().alpha(0).setDuration(300).start();
        btn_mines.setEnabled(false);
        ic_noticeuang.setVisibility(View.GONE);

        //get user from database
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myBalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textMybalance.setText("Rp. "+myBalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("Products").child(jenis_product_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namaproduct.setText(dataSnapshot.child("nama_products").getValue().toString());
                hargaProducts.setText("Rp. "+dataSnapshot.child("harga_products").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                nilaiHargaBarang = Integer.valueOf(dataSnapshot.child("harga_products").getValue().toString());
                nilaiTotalHarga = nilaiJumlahClothes * nilaiHargaBarang;
                textTotalHarga.setText("Rp. " +nilaiTotalHarga+"");
                gambar = dataSnapshot.child("url_thumbnail").getValue().toString();
                Picasso.with(ProductsCheckoutActivity.this).load(dataSnapshot.child("url_thumbnail").
                        getValue().toString()).centerCrop().fit().into(gambar_checkout_product);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nilaiJumlahClothes -= 1;
                textjumlahClothes.setText(nilaiJumlahClothes.toString());
                if (nilaiJumlahClothes < 2){
                    btn_mines.animate().alpha(0).setDuration(300).start();
                    btn_mines.setEnabled(false);
                }
                nilaiTotalHarga = nilaiJumlahClothes * nilaiHargaBarang;
                textTotalHarga.setText("Rp. " +nilaiTotalHarga+"");
                if (nilaiTotalHarga < myBalance){
                    btn_buyClothes.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buyClothes.setEnabled(true);
                    textMybalance.setTextColor(Color.parseColor("#203DD1"));
                    ic_noticeuang.setVisibility(View.GONE);
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nilaiJumlahClothes += 1;
                textjumlahClothes.setText(nilaiJumlahClothes.toString());
                if (nilaiJumlahClothes > 1){
                    btn_mines.animate().alpha(1).setDuration(300).start();
                    btn_mines.setEnabled(true);
                }
                nilaiTotalHarga = nilaiJumlahClothes * nilaiHargaBarang;
                textTotalHarga.setText("Rp. " +nilaiTotalHarga+"");
                if (nilaiTotalHarga > myBalance){
                    btn_buyClothes.animate().translationY(500).alpha(0).setDuration(350).start();
                    btn_buyClothes.setEnabled(false);
                    textMybalance.setTextColor(Color.parseColor("#D1206B"));
                    ic_noticeuang.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_buyClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menyimpan orderan ke database
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyOrders").child(username_key_new).
                        child(namaproduct.getText().toString() + "-"+no_order);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("order_number").setValue(namaproduct.getText().toString() + "-"+no_order);
                        reference3.getRef().child("url_thumbnail").setValue(gambar);
                        reference3.getRef().child("nama_product").setValue(namaproduct.getText().toString());
                        reference3.getRef().child("harga_product").setValue(nilaiHargaBarang);
                        reference3.getRef().child("waktu_order").setValue(waktu_order);
                        reference3.getRef().child("jumlah_product").setValue(nilaiJumlahClothes);
                        reference3.getRef().child("total_harga").setValue(nilaiTotalHarga);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
                    }
                });

                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference2.getRef().child("user_balance").setValue((myBalance-nilaiTotalHarga));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent gotosuccesclothes = new Intent(ProductsCheckoutActivity.this, SuccessBuyProductsActivity.class);
                startActivity(gotosuccesclothes);
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
