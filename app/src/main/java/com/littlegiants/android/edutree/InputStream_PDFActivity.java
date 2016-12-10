package com.littlegiants.android.edutree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.anwarshahriar.calligrapher.Calligrapher;

public class InputStream_PDFActivity extends AppCompatActivity {

    private ProgressDialog mprogress;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        Intent i = this.getIntent();
        String pdfurl = i.getStringExtra("url");

        new RetrievePDFStream().execute(pdfurl);

        mprogress = new ProgressDialog(this);
        mprogress.setMessage("Please wait...");
        mprogress.show();
        mprogress.setCanceledOnTouchOutside(false);
        mprogress.setCancelable(false);

        pdfView = (PDFView) findViewById(R.id.pdfview);

    }


    class RetrievePDFStream extends AsyncTask<String, Void, InputStream>
    {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200)
                {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e)
            {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    mprogress.dismiss();
                }
            }).enableAntialiasing(true).load();
        }
    }

}
