package com.littlegiants.android.edutree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LoggedClientActivity extends AppCompatActivity{

    EditText cClass, cSchool;
    Button button;

    DatabaseHelper myDb;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        myDb = new DatabaseHelper(this);

        cClass = findViewById(R.id.client_class);
        cSchool = findViewById(R.id.client_school);

        button = findViewById(R.id.btn_client);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
                String useremail = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                String userclass = cClass.getText().toString();
                String userschool = cSchool.getText().toString();

                if(!userclass.isEmpty() && !userschool.isEmpty()){

                    UserInformation information = new UserInformation(userid,username,useremail,userclass,userschool);
                    mRef.child("users").child(userid).setValue(information);
                    myDb.insertData(userid,username,useremail,userclass,userschool);

                    Intent intent = new Intent(LoggedClientActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }else{


                }

            }
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

    }
}
