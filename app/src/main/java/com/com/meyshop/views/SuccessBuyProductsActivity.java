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

public class SuccessBuyProductsActivity extends AppCompatActivity {

    ImageView icon;
    TextView title, subTitle;
    Button btn_viewClothes, btn_myDashboard;
    Animation btt, ttb, btf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_products);

        //load anim
        btt = AnimationUtils.loadAnimation(this,R.anim.buttomtotop);
        ttb = AnimationUtils.loadAnimation(this,R.anim.toptobuttom);
        btf = AnimationUtils.loadAnimation(this, R.anim.app_splash_slogan);

        icon = findViewById(R.id.imageView4);
        title = findViewById(R.id.textView3);
        subTitle = findViewById(R.id.textView4);
        btn_viewClothes = findViewById(R.id.btn_viewClothes);
        btn_myDashboard = findViewById(R.id.btn_myDashboard);

        //run animation
        icon.startAnimation(btf);
        title.startAnimation(ttb);
        subTitle.startAnimation(ttb);
        btn_viewClothes.startAnimation(btt);
        btn_myDashboard.startAnimation(btt);

        btn_viewClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessBuyProductsActivity.this, ProfilActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_myDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessBuyProductsActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}
