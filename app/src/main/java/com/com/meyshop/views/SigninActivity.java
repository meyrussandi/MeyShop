package com.com.meyshop.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.com.meyshop.R;
import com.com.meyshop.viewmodel.SignInViewModel;
import com.com.meyshop.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class SigninActivity extends AppCompatActivity {
    private EditText etusername;
    private EditText etpass;
    private Button button_masuk;
    private TextView daftar;
    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etusername = findViewById(R.id.et_username);
        etpass = findViewById(R.id.et_password);
        button_masuk = findViewById(R.id.btn_masuk);
        daftar = findViewById(R.id.tv_daftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosignup = new Intent(SigninActivity.this, SignUpOneActivity.class);
                startActivity(gotosignup);
                finish();
            }
        });

        button_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_masuk.setEnabled(false);
                button_masuk.setText("Loading ...");
                final String username = etusername.getText().toString();
                final String password = etpass.getText().toString();

                if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Kosong", Toast.LENGTH_SHORT).show();
                    button_masuk.setEnabled(true);
                    button_masuk.setText("MASUK");
                }else {
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Kosong", Toast.LENGTH_SHORT).show();
                        button_masuk.setEnabled(true);
                        button_masuk.setText("MASUK");
                    }else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    //ambil data password dari firebase
                                    String passwordfromfirebase = dataSnapshot.child("password").getValue().toString();
                                    //validasi password dengan password firebase
                                    if (password.equals(passwordfromfirebase)){
                                        //simpan username (key) pada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, etusername.getText().toString());
                                        editor.apply();

                                        //pindah activity
                                        Intent gotohome = new Intent(SigninActivity.this, HomeActivity.class);
                                        startActivity(gotohome);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Password Salah", Toast.LENGTH_SHORT).show();
                                        button_masuk.setEnabled(true);
                                        button_masuk.setText("MASUK");
                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(), "Username Tidak Ada", Toast.LENGTH_SHORT).show();
                                    button_masuk.setEnabled(true);
                                    button_masuk.setText("MASUK");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }


            }
        });

    }
/**
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_masuk){
            actionMasuk();
        }
    }

    private void actionMasuk() {
        SignInViewModel viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        User user = new User(username.getText().toString(),pass.getText().toString());
        if(!viewModel.checkUser(user)){
            Toast.makeText(this, "Username Password Salah", Toast.LENGTH_SHORT).show();
        }else{
            Intent gotohome = new Intent(SigninActivity.this, HomeActivity.class);
            startActivity(gotohome);
            finish();
        }
    }*/
}
