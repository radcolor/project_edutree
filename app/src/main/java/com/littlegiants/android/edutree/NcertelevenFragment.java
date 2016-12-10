package com.littlegiants.android.edutree;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
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


public class NcertelevenFragment extends Fragment  implements View.OnClickListener {

    ListItemView maths_pdf, physics1_pdf, physics2_pdf, chemistry1_pdf, chemistry2_pdf, biology_pdf, accountancy1_pdf,
            accountancy2_pdf, bussinessstudies_pdf, economics_pdf, geography1_pdf, geography2_pdf, ps1_pdf, ps2_pdf,
            socialogy1_pdf, socialogy2_pdf, psychology_pdf, statistics_pdf, english1_pdf, english2_pdf, english3_pdf;

    View v;

    public NcertelevenFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater mInflater = getActivity().getLayoutInflater();

        View convertView = mInflater.inflate(R.layout.fragment_eleven, null);

        typeface(convertView);

        return convertView;
    }

    public void typeface(View convertView){

        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(convertView.findViewById(R.id.maths11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.physics1_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.physics2_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.chemistry1_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.chemistry2_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.biology11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.accountancy1_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.accountancy2_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.bussinessstudies11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.economics11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.geography1_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.geography2_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.ps1_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.ps2_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.socialogy1_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.socialogy2_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.psychology11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.statistics11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.english1_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.english2_11), "truenolt.otf");
        calligrapher.setFont(convertView.findViewById(R.id.english3_11), "truenolt.otf");


        maths_pdf = convertView.findViewById(R.id.maths11);
        physics1_pdf = convertView.findViewById(R.id.physics1_11);
        physics2_pdf = convertView.findViewById(R.id.physics2_11);
        chemistry1_pdf = convertView.findViewById(R.id.chemistry1_11);
        chemistry2_pdf = convertView.findViewById(R.id.chemistry2_11);
        biology_pdf = convertView.findViewById(R.id.biology11);
        accountancy1_pdf = convertView.findViewById(R.id.accountancy1_11);
        accountancy2_pdf = convertView.findViewById(R.id.accountancy2_11);
        bussinessstudies_pdf = convertView.findViewById(R.id.bussinessstudies11);
        economics_pdf = convertView.findViewById(R.id.economics11);
        geography1_pdf = convertView.findViewById(R.id.geography1_11);
        geography2_pdf = convertView.findViewById(R.id.geography2_11);
        ps1_pdf = convertView.findViewById(R.id.ps1_11);
        ps2_pdf = convertView.findViewById(R.id.ps2_11);
        socialogy1_pdf = convertView.findViewById(R.id.socialogy1_11);
        socialogy2_pdf = convertView.findViewById(R.id.socialogy2_11);
        psychology_pdf = convertView.findViewById(R.id.psychology11);
        statistics_pdf = convertView.findViewById(R.id.statistics11);
        english1_pdf = convertView.findViewById(R.id.english1_11);
        english2_pdf = convertView.findViewById(R.id.english2_11);
        english3_pdf = convertView.findViewById(R.id.english3_11);

        maths_pdf.setOnClickListener(this);
        physics1_pdf.setOnClickListener(this);
        physics2_pdf.setOnClickListener(this);
        chemistry1_pdf.setOnClickListener(this);
        chemistry2_pdf.setOnClickListener(this);
        biology_pdf.setOnClickListener(this);
        accountancy1_pdf.setOnClickListener(this);
        accountancy2_pdf.setOnClickListener(this);
        bussinessstudies_pdf.setOnClickListener(this);
        economics_pdf.setOnClickListener(this);
        geography1_pdf.setOnClickListener(this);
        geography2_pdf.setOnClickListener(this);
        ps1_pdf.setOnClickListener(this);
        ps2_pdf.setOnClickListener(this);
        socialogy1_pdf.setOnClickListener(this);
        socialogy2_pdf.setOnClickListener(this);
        psychology_pdf.setOnClickListener(this);
        statistics_pdf.setOnClickListener(this);
        english1_pdf.setOnClickListener(this);
        english2_pdf.setOnClickListener(this);
        english3_pdf.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.maths11:

                String book0 = "Ncert_maths_class_11.pdf";
                if(checkFile("/Edutree/Ebooks/Class 11/"+book0)){
                    Intent intent = new Intent(getActivity(), Storage_PDFActivity.class);
                    intent.putExtra("path", "/Edutree/Ebooks/Class 11/"+book0);
                    startActivity(intent);
                }else{
                    Uri urimaths = Uri.parse(getString(R.string.book_maths_11));
                    downloadfile(urimaths,book0);
                }
                break;

            case R.id.physics1_11:

                String book1 = "Ncert_physics_p1_class_11.pdf";
                if(checkFile("/Edutree/Ebooks/Class 11/"+book1)){
                    Intent intent = new Intent(getActivity(), Storage_PDFActivity.class);
                    intent.putExtra("path", "/Edutree/Ebooks/Class 11/"+book1);
                    startActivity(intent);
                }else{
                    Uri uriphysics1 = Uri.parse(getString(R.string.book_phy_11_p1));
                    downloadfile(uriphysics1,book1);
                }
                break;

            /*case R.id.physics2_11:
                Uri uriphysics2 = Uri.parse(getString(R.string.book_phy_11_p2));
                downloadfile(uriphysics2);
                break;
            case R.id.chemistry1_11:
                Uri urichemistry1 = Uri.parse(getString(R.string.book_chem_11_p1));
                downloadfile(urichemistry1);
                break;
            case R.id.chemistry2_11:
                Uri urichemistry2 = Uri.parse(getString(R.string.book_chem_11_p2));
                downloadfile(urichemistry2);
                break;
            case R.id.biology11:
                Uri uribiology = Uri.parse(getString(R.string.book_bio_11));
                downloadfile(uribiology);
                break;
            case R.id.accountancy1_11:
                Uri uriaccounts1 = Uri.parse(getString(R.string.book_acc_11_p1));
                downloadfile(uriaccounts1);
                break;
            case R.id.accountancy2_11:
                Uri uriaccounts2 = Uri.parse(getString(R.string.book_acc_11_p2));
                downloadfile(uriaccounts2);
                break;
            case R.id.bussinessstudies11:
                Uri uribs = Uri.parse(getString(R.string.book_bst_11));
                downloadfile(uribs);
                break;
            case R.id.economics11:
                Uri urieconomics = Uri.parse(getString(R.string.book_eco_11));
                downloadfile(urieconomics);
                break;
            case R.id.geography1_11:
                Uri urigeo1 = Uri.parse(getString(R.string.book_geo_11_p1));
                downloadfile(urigeo1);
                break;
            case R.id.geography2_11:
                Uri urigeo2 = Uri.parse(getString(R.string.book_geo_11_p2));
                downloadfile(urigeo2);
                break;
            case R.id.ps1_11:
                Uri urips1 = Uri.parse(getString(R.string.book_ps_11_p1));
                downloadfile(urips1);
                break;
            case R.id.ps2_11:
                Uri urips2 = Uri.parse(getString(R.string.book_ps_11_p2));
                downloadfile(urips2);
                break;
            case R.id.socialogy1_11:
                Uri urisoc1 = Uri.parse(getString(R.string.book_soc_11_p1));
                downloadfile(urisoc1);
                break;
            case R.id.socialogy2_11:
                Uri urisoc2 = Uri.parse(getString(R.string.book_soc_11_p2));
                downloadfile(urisoc2);
                break;
            case R.id.psychology11:
                Uri uripsycho = Uri.parse(getString(R.string.book_psyc_11));
                downloadfile(uripsycho);
                break;
            case R.id.statistics11:
                Uri uristate = Uri.parse(getString(R.string.book_stat_11));
                downloadfile(uristate);
                break;
            case R.id.english1_11:
                Uri urienglish1 = Uri.parse(getString(R.string.book_eng_11_p1));
                downloadfile(urienglish1);
                break;
            case R.id.english2_11:
                //Toast.makeText(this, "File not uploaded on Your Google Firebase" , Toast.LENGTH_LONG);
                //Uri urienglish2 = Uri.parse(getString(R.string.book_eng_11_p2));
                //downloadfile(urienglish2);
                break;
            case R.id.english3_11:
                Uri urienglish3 = Uri.parse(getString(R.string.book_eng_11_p3));
                downloadfile(urienglish3);
                break;*/

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

    private void downloadfile(Uri uri,String book) {

        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if (isSDPresent) {

            File direct = new File(Environment.getExternalStorageDirectory() + "/Edutree/Ebooks/Class 11");
            if (!direct.exists()) {
                direct.mkdirs();
            }

        } else {

            File file = getContext().getDir("Edutree/Ebooks/Class - 11", Context.MODE_PRIVATE);
            if(!file.exists()){
                file.mkdirs();

            }

            }

            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            request.setTitle("Downloading Cbse ebooks");
            request.setDescription("Please wait while your book is downloading...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setDestinationInExternalPublicDir("/Edutree/Ebooks/Class 11", book);

            Long reference = downloadManager.enqueue(request);

        }
    }


