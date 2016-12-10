package com.littlegiants.android.edutree;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CbseUpdateActivity extends AppCompatActivity {

    String name;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbseupdate);

        EditText editText = (EditText) findViewById(R.id.fb_name);
        name = editText.getText().toString();

        user =  FirebaseAuth.getInstance().getCurrentUser();

        Button button = (Button) findViewById(R.id.save_fb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();

                user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(CbseUpdateActivity.this, "Profile Updated" , Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });




    }
}
