package com.littlegiants.android.edutree;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomTabsClient mClient;

    ImageView view1,view2,view3,view4,view5;

    String iamFB,iamTW,iamGIT,iamINSTA,iamGP,iamSOF
            ,lgdFB,lgdINSTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        iamFB = "";
        iamTW = "";
        iamGIT = "";
        iamINSTA = "";
        iamGP = "";
        iamSOF = "";

        lgdFB = "";
        lgdINSTA = "";

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        /*DatabaseEduNotes databaseEduNotes = new DatabaseEduNotes(this);
        boolean isInserted = databaseEduNotes.insertData("ID1","My Tittle", "My Notes", "Today", "This Time");

        if(isInserted == true){
            Toast.makeText(AboutActivity.this,"Inserted",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(AboutActivity.this,"!Inserted",Toast.LENGTH_LONG).show();
        }*/

        view1 = findViewById(R.id.facebook);
        view2 = findViewById(R.id.twitter);
        view3 = findViewById(R.id.instagram);
        view4 = findViewById(R.id.googleplus);
        view5 = findViewById(R.id.github);

        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);

        prefetchContent();

    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();
        switch (btnId) {

            case R.id.facebook:
                GoLink(Uri.parse(iamFB));
                break;
            case R.id.instagram:
                GoLink(Uri.parse(iamINSTA));
                break;
            case R.id.twitter:
                GoLink(Uri.parse(iamTW));
                break;
            case R.id.github:
                GoLink(Uri.parse(iamGIT));
                break;
            case R.id.googleplus:
                //GoLink(Uri.parse(iamGP));
                break;

        }
    }
    private void GoLink(Uri uri){

        CustomTabsServiceConnection customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                mClient = client;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };
        String package_name = "com.android.chrome";
        CustomTabsClient.bindCustomTabsService(AboutActivity.this,package_name,customTabsServiceConnection);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        builder.setShowTitle(true);
        customTabsIntent.intent.setPackage(package_name);
        builder.setToolbarColor(ContextCompat.getColor(AboutActivity.this, R.color.colorPrimary));
        builder.setSecondaryToolbarColor(ContextCompat.getColor(AboutActivity.this, R.color.colorPrimaryDark));
        customTabsIntent.launchUrl(AboutActivity.this, uri);

    }

    public void showMessage(String Tittle, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Tittle);
        builder.setMessage(Message);
        builder.show();
    }

    public void prefetchContent(){

        if(mClient !=null ){

            mClient.warmup(0);
            CustomTabsSession customTabsSession = getSession();
            customTabsSession.mayLaunchUrl(Uri.parse(iamFB),null,null);
            customTabsSession.mayLaunchUrl(Uri.parse(iamTW),null,null);
            customTabsSession.mayLaunchUrl(Uri.parse(iamGIT),null,null);
            customTabsSession.mayLaunchUrl(Uri.parse(iamINSTA),null,null);

            customTabsSession.mayLaunchUrl(Uri.parse(lgdINSTA),null,null);

        }

    }

    private CustomTabsSession getSession(){

        return mClient.newSession(new CustomTabsCallback(){
            @Override
            public void onNavigationEvent(int navigationEvent, Bundle extras) {
                super.onNavigationEvent(navigationEvent, extras);
            }
        });
    }

}
