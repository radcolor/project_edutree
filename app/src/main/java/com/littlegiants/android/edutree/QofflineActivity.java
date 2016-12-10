package com.littlegiants.android.edutree;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import me.anwarshahriar.calligrapher.Calligrapher;

public class QofflineActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    TextView name,question,o1,o2,o3,o4;
    JSONObject jsonObj;
    int i;
    String document_name = "Quizango_GK_j1.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qu_ac);

        name = findViewById(R.id.user_namequiz);
        question = findViewById(R.id.quiz_question);
        o1 = findViewById(R.id.quiz_o1);
        o2 = findViewById(R.id.quiz_o2);
        o3 = findViewById(R.id.quiz_o3);
        o4 = findViewById(R.id.quiz_o4);

        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        LoadQuestions();

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf",true);

        final ImageView imageView1 = findViewById(R.id.qui_move);
        final ImageView imageView2 = findViewById(R.id.qui_move2);

        final ImageView imageViews1 = findViewById(R.id.sky1);
        final ImageView imageViews2 = findViewById(R.id.sky2);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f,1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
              final float progress = (float) animation.getAnimatedValue();
              final float width = imageView1.getWidth();
              final float translationX = width*progress;
              imageView1.setTranslationX(translationX);
              imageView2.setTranslationX(translationX-width);
              imageViews1.setTranslationX(translationX);
              imageViews2.setTranslationX(translationX-width);
          }
      });
        animator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //RunAnimation();
            }
        },400);

    }

    private void LoadQuestions(){

        try {

            JSONObject jsonObjMain = new JSONObject(readJSONFromAsset(document_name));
            JSONArray jsonArray = jsonObjMain.getJSONArray("questions");

            ArrayList<Integer> list = getRandomNonRepeat(1,0,7);
            for(i = 0 ; i<list.size() ; i++){
                /*item = item + list.get(i);
                if(i != list.size() -1){
                    item = item + ", ";
                }
                textView11.setText(item);*/
                Toast.makeText(QofflineActivity.this," "+list.get(i),Toast.LENGTH_LONG).show();
                jsonObj = jsonArray.getJSONObject(list.get(i));
            }

            int id = jsonObj.getInt("id");
            String ques = jsonObj.getString("question");
            String optionA = jsonObj.getString("optionA");
            String optionB = jsonObj.getString("optionB");
            String optionC = jsonObj.getString("optionC");
            String optionD = jsonObj.getString("optionD");
            String rightAnswer = jsonObj.getString("rightAnswer");

            String[] shuffle_options = new String[]{optionA, optionB, optionC, optionD};
            List<String> shuffle_list = new ArrayList<>();
            Collections.addAll(shuffle_list,shuffle_options);
            Collections.shuffle(shuffle_list);

            question.setText(ques);
            o1.setText(shuffle_list.get(0));
            o2.setText(shuffle_list.get(1));
            o3.setText(shuffle_list.get(2));
            o4.setText(shuffle_list.get(3));

            //}

        } catch (JSONException e) {
            e.printStackTrace();
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
                        Intent intent = new Intent(QofflineActivity.this,QuizActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mMediaPlayer.stop();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent);
                        mMediaPlayer.stop();
                    }
                }).create().show();
    }

    /*private void RunAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale);
        a.reset();
        TextView tv = findViewById(R.id.quiz_question);
        tv.clearAnimation();
        tv.startAnimation(a);
    }*/

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

    public String readJSONFromAsset(String document) {
        String json = null;
        try {
            InputStream is = getAssets().open(document);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int getRandomInt(int min, int max){

        Random random = new Random();
        return random.nextInt((max-min)+1)+min;

    }

    public static ArrayList<Integer> getRandomNonRepeat(int size, int min, int max){

        ArrayList<Integer> numbers = new ArrayList<>();
        while(numbers.size()<size){
            int random = getRandomInt(min, max);
            if (!numbers.contains(random)){
                numbers.add(random);
            }
        }
        return numbers;
    }


}
