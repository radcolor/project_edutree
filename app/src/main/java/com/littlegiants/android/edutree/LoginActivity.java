package com.littlegiants.android.edutree;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    // Email Password
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    RelativeLayout LoginActivity;
    private ProgressDialog mprogress;

    //Database
    private String userId;
    FirebaseUser user;
    DatabaseHelper myDb;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    //Google Sign In
    SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mGoogleBtn = findViewById(R.id.googleSignIn);
        mGoogleBtn.setOnClickListener(this);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        mAuth = FirebaseAuth.getInstance();

        final Typeface mytypeface = Typeface.createFromAsset(getAssets(), "truenoultlt.otf");
        TextView textView = findViewById(R.id.tit);
        textView.setTypeface(mytypeface);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myDb = new DatabaseHelper(this);

        //Configure Google Sign In

        mprogress = new ProgressDialog(this);

        LoginActivity = findViewById(R.id.activity_login);
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    mprogress.dismiss();
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiclient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        mprogress.dismiss();
                        Toast.makeText(LoginActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        Button button = findViewById(R.id.signout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                myDb.deleteData(userID);
                FirebaseAuth.getInstance().signOut();

                if(mGoogleApiclient != null && mGoogleApiclient.isConnected()) {
                    mGoogleApiclient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {

                        }
                    });
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();
        switch (btnId) {

            case R.id.googleSignIn:

                if (isNetworkAvailable()) {
                    mprogress.setMessage("Signing In...");
                    mprogress.show();
                    mprogress.setCanceledOnTouchOutside(false);
                    mprogress.setCancelable(false);
                    //signIn();
                    startActivity(new Intent(com.littlegiants.android.edutree.LoginActivity.this,MainActivity.class));
                    Crashlytics.log("User Authentication Begin with Google");
                } else {
                    mprogress.dismiss();
                    Toast.makeText(LoginActivity.this, "No Network Available", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiclient);
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else{
                mprogress.dismiss();
                Toast.makeText(LoginActivity.this, "Error",Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)

                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                fireDBtoSQL();
                mprogress.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mprogress.dismiss();
                Toast.makeText(LoginActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fireDBtoSQL(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getfbdbDATA(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "fbtosql failed.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getfbdbDATA(DataSnapshot dataSnapshot){

        for (DataSnapshot ds : dataSnapshot.getChildren()){

            UserInformation uInfo = new UserInformation();

            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            uInfo.setName(ds.child(userId).getValue(UserInformation.class).getName());
            uInfo.setEmail(ds.child(userId).getValue(UserInformation.class).getEmail());
            uInfo.setclass(ds.child(userId).getValue(UserInformation.class).getclass());
            uInfo.setSchool(ds.child(userId).getValue(UserInformation.class).getSchool());

            String name = uInfo.getName().toString();
            String email = uInfo.getEmail().toString();
            String cclass = uInfo.getclass().toString();
            String school = uInfo.getSchool().toString();

            if(name.isEmpty() && email.isEmpty() && cclass.isEmpty() && school.isEmpty()){

                Intent intent = new Intent(LoginActivity.this, LoggedClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }else{

                myDb.insertData(userId,uInfo.getName(),uInfo.getEmail(),uInfo.getclass(),uInfo.getSchool());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        }
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();

    }

}

