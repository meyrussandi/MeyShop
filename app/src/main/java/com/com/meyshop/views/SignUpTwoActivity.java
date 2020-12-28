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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SignUpTwoActivity extends AppCompatActivity {

    private LinearLayout kembali;
    private Button btn_daftar, btn_addPhotoUser;
    ImageView pic_photo_user;
    EditText nama, kelamin;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String password_key = "passwordkey", email_key = "emailkey";
    String username_key_new, password_key_new, email_key_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_two);

        getUsernameLocal();

        kembali = findViewById(R.id.btn_kembalikeisgnupone);
        btn_daftar = findViewById(R.id.btn_selanjutnyakeexplore);
        btn_addPhotoUser = findViewById(R.id.btn_tambahfotouser);
        pic_photo_user = findViewById(R.id.pic_photo_regis_user);
        nama = findViewById(R.id.et_nama);
        kelamin = findViewById(R.id.et_kelamin);

        btn_addPhotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPhoto();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ubah state menjadi loading
                btn_daftar.setEnabled(false);
                btn_daftar.setText("Loading ...");
                //menyimpan kedatabase
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

                //validasi file (apakah ada?)
                if (photo_location != null && (nama.getText().toString() != null && kelamin.getText().toString() != null)){
                    final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uri_photo = uri.toString();
                                    reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                    reference.getRef().child("nama_lengkap").setValue(nama.getText().toString());
                                    reference.getRef().child("jenis_kelamin").setValue(kelamin.getText().toString());
                                    reference.getRef().child("username").setValue(username_key_new);
                                    reference.getRef().child("password").setValue(password_key_new);
                                    reference.getRef().child("email_adress").setValue(email_key_new);
                                    reference.getRef().child("user_balance").setValue(250000);
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Intent gotoexplore = new Intent(SignUpTwoActivity.this, SuccesRegisActivity.class);
                                    startActivity(gotoexplore);
                                    finish();
                                }
                            });

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            Intent gotoexplore = new Intent(SignUpTwoActivity.this, SuccesRegisActivity.class);
//                            startActivity(gotoexplore);
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi loading
                    btn_daftar.setEnabled(true);
                    btn_daftar.setText("Daftar Sekarang");
                }

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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_photo_user);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        password_key_new = sharedPreferences.getString(password_key, "");
        email_key_new = sharedPreferences.getString(email_key, "");
        Toast.makeText(getApplicationContext(), "username : " + username_key_new +" password : "+password_key_new+" email : " + email_key_new,Toast.LENGTH_SHORT).show();;
    }
}
