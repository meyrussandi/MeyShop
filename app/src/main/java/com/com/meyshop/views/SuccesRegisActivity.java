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

public class SuccesRegisActivity extends AppCompatActivity {
    Button btn_gohome;
    Animation btt, ttb, btf;
    ImageView ic_success;
    TextView title, subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_regis);

        //load anim
        btt = AnimationUtils.loadAnimation(this,R.anim.buttomtotop);
        ttb = AnimationUtils.loadAnimation(this,R.anim.toptobuttom);
        btf = AnimationUtils.loadAnimation(this, R.anim.app_splash_slogan);

        //load element
        ic_success = findViewById(R.id.imageView4);
        title = findViewById(R.id.textView3);
        subtitle = findViewById(R.id.textView4);
        btn_gohome = findViewById(R.id.btn_explore);

        //run animation
        ic_success.startAnimation(ttb);
        title.startAnimation(ttb);
        subtitle.startAnimation(btt);
        btn_gohome.startAnimation(btf);

        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccesRegisActivity.this, HomeActivity.class);
                startActivity(gotohome);
                finish();
            }
        });
    }
}
