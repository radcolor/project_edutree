package com.littlegiants.android.edutree;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import me.anwarshahriar.calligrapher.Calligrapher;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quiz);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        Toolbar toolbar = findViewById(R.id.quiz_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = findViewById(R.id.name_quiz);
        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        calligrapher.setFont(findViewById(R.id.quiz_tool), "truenolt.otf");

        Button button = findViewById(R.id.playQuiz);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.playQuiz:

                Intent intent = new Intent(QuizActivity.this,QofflineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }

    }

    @Override
    protected void onStart() {

        mMediaPlayer  = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.waiting_christmas_sm);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Close")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mMediaPlayer.stop();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent);
                        mMediaPlayer.stop();
                    }
                }).create().show();
    }

    @Override
    protected void onPause() {
        mMediaPlayer.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mMediaPlayer.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mMediaPlayer.stop();
        super.onDestroy();
    }

}
