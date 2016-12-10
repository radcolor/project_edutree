package com.littlegiants.android.edutree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Storage_PDFActivity extends AppCompatActivity {

    private ProgressDialog mprogress;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storagepdf);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        Intent i = this.getIntent();
        String pdf_ex = i.getStringExtra("path");

        File file = Environment.getExternalStorageDirectory();
        File myFile = new File(file.getAbsolutePath() + pdf_ex);

        mprogress = new ProgressDialog(this);
        mprogress.setMessage("Please wait...");
        mprogress.show();
        mprogress.setCanceledOnTouchOutside(false);
        mprogress.setCancelable(false);

        pdfView = findViewById(R.id.pdfView_ex);

        pdfView.fromFile(myFile).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                mprogress.dismiss();
            }
        }).load();

    }
}
