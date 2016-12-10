package com.littlegiants.android.edutree;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.littlegiants.android.edutree.eduBIOLOGY.BiologyActivity;
import com.littlegiants.android.edutree.eduCHEMISTRY.ChemistryActivity;
import com.littlegiants.android.edutree.eduMATHEMATICS.MathmaticsActivity;
import com.littlegiants.android.edutree.eduPHYSICS.PhysicsActivity;

import me.anwarshahriar.calligrapher.Calligrapher;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video);

    Calligrapher calligrapher = new Calligrapher(this);
    calligrapher.setFont(this, "truenolt.otf", true);

      CardView cvmaths = findViewById(R.id.cv_vid_maths);
      cvmaths.setOnClickListener(this);
      CardView cvphysics = findViewById(R.id.cv_vid_physics);
      cvphysics.setOnClickListener(this);
      CardView cvchemistry = findViewById(R.id.cv_vid_chemistry);
      cvchemistry.setOnClickListener(this);
      CardView cvbiology = findViewById(R.id.cv_vid_biology);
      cvbiology.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {

      switch (view.getId()) {
          case R.id.cv_vid_maths:
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      startActivity(new Intent(VideoActivity.this,MathmaticsActivity.class));
                  }
              },150);
              break;
          case R.id.cv_vid_physics:
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      startActivity(new Intent(VideoActivity.this,PhysicsActivity.class));
                  }
              },150);
              break;
          case R.id.cv_vid_chemistry:
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      startActivity(new Intent(VideoActivity.this,ChemistryActivity.class));
                  }
              },150);
              break;
          case R.id.cv_vid_biology:
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      startActivity(new Intent(VideoActivity.this,BiologyActivity.class));
                  }
              },150);
              break;
      }

  }

}







