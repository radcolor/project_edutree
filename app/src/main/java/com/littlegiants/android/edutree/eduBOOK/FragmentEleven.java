package com.littlegiants.android.edutree.eduBOOK;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.littlegiants.android.edutree.R;
import com.littlegiants.android.edutree.Storage_PDFActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class FragmentEleven extends Fragment implements View.OnClickListener {

    public void FragmentEleven(){

    }

    CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8,cv9,cv10,cv11;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.booknotes_eleven, container, false);

        cardView(v);

        img1 = v.findViewById(R.id.img_bn11_1);
        img2 = v.findViewById(R.id.img_bn11_2);
        img3 = v.findViewById(R.id.img_bn11_3);
        img4 = v.findViewById(R.id.img_bn11_4);
        img5 = v.findViewById(R.id.img_bn11_5);
        img8 = v.findViewById(R.id.img_bn11_8);
        img9 = v.findViewById(R.id.img_bn11_9);
        img10 = v.findViewById(R.id.img_bn11_10);
        img11 = v.findViewById(R.id.img_bn11_11);

        img1.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_maths_11.jpg"));
        img2.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_physics-part-2-11.jpg"));
        img3.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_chemistry_2_11.jpg"));
        img4.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_snapshot_11.jpg"));
        img5.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_ip_11.jpg"));
        img8.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_physics_1_11.jpg"));
        img9.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_chemistry_1_11.jpg"));
        img10.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_hornbill_11.jpg"));
        img11.setImageBitmap(getBitmapFromAssets("ncert-cover-11/cover_physical_education_11.jpg"));


        return v;

    }

    public void cardView(View v){

        cv1 = v.findViewById(R.id.not11_cv1);
        cv2 = v.findViewById(R.id.not11_cv2);
        cv3 = v.findViewById(R.id.not11_cv3);
        cv4 = v.findViewById(R.id.not11_cv4);
        cv5 = v.findViewById(R.id.not11_cv5);
        cv6 = v.findViewById(R.id.not11_cv6);
        cv7 = v.findViewById(R.id.not11_cv7);
        cv8 = v.findViewById(R.id.not11_cv8);
        cv9 = v.findViewById(R.id.not11_cv9);
        cv10 = v.findViewById(R.id.not11_cv10);
        cv11 = v.findViewById(R.id.not11_cv11);

        cv1.setOnClickListener(this);
        cv2.setOnClickListener(this);
        cv3.setOnClickListener(this);
        cv4.setOnClickListener(this);
        cv5.setOnClickListener(this);
        cv6.setOnClickListener(this);
        cv7.setOnClickListener(this);
        cv8.setOnClickListener(this);
        cv9.setOnClickListener(this);
        cv10.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();

        switch (btnId) {

            case R.id.not11_cv1:
                String path = "/Edutree/Class - 11/EBooks/";
                String pdfpath = "/Edutree/Class - 11/EBooks/Mathematics-NCERT-11.pdf";
                String book = "Mathematics-NCERT-11.pdf";

                if(checkFile(pdfpath)){
                    pdfView(pdfpath);
                }else{
                    Uri uriip = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%2011%2FNCERT%20Ebooks%20-%2011%2FNCERT-Class-11-Mathematics.pdf?alt=media&token=695dbc6c-4f93-4157-950e-2e7641f7dd59");
                    downloadfile(uriip,book,path);
                }
                break;

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

        File direct = new File(Environment.getExternalStorageDirectory() + path);
        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Downloading NCERT EBooks");
        request.setDescription("Please wait while your book is downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);

        request.setDestinationInExternalPublicDir(path, book);

        Long reference = downloadManager.enqueue(request);

    }

    private void pdfView(String pdf_path){

        Intent intent = new Intent(getActivity(), Storage_PDFActivity.class);
        intent.putExtra("path", pdf_path);
        startActivity(intent);

    }

    private Bitmap getBitmapFromAssets(String fileName){

        AssetManager am = getActivity().getAssets();
        InputStream is = null;
        try{

            is = am.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }


}
