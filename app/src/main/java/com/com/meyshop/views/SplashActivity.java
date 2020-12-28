package com.com.meyshop.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.meyshop.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Animation app_splash, app_splash_slogan;
    ImageView app_logo;
    TextView app_slogan;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String password_key = "passwordkey", email_key = "emailkey";
    String username_key_new, password_key_new, email_key_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.toptobuttom);
        app_splash_slogan = AnimationUtils.loadAnimation(this, R.anim.app_splash_slogan);

        //load element
        app_logo = findViewById(R.id.iv_logo);
        app_slogan = findViewById(R.id.tv_slogan);

        //run animation
        app_logo.startAnimation(app_splash);
        app_slogan.startAnimation(app_splash_slogan);

        getUsernameLocal();

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

        Log.d("cek username", username_key_new);
        Handler handler = new Handler();
        if(username_key_new.isEmpty()){
            //setting timer 2s
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //merubah activity ke activity lain
                    Intent gogetstarted = new Intent(SplashActivity.this, GetStartedAct.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000); //2000ms = 2s
        }else {
            //setting timer 2s
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //merubah activity ke activity lain
                    Intent gohome = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(gohome);
                    finish();
                }
            }, 2000); //2000ms = 2s
        }
    }

}
