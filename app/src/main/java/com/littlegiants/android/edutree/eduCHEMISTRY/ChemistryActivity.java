package com.littlegiants.android.edutree.eduCHEMISTRY;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.littlegiants.android.edutree.R;
import com.littlegiants.android.edutree.SamplepaperActivity;
import com.littlegiants.android.edutree.Storage_PDFActivity;

import java.io.File;

import me.anwarshahriar.calligrapher.Calligrapher;

public class ChemistryActivity extends AppCompatActivity implements View.OnClickListener{

    CardView course,notes,tests,sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        notes = findViewById(R.id.chem_notes);
        notes.setOnClickListener(this);
        tests = findViewById(R.id.chem_tests);
        tests.setOnClickListener(this);
        sp = findViewById(R.id.chem_sp);
        sp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();

        switch (btnId) {

            case R.id.chem_notes:

                File file = Environment.getExternalStorageDirectory();
                File myFile = new File(file.getAbsolutePath() + "/Edutree/Revision Notes/Class 11/Chemistry-Revision-Notes11.pdf");

                if(myFile.exists()){

                    Intent intent12 = new Intent(ChemistryActivity.this, Storage_PDFActivity.class);
                    intent12.putExtra("path", "/Edutree/Revision Notes/Class 11/Chemistry-Revision-Notes11.pdf");
                    startActivity(intent12);

                }else{

                    Uri notes = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2011%2FCBSE%20Revision%20Notes%2FSESSION%202017-18%20CLASS%20XI%20CHEMISTRY%20STUDY%20MATERIAL.pdf?alt=media&token=5508d079-3c53-4238-9e2a-d4f17c15a003");
                    downloadfile(notes);

                }

                break;

            case R.id.chem_sp:

                Intent intent = new Intent(ChemistryActivity.this, SamplepaperActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(ChemistryActivity.this,findViewById(R.id.img_che_sp),"sp_img");
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

        request.setTitle("Downloading Chemistry Notes");
        request.setDescription("Please wait while downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir("/Edutree/Revision Notes/Class 11", "Chemistry-Revision-Notes11.pdf");

        Long reference = downloadManager.enqueue(request);

    }

}

