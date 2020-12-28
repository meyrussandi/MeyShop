package com.com.meyshop.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.com.meyshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpOneActivity extends AppCompatActivity {
    LinearLayout kembali;
    Button selanjutnyakesignuptwo;
    EditText username, password, email_adress;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String password_key = "passwordkey", email_key = "emailkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_one);

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        email_adress = findViewById(R.id.et_email);
        kembali = findViewById(R.id.btn_kembalidarisignupone);
        selanjutnyakesignuptwo = findViewById(R.id.btn_selanjutnyasignuptwo);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        selanjutnyakesignuptwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menyimpan data kepada lokal storage
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, username.getText().toString());
                editor.putString(password_key, password.getText().toString());
                editor.putString(email_key, email_adress.getText().toString());
                editor.apply();

                if(!username.getText().toString().equals("") && (!password.getText().toString().equals("") && !email_adress.getText().toString().equals(""))){
/*                                    //menyimpan ke database
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("email_adress").setValue(email_adress.getText().toString());
                        dataSnapshot.getRef().child("user_balance").setValue(250000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
*/
                    //pindah activity
                    Intent gotosignuptwo = new Intent(SignUpOneActivity.this, SignUpTwoActivity.class);
                    startActivity(gotosignuptwo);
                }else {
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
