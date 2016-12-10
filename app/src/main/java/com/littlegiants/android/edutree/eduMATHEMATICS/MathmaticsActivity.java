package com.littlegiants.android.edutree.eduMATHEMATICS;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.littlegiants.android.edutree.R;
import com.littlegiants.android.edutree.SamplepaperActivity;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MathmaticsActivity extends AppCompatActivity implements View.OnClickListener {

    CardView sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathmatics);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        sp = findViewById(R.id.maths_sp);
        sp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        int btnId = view.getId();
        switch (btnId) {

            case R.id.maths_sp:

                Intent intent = new Intent(MathmaticsActivity.this, SamplepaperActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(MathmaticsActivity.this,findViewById(R.id.img_math_sp),"sp_img");
                startActivity(intent,optionsCompat.toBundle());

                break;

        }
    }
}
