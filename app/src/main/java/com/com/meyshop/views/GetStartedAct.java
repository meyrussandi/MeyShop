package com.com.meyshop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.meyshop.R;

public class GetStartedAct extends AppCompatActivity {
    Button signin,signup;
    Animation ttb,btt;
    ImageView logo;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        ttb = AnimationUtils.loadAnimation(this,R.anim.toptobuttom);
        btt = AnimationUtils.loadAnimation(this,R.anim.buttomtotop);

        //load element
        logo = findViewById(R.id.imageView);
        slogan = findViewById(R.id.textView);
        signin = findViewById(R.id.btn_masuk);
        signup = findViewById(R.id.btn_daftar);

        //run animation
        logo.startAnimation(ttb); slogan.startAnimation(ttb);
        signin.startAnimation(btt); signup.startAnimation(btt);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosignin = new Intent(GetStartedAct.this, SigninActivity.class);
                startActivity(gotosignin);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosignup = new Intent(GetStartedAct.this, SignUpOneActivity.class);
                startActivity(gotosignup);
            }
        });

    }
}
