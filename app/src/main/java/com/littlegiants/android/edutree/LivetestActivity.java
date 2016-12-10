package com.littlegiants.android.edutree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LivetestActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livetest);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();
        switch (btnId) {

            case R.id.cv_maths_11:

                break;
            case R.id.cv_physics_11:

                break;
            case R.id.cv_chemistry_11:

                break;
            case R.id.cv_biology_11:

                break;

        }
    }
}
