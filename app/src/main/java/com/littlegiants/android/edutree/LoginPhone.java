package com.littlegiants.android.edutree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LoginPhone extends AppCompatActivity {

    private EditText mphone;
    private EditText motp;
    private Button mphone_btn;
    private Button mverify_btn;
    private ProgressDialog mprogress;

    private String mVerificationId;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    View focusview = null;
    boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mprogress = new ProgressDialog(this);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        mphone = (EditText) findViewById(R.id.phone_no);
        motp = (EditText) findViewById(R.id.OTP_no);
        mphone_btn = (Button) findViewById(R.id.auth_btn);
        mverify_btn = (Button) findViewById(R.id.auth_btn_otp);

        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(LoginPhone.this, MainActivity.class));
                    mprogress.dismiss();

                }
            }
        };

        mphone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthentication();
            }
        });

        mverify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprogress.setMessage("Signing In...");
                mprogress.show();
                mprogress.setCanceledOnTouchOutside(false);
                mprogress.setCancelable(false);

                String otp = motp.getText().toString();

                if (TextUtils.isEmpty(otp)) {

                    mprogress.dismiss();

                } else
                {
                    SignIn();
                }

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    private void PhoneAuthentication() {

        String Number = "+91"+mphone.getText().toString();

        if (TextUtils.isEmpty(Number)) {

            mphone.setError("Please fill your No");
            focusview = mphone;
            cancel = true;

        } else {

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    Number,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                            signInWithCredential(phoneAuthCredential);

                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {

                        }

                        @Override
                        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);

                            mVerificationId = s;
                        }
                    });

        }

    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {

                    Toast.makeText(LoginPhone.this, "Error", Toast.LENGTH_LONG).show();
                    mprogress.dismiss();

                } else {

                    Toast.makeText(LoginPhone.this, "Welcome" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
                    mprogress.dismiss();

                }


            }
        });

    }

    private void SignIn() {

        String code = motp.getText().toString();

        if (TextUtils.isEmpty(code)) {

            motp.setError("Please fill Otp");
            focusview = mphone;
            cancel = true;

        } else {

            signInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, code));

        }

    }
}



