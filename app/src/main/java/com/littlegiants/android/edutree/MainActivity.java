package com.littlegiants.android.edutree;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.littlegiants.android.edutree.eduBIOLOGY.BiologyActivity;
import com.littlegiants.android.edutree.eduBOOK.EbooksActivity;
import com.littlegiants.android.edutree.eduCHEMISTRY.ChemistryActivity;
import com.littlegiants.android.edutree.eduMATHEMATICS.MathmaticsActivity;
import com.littlegiants.android.edutree.eduNOTES.NotesActivity;
import com.littlegiants.android.edutree.eduPHYSICS.PhysicsActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CardView cv_mathematics,cv_physics,cv_chemistry,cv_biology,cv_quiz;
    private GoogleApiClient mGoogleApiclient;
    DatabaseHelper myDb;

    LinearLayout content_main;
    TextView unamehead, content_name, uemailhead, pro_name,main_school;

    private FirebaseAnalytics mFirebaseAnalytics;

    private CustomTabsClient mClient;

    FirebaseAuth mAuth;

    String user_name,user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarfade);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiclient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        myDb = new DatabaseHelper(this);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Crashlytics.log("MainActivity created");

        content_main = findViewById(R.id.content_main);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String user_pic = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        user_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                content_name = findViewById(R.id.content_name);
                content_name.setText(user_name);

            }
        },400);

        //String user_image = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        Typeface mytypeface = Typeface.createFromAsset(getAssets(), "truenolt.otf");

        unamehead = headerView.findViewById(R.id.unamehead);
        unamehead.setTypeface(mytypeface);
        unamehead.setText(user_name);
        uemailhead = headerView.findViewById(R.id.uemailhead);
        uemailhead.setTypeface(mytypeface);
        uemailhead.setText(user_email);

        final ImageView imageView_nav = headerView.findViewById(R.id.nav_img);
        final ImageView imageView = findViewById(R.id.user_image);

        Picasso.with(this).load(user_pic).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(createBitmapWithRoundCorners(bitmap,2f));
                imageView_nav.setImageBitmap(createBitmapWithRoundCorners(bitmap,2f));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        //SetSql();
        //unamehead.setText(user_name);
        //SetSqlemail();
        //uemailhead.setText(user_email);

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...

            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                .makeSceneTransitionAnimation(MainActivity.this,findViewById(R.id.user_image),"userimage");
                        startActivity(intent,optionsCompat.toBundle());
                    }
                },400);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prefetchContent();
                SqlSchool();
            }
        },500);
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return createBitmapWithRoundCorners(myBitmap,2f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    public static Bitmap createBitmapWithRoundCorners(Bitmap bitmap,
                                                      float factor) {

        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, width, height);
        RectF roundCornerFrameRect = new RectF(rect);
        float cornerRadius = width / factor;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawRoundRect(roundCornerFrameRect, cornerRadius, cornerRadius,
                paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return result;
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "truenolt.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Close")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }).create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        cv_mathematics = findViewById(R.id.cv_mathmatics);
        cv_physics = findViewById(R.id.cv_physics);
        cv_chemistry = findViewById(R.id.cv_chemistry);
        cv_biology = findViewById(R.id.cv_biology);
        cv_quiz = findViewById(R.id.cv_quiz);

        cv_mathematics.setOnClickListener(this);
        cv_physics.setOnClickListener(this);
        cv_chemistry.setOnClickListener(this);
        cv_biology.setOnClickListener(this);
        cv_quiz.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.abt) {
             startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        if (id == R.id.setmain) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        }

        if (id == R.id.logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to log out!")

                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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

                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }

        if (id == R.id.noty){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,NotificationActivity.class));
                }
            },100);

        }

        return super.onOptionsItemSelected(item);
    }

    private void SqlSchool(){

        Cursor res = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append(res.getString(4));
        }
         main_school = findViewById(R.id.user_main_school);
         main_school.setText(buffer.toString());
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.letsstart) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,AnalysisActivity.class));
                }
            },380);

        /*} else if (id == R.id.chvid) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,VideoActivity.class));
                }
            },380);*/

        } else if (id == R.id.cbsesyl) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,SyllabusActivity.class));
                }
            },380);

        } else if (id == R.id.sampp) {
              new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,SamplepaperActivity.class));
                }
            },380);

        } /*else if (id == R.id.chtest) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,LivetestActivity.class));
                }
            },380);


        }*/ else if (id == R.id.rev_note) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,NotesActivity.class));
                }
            },380);

        } else if (id == R.id.myn) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,MyNotesActivity.class));
                }
            },380);

        } else if (id == R.id.mbook) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,EbooksActivity.class));
                }
            },380);

        } else if (id == R.id.quit) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,SplashQuizActivity.class));
                }
            },380);

        } else if (id == R.id.Boardre) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    String url = "http://www.cbseresults.nic.in";

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
                    CustomTabsClient.bindCustomTabsService(MainActivity.this,package_name,customTabsServiceConnection);

                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    builder.setShowTitle(true);
                    customTabsIntent.intent.setPackage(package_name);
                    builder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    builder.setSecondaryToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));

                }
            },380);

        } /*else if (id == R.id.Boardreg) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //startActivity(new Intent(MainActivity.this,CbseUpdateActivity.class));
                }
            },380);

        }*/else if (id == R.id.examsc) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,ComExamActivity.class));
                }
            },380);

        } else if (id == R.id.settings) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                }
            },380);

        } else if (id == R.id.invite) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }
            },380);

        } else if (id == R.id.feedback) {

            Intent i = new Intent(Intent.ACTION_SEND);
            //i.setPackage("com.google.android.gm");
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"askEdu@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Feedback to the Developer");
            i.putExtra(Intent.EXTRA_TEXT, "Type your reviews, features, and what you like about #askedu\n" + DeviceInformation.getInfosAboutDevice(this));
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Snackbar.make(content_main, "There is no Email clients installed ", Snackbar.LENGTH_SHORT).show();
            }

             } else if (id == R.id.about) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this,AboutActivity.class));
                }
            },380);

             }
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        public void prefetchContent(){

            String url = "http://www.cbseresults.nic.in";

            if(mClient !=null ){
                mClient.warmup(0);
                CustomTabsSession customTabsSession = getSession();
                customTabsSession.mayLaunchUrl(Uri.parse(url),null,null);
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

    @Override
    public void onClick(View v) {

        int item = v.getId();

        switch (item) {

            case R.id.cv_mathmatics:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, MathmaticsActivity.class);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                },50);
                break;

            case R.id.cv_physics:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, PhysicsActivity.class);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                },50);
                break;

            case R.id.cv_chemistry:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ChemistryActivity.class);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                },50);
                break;

            case R.id.cv_biology:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(
                                MainActivity.this, BiologyActivity.class);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                },50);
                break;

            case R.id.cv_quiz:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, SplashQuizActivity.class);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                },50);
                break;
        }

    }

    private void SetSql(){

            Cursor res = myDb.getAllData();

            StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()){
                buffer.append(res.getString(1));
            }

            content_name.setText(buffer.toString());
            unamehead.setText(buffer.toString());

        }

    private void SetSqlemail(){

        Cursor res = myDb.getAllData();

        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()){
            buffer.append(res.getString(2));
        }

        uemailhead.setText(buffer.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
