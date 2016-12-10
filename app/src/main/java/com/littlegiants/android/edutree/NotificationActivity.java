package com.littlegiants.android.edutree;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.net.Uri;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.littlegiants.android.edutree.com.littlegiants.android.edutree.blog.BloggerAPI;
import com.littlegiants.android.edutree.com.littlegiants.android.edutree.blog.PostAdapter;
import com.littlegiants.android.edutree.com.littlegiants.android.edutree.blog.PostList;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private CustomTabsClient mClient;
    TextView head_string;
    RecyclerView recyclerView;
    ProgressDialog mprogress;
    String blog_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mprogress = new ProgressDialog(this);

        mprogress.setMessage("Loading...");
        mprogress.show();
        mprogress.setCanceledOnTouchOutside(false);
        mprogress.setCancelable(false);

        blog_link = "http://littlegiantsdevelopers.blogspot.in";

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        recyclerView = findViewById(R.id.rec_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        getData();
        prefetchContent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.open_blog){
            GoLink(Uri.parse(blog_link));
        }

        return super.onOptionsItemSelected(item);
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
        CustomTabsClient.bindCustomTabsService(NotificationActivity.this,package_name,customTabsServiceConnection);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        builder.setShowTitle(true);
        customTabsIntent.intent.setPackage(package_name);
        builder.setToolbarColor(ContextCompat.getColor(NotificationActivity.this, R.color.colorPrimary));
        builder.setSecondaryToolbarColor(ContextCompat.getColor(NotificationActivity.this, R.color.colorPrimaryDark));
        customTabsIntent.launchUrl(NotificationActivity.this, uri);

    }

    public void prefetchContent(){

        if(mClient !=null ){

            mClient.warmup(0);
            CustomTabsSession customTabsSession = getSession();
            customTabsSession.mayLaunchUrl(Uri.parse(blog_link),null,null);

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

    private void getData()
    {
        Call<PostList> postList = BloggerAPI.getService().getPostList();
        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                recyclerView.setAdapter(new PostAdapter(NotificationActivity.this, list.getItems()));
                mprogress.dismiss();
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Snackbar.make(findViewById(R.id.rec_view),"No Connection",Snackbar.LENGTH_LONG).show();
                mprogress.dismiss();
            }
        });

    }
}

