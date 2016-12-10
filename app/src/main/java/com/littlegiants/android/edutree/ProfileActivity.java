package com.littlegiants.android.edutree;

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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.lucasurbas.listitemview.ListItemView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.anwarshahriar.calligrapher.Calligrapher;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference myRef;
    DatabaseHelper myDb;
    ListItemView name,email,stan,school;
    TextView pro_name,pro_school;
    EditText mName,mClass,mSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.pro_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");

        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true);

        //String user_name = "Shashank Baghel";
        //String user_email = "Shashankbaghel@edutree.com";

        String usr_pic = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        String user_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        String user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

        myDb = new DatabaseHelper(this);

        final ImageView imageView = findViewById(R.id.usr_img);
        Picasso.with(this).load(usr_pic).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(createBitmapWithRoundCorners(bitmap,2f));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        pro_name = findViewById(R.id.pro_name);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        stan = findViewById(R.id.user_class);
        school = findViewById(R.id.user_school);

        name.setOnClickListener(this);
        stan.setOnClickListener(this);
        school.setOnClickListener(this);

        name.setOnClickListener(this);

        pro_name.setText(user_name);
        name.setTitle(user_name);
        email.setTitle(user_email);
        pro_school = findViewById(R.id.user_appbar_school);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);
        calligrapher.setFont(toolbar,"truenolt.otf");

        //calligrapher.setFont(findViewById(R.id.pro_tool), "truenolt.otf");

        FloatingActionButton fab = findViewById(R.id.editfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, AvatarActivity.class));
            }
        });

        SqlClass();
        SqlSchool();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(ProfileActivity.this,findViewById(R.id.usr_img),"userimage");
                startActivity(intent,optionsCompat.toBundle());
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void onClick(View view) {

        int btnId = view.getId();
        switch (btnId) {

            case R.id.usr_img:

                break;
            case R.id.user_name:

                break;
            case R.id.user_email:

                break;
            case R.id.user_class:

                break;
            case R.id.user_school:

                break;
        }
    }

    private void SqlClass(){

        Cursor res = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append(res.getString(3));
        }
        stan.setTitle(buffer.toString());
    }

    private void SqlSchool(){

        Cursor res = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append(res.getString(4));
        }
        school.setTitle(buffer.toString());
        pro_school.setText(buffer.toString());
    }

}
