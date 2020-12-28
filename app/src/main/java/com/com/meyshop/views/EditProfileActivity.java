package com.com.meyshop.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.com.meyshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {
    LinearLayout kembali;
    Button btn_save, btn_add_photo;
    ImageView editphoto;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storageReference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    EditText nama, kelamin, username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUsernameLocal();

        kembali = findViewById(R.id.btn_kembalikemyprofiledarieditprofile);
        nama = findViewById(R.id.et_nama);
        kelamin = findViewById(R.id.et_kelamin);
        username = findViewById(R.id.et_username);
        username.setKeyListener(null);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Tidak bisa merubah username", Toast.LENGTH_SHORT).show();
            }
        });
        password = findViewById(R.id.et_password);
        email = findViewById(R.id.et_email);
        email.setKeyListener(null);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Tidak bisa merubah alamat Email", Toast.LENGTH_SHORT).show();
            }
        });
        btn_save = findViewById(R.id.btn_saveprofile);
        btn_add_photo = findViewById(R.id.btn_edit_fotoprofil);
        editphoto = findViewById(R.id.pic_edit_photo_user);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storageReference = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                kelamin.setText(dataSnapshot.child("jenis_kelamin").getValue().toString());
                username.setText(dataSnapshot.child("username").getValue().toString());
                password.setText(dataSnapshot.child("password").getValue().toString());
                email.setText(dataSnapshot.child("email_adress").getValue().toString());
                Picasso.with(EditProfileActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).
                        centerCrop().fit().into(editphoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (photo_location != null){
                    final StorageReference storageReference1 = storageReference.child(System.currentTimeMillis() +"."+
                            getFileExtension(photo_location));
                    storageReference1.putFile(photo_location).
                            addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                            reference.getRef().child("nama_lengkap").setValue(nama.getText().toString());
                                            reference.getRef().child("jenis_kelamin").setValue(kelamin.getText().toString());
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                            Intent gotoprofile = new Intent(EditProfileActivity.this, ProfilActivity.class);
                                            startActivity(gotoprofile);
                                            finish();
                                        }
                                    });
                                }
                            });
                }
            }
        });

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null){
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(editphoto);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
