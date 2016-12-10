package com.littlegiants.android.edutree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Main2Activity extends AppCompatActivity {

    TextView idt,textView,textView1,textView2,textView3,textView4,textView5;

    int i;
    String item = "";
    JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        idt = findViewById(R.id.json_id);

        TextView textView11 = findViewById(R.id.json_random);

        textView = findViewById(R.id.json_question);
        textView1 = findViewById(R.id.json_o1);
        textView2 = findViewById(R.id.json_o2);
        textView3 = findViewById(R.id.json_o3);
        textView4 = findViewById(R.id.json_o4);
        textView5 = findViewById(R.id.json_answer);

        String document_name = "Quizango_GK_j1.json";

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
                Toast.makeText(Main2Activity.this," "+list.get(i),Toast.LENGTH_LONG).show();

               jsonObj = jsonArray.getJSONObject(list.get(i));
            }

                int id = jsonObj.getInt("id");
                String question = jsonObj.getString("question");
                String optionA = jsonObj.getString("optionA");
                String optionB = jsonObj.getString("optionB");
                String optionC = jsonObj.getString("optionC");
                String optionD = jsonObj.getString("optionD");
                String rightAnswer = jsonObj.getString("rightAnswer");

                idt.setText(""+id);
                textView.setText(question);
                textView1.setText(optionA);
                textView2.setText(optionB);
                textView3.setText(optionC);
                textView4.setText(optionD);
                textView5.setText(rightAnswer);

            //}

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
