package com.littlegiants.android.edutree.eduPHYSICS;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.littlegiants.android.edutree.Fragment_phy11;
import com.littlegiants.android.edutree.R;
import com.littlegiants.android.edutree.SamplepaperActivity;
import com.littlegiants.android.edutree.Storage_PDFActivity;

import java.io.File;

public class PhysicsActivity extends AppCompatActivity implements View.OnClickListener{

    CardView course,notes,tests,sp;

    private Fragment mfragment;
    private FragmentManager mfragmentManager;

    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physics);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        notes = (CardView) findViewById(R.id.phy_notes);
        notes.setOnClickListener(this);
        tests = (CardView) findViewById(R.id.phy_tests);
        tests.setOnClickListener(this);
        sp = (CardView) findViewById(R.id.phy_sp);
        sp.setOnClickListener(this);

        mfragmentManager = getSupportFragmentManager();

        mprogress = new ProgressDialog(this);

    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();
        switch (btnId) {

            case R.id.phy_notes:

                File file = Environment.getExternalStorageDirectory();
                File myFile = new File(file.getAbsolutePath() + "/Edutree/Revision Notes/Class 11/Physics-Revision-Notes11.pdf");


                if(myFile.exists()){

                    Intent intent12 = new Intent(PhysicsActivity.this, Storage_PDFActivity.class);
                    intent12.putExtra("path", "/Edutree/Revision Notes/Class 11/Physics-Revision-Notes11.pdf");
                    startActivity(intent12);

                }else{

                    Uri notes = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2011%2FCBSE%20Revision%20Notes%2FSESSION%202017-18%20%20CLASS%20XI%20PHYSICS%20STUDY%20MATERIAL.pdf?alt=media&token=bcac9453-86a5-48e5-93c2-ba4768df4bb5");
                    downloadfile(notes);

                }

                break;

            case R.id.phy_sp:

                Intent intent = new Intent(PhysicsActivity.this, SamplepaperActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(PhysicsActivity.this,findViewById(R.id.img_phy_sp),"sp_img");
                startActivity(intent,optionsCompat.toBundle());

                break;


        }
    }

    private void downloadfile(Uri uri) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/Edutree/Revision Notes/Class 11");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Downloading Physics Notes");
        request.setDescription("Please wait while downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir("/Edutree/Revision Notes/Class 11", "Physics-Revision-Notes11.pdf");

        Long reference = downloadManager.enqueue(request);

    }


}

