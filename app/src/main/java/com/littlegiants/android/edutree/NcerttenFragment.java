package com.littlegiants.android.edutree;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucasurbas.listitemview.ListItemView;

import java.io.File;

import me.anwarshahriar.calligrapher.Calligrapher;

public class NcerttenFragment extends Fragment implements View.OnClickListener{

    ListItemView maths10_pdf, science10_pdf, economics10_pdf, geography10_pdf, ps10_pdf, history10_pdf, english10_pdf,
            hindi1_10_pdf, hindi2_10_pdf, hindi3_10_pdf, hindi4_10_pdf, sanskrit10_pdf;

    public NcerttenFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ten, container, false);

        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(v.findViewById(R.id.maths10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.science10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.economics10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.geography10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.ps10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.history10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.english10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi1_10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi2_10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi3_10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi4_10), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.sanskrit10), "truenolt.otf");


        maths10_pdf = (ListItemView) v.findViewById(R.id.maths10);
        science10_pdf = (ListItemView) v.findViewById(R.id.science10);
        economics10_pdf = (ListItemView) v.findViewById(R.id.economics10);
        geography10_pdf = (ListItemView) v.findViewById(R.id.geography10);
        ps10_pdf = (ListItemView) v.findViewById(R.id.ps10);
        history10_pdf = (ListItemView) v.findViewById(R.id.history10);
        english10_pdf = (ListItemView) v.findViewById(R.id.english10);
        hindi1_10_pdf = (ListItemView) v.findViewById(R.id.hindi1_10);
        hindi2_10_pdf = (ListItemView) v.findViewById(R.id.hindi2_10);
        hindi3_10_pdf = (ListItemView) v.findViewById(R.id.hindi3_10);
        hindi4_10_pdf = (ListItemView) v.findViewById(R.id.hindi4_10);
        sanskrit10_pdf = (ListItemView) v.findViewById(R.id.sanskrit10);

        maths10_pdf.setOnClickListener(this);
        science10_pdf.setOnClickListener(this);
        economics10_pdf.setOnClickListener(this);
        geography10_pdf.setOnClickListener(this);
        ps10_pdf.setOnClickListener(this);
        history10_pdf.setOnClickListener(this);
        english10_pdf.setOnClickListener(this);
        hindi1_10_pdf.setOnClickListener(this);
        hindi2_10_pdf.setOnClickListener(this);
        hindi3_10_pdf.setOnClickListener(this);
        hindi4_10_pdf.setOnClickListener(this);
        sanskrit10_pdf.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.maths10:
                Uri urimaths10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Class-10-Mathematics.pdf?alt=media&token=caad3481-06e8-4b57-88a7-05a5b484dc00");
                downloadfile(urimaths10);
                break;

            case R.id.science10:
                Uri uriscience10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Class-10-Science.pdf?alt=media&token=7794d30f-d5ec-4348-a00c-3715b814a032");
                downloadfile(uriscience10);
                break;

            case R.id.economics10:
                Uri urieconomics10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Class-10-Economics.pdf?alt=media&token=cb8735a2-5892-465b-bde8-ca579f41e17d");
                downloadfile(urieconomics10);
                break;

            case R.id.geography10:
                Uri urigeo10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Class-10-Geography.pdf?alt=media&token=04aaf3a7-0dca-4cc8-9b00-beac6b50a0ae");
                downloadfile(urigeo10);
                break;

            case R.id.ps10:
                Uri urips10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Class-10-Political-Science.pdf?alt=media&token=150627bf-0a04-4052-8a76-35fcbf422427");
                downloadfile(urips10);
                break;

            case R.id.history10:
                Uri urihis10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Class-10-History.pdf?alt=media&token=bfbea6c5-7725-4f8d-a5da-d4fc1ad27ac0");
                downloadfile(urihis10);
                break;

            case R.id.english10:
                Uri urieng10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Class-10-English-Part-2.pdf?alt=media&token=604b1fdc-4407-4d3a-a5e2-d42361b67c5f");
                downloadfile(urieng10);
                break;

            case R.id.hindi1_10:
                Uri urihi110 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-10-Hindi-Part-1.pdf?alt=media&token=4673e86c-2c66-4811-b201-299b9462bd9c");
                downloadfile(urihi110);
                break;

            case R.id.hindi2_10:
                Uri urihi210 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-10-Hindi-Part-2.pdf?alt=media&token=acaafb5d-95d9-4d7d-ae26-5236fd35df43");
                downloadfile(urihi210);
                break;

            case R.id.hindi3_10:
                Uri urihi310 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-10-Hindi-Part-3.pdf?alt=media&token=fc6ee73e-95c1-48d6-b38b-d85bed6e6099");
                downloadfile(urihi310);
                break;

            case R.id.hindi4_10:
                Uri urihi410 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2010%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-10-Hindi-Part-4.pdf?alt=media&token=80c875b5-494e-46ca-9e6f-f79aba39c671");
                downloadfile(urihi410);
                break;

            case R.id.sanskrit10:
                //Uri urisans10 = Uri.parse("");
                //downloadfile(urisans10);

                break;

        }
    }

    private void downloadfile(Uri uri) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/Edutree/Ebooks/Class 10");
        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Downloading Cbse ebooks");
        request.setDescription("Please wait while your book is downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir("/Edutree/Ebooks/Class 10", "Ncert ebooks_class 10.pdf");

        Long reference = downloadManager.enqueue(request);

    }

}
