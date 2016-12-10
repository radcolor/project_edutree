package com.littlegiants.android.edutree.eduNOTES;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.littlegiants.android.edutree.R;
import com.littlegiants.android.edutree.Storage_PDFActivity;
import com.littlegiants.android.edutree.eduBOOK.EbooksActivity;
import com.littlegiants.android.edutree.eduBOOK.FragmentEleven;

import java.io.File;

import me.anwarshahriar.calligrapher.Calligrapher;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener{

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ActivityCompat.requestPermissions(NotesActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        Toolbar toolbar = findViewById(R.id.toolbar_not);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        calligrapher.setFont(collapsingToolbarLayout,"truenolt.otf");
        calligrapher.setFont(toolbar,"truenolt.otf");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentConstructor();

    }

    public void fragmentConstructor(){

        fragment = new FragmentEleven();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linrep_note,fragment).commit();

    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();

        switch (btnId) {

            case R.id.not11_cv1:

                String path = "/Edutree/Class - 11/Revision Notes";
                String book = "IP-Revision-Notes.pdf";
                if(checkFile(path+book)){
                    pdfView(path+book);
                }else{
                    Uri uriip = Uri.parse(getString(R.string.notes_ip_11));
                    downloadfile(uriip,book,path);
                }
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }

    }

    public boolean checkFile(String path){

        File file = Environment.getExternalStorageDirectory();
        File myFile = new File(file.getAbsolutePath() + path);

        if(myFile.exists()){
            return true;
        }else if(!myFile.exists()){
            return false;
        }
        return false;
    }

    private void downloadfile(Uri uri, String book, String path) {

        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if (isSDPresent) {

            File direct = new File(Environment.getExternalStorageDirectory() + path);
            if (!direct.exists()) {
                direct.mkdirs();
            }

        } else {

            File file = getDir(path, Context.MODE_PRIVATE);
            if(!file.exists()){
                file.mkdirs();

            }

        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Downloading Cbse ebooks");
        request.setDescription("Please wait while your book is downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);

        request.setDestinationInExternalPublicDir(path, book);

        Long reference = downloadManager.enqueue(request);

    }

    private void pdfView(String pdf_path){

        Intent intent = new Intent(NotesActivity.this, Storage_PDFActivity.class);
        intent.putExtra("path", pdf_path);
        startActivity(intent);

    }


}
