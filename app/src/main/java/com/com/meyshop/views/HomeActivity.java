package com.com.meyshop.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.com.meyshop.R;
import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    LinearLayout menu1, menu2, mneu3,menu4, menu5, menu6;
    CircleView btn_to_profile;
    ImageView photo_user;
    TextView user_balance, nama_lengkap, jenis_kelamin;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUsernameLocal();

        menu1 = findViewById(R.id.btn_menu1);
        menu2 = findViewById(R.id.btn_menu2);
        mneu3 = findViewById(R.id.btn_menu3);
        menu4 = findViewById(R.id.btn_menu4);
        menu5 = findViewById(R.id.btn_menu5);
        menu6 = findViewById(R.id.btn_menu6);
        btn_to_profile = findViewById(R.id.btn_to_profile);
        photo_user =findViewById(R.id.photo_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.user_nama_lengkap);
        jenis_kelamin = findViewById(R.id.user_kelamin);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                jenis_kelamin.setText(dataSnapshot.child("jenis_kelamin").getValue().toString());
                user_balance.setText("RP. "+dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop().fit().into(photo_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gootoclothesdetails = new Intent(HomeActivity.this, ProductsDetailsActivity.class);
                gootoclothesdetails.putExtra("jenis_product", "Clothes");
                startActivity(gootoclothesdetails);
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gootoclothesdetails = new Intent(HomeActivity.this, ProductsDetailsActivity.class);
                gootoclothesdetails.putExtra("jenis_product", "Socks");
                startActivity(gootoclothesdetails);
            }
        });
        mneu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gootoclothesdetails = new Intent(HomeActivity.this, ProductsDetailsActivity.class);
                gootoclothesdetails.putExtra("jenis_product", "Shoes");
                startActivity(gootoclothesdetails);
            }
        });
        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gootoclothesdetails = new Intent(HomeActivity.this, ProductsDetailsActivity.class);
                gootoclothesdetails.putExtra("jenis_product", "Bag");
                startActivity(gootoclothesdetails);
            }
        });
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gootoclothesdetails = new Intent(HomeActivity.this, ProductsDetailsActivity.class);
                gootoclothesdetails.putExtra("jenis_product", "Blanket");
                startActivity(gootoclothesdetails);
            }
        });
        menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gootoclothesdetails = new Intent(HomeActivity.this, ProductsDetailsActivity.class);
                gootoclothesdetails.putExtra("jenis_product", "Accessories");
                startActivity(gootoclothesdetails);
            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeActivity.this, ProfilActivity.class);
                startActivity(gotoprofile);
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("tag : ", "signInAnonymously:FAILURE", exception);
                    }
                });
    }
}
