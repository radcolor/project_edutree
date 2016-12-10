package com.littlegiants.android.edutree;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userId;
    DatabaseHelper myDb;


    private Button signUp;
    private EditText uname,uemail,upassword,ucon_password;
    private AutoCompleteTextView ugender,ustate,ucity,uschool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myDb = new DatabaseHelper(this);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf",true);

        mAuth = FirebaseAuth.getInstance();
        setAdapter();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                }
            }
        });

        uname = findViewById(R.id.su_name);
        uemail = findViewById(R.id.su_email);
        upassword = findViewById(R.id.su_password);
        ucon_password = findViewById(R.id.su_confrim_password);
        ugender = findViewById(R.id.su_gender);
        ustate = findViewById(R.id.su_state);
        ucity = findViewById(R.id.su_city);
        uschool = findViewById(R.id.su_school);

        signUp = findViewById(R.id.btn_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpEmailPassword();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setAdapter(){

        String[] cities = getResources().getStringArray(R.array.list_of_cities);
        String[] states = getResources().getStringArray(R.array.list_of_states);
        String[] gender = {"Male","Female"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cities);
        AutoCompleteTextView textView =
                findViewById(R.id.su_city);
        textView.setAdapter(adapter);

        ArrayAdapter<String> adapter0 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, states);
        AutoCompleteTextView textView0 =
                findViewById(R.id.su_state);
        textView0.setAdapter(adapter0);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, gender);
        AutoCompleteTextView textView1 =
                findViewById(R.id.su_gender);
        textView1.setAdapter(adapter1);

    }

    private void signUpEmailPassword(){

        final String user_name = uname.getText().toString();
        final String user_email = uemail.getText().toString();
        String user_password = upassword.getText().toString();
        final String user_class = "11";
        final String school = uschool.getText().toString();

            mAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){

                        Toast.makeText(SignUpActivity.this,"Error while Sign Up", Toast.LENGTH_LONG).show();

                    }else{

                        userId = mAuth.getCurrentUser().getUid().toString();

                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(user_name).build();
                        FirebaseUser user = mAuth.getCurrentUser();
                        user.sendEmailVerification();
                        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Profile", "User Profile Updated successfully");
                                    Crashlytics.log("Profile Updated");
                                } else {
                                    Log.d("Error", "error while updating profile");
                                    Crashlytics.log("Error while updating profile");
                                }
                            }
                        });

                        boolean isInserted = myDb.insertData(userId,user_name,user_email,user_class,school);

                        UserInformation userInformation = new UserInformation(userId,user_name,user_email,user_class,school);
                        myRef.child("Users").child(userId).setValue(userInformation);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                Crashlytics.log("Successfully Authenticated");
                                Toast.makeText(SignUpActivity.this, "Welcome "+ FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
                            }
                        },1000);

                    }

                }
            });

    }

    View focusview = null;
    boolean cancel = false;

    private boolean textCheck(String email, String password) {

        if (TextUtils.isEmpty(email)) {

            uemail.setError("Please fill your Email");
            focusview = uemail;
            cancel = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    uemail.setError(null);
                }
            }, 3000);

            return false;
        }

        if (TextUtils.isEmpty(password)) {

            upassword.setError("Please fill your Password");
            focusview = uemail;
            cancel = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    upassword.setError(null);
                }
            }, 3000);

            return false;

        }
        return true;
    }

}





