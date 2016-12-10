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


public class NcertnineFragment extends Fragment implements View.OnClickListener {

    ListItemView maths9_pdf, science9_pdf, economics9_pdf, geography9_pdf, ps9_pdf, history9_pdf, english9_pdf,
                 hindi1_9_pdf,hindi2_9_pdf,hindi3_9_pdf,hindi4_9_pdf, sanskrit9_pdf;

    public NcertnineFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nine, container, false);

        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(v.findViewById(R.id.maths9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.science9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.economics9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.geography9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.ps9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.history9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.english9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi1_9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi2_9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi3_9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.hindi4_9), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.sanskrit9), "truenolt.otf");


        maths9_pdf = (ListItemView) v.findViewById(R.id.maths9);
        science9_pdf = (ListItemView) v.findViewById(R.id.science9);
        economics9_pdf = (ListItemView) v.findViewById(R.id.economics9);
        geography9_pdf = (ListItemView) v.findViewById(R.id.geography9);
        ps9_pdf = (ListItemView) v.findViewById(R.id.ps9);
        history9_pdf = (ListItemView) v.findViewById(R.id.history9);
        english9_pdf = (ListItemView) v.findViewById(R.id.english9);
        hindi1_9_pdf = (ListItemView) v.findViewById(R.id.hindi1_9);
        hindi2_9_pdf = (ListItemView) v.findViewById(R.id.hindi2_9);
        hindi3_9_pdf = (ListItemView) v.findViewById(R.id.hindi3_9);
        hindi4_9_pdf = (ListItemView) v.findViewById(R.id.hindi4_9);
        sanskrit9_pdf = (ListItemView) v.findViewById(R.id.sanskrit9);

        maths9_pdf.setOnClickListener(this);
        science9_pdf.setOnClickListener(this);
        economics9_pdf.setOnClickListener(this);
        geography9_pdf.setOnClickListener(this);
        ps9_pdf.setOnClickListener(this);
        history9_pdf.setOnClickListener(this);
        english9_pdf.setOnClickListener(this);
        hindi1_9_pdf.setOnClickListener(this);
        hindi2_9_pdf.setOnClickListener(this);
        hindi3_9_pdf.setOnClickListener(this);
        hindi4_9_pdf.setOnClickListener(this);
        sanskrit9_pdf.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.maths9:
                Uri urimaths9 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Class-9-Mathematics.pdf?alt=media&token=f039bfd2-d578-4fc4-a727-e8081e16a996");
                downloadfile(urimaths9);
                break;

            case R.id.science9:
                Uri uriscience10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Class-9-Science.pdf?alt=media&token=ebbfe86b-7d1c-45d7-8cb1-86169bbf2c85");
                downloadfile(uriscience10);
                break;

            case R.id.economics9:
                Uri urieconomics10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Class-9-Economics.pdf?alt=media&token=3d950fe4-60b7-49c6-b4e0-9d20da5ff1f5");
                downloadfile(urieconomics10);
                break;

            case R.id.geography9:
                Uri urigeo10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Class-9-Geography.pdf?alt=media&token=6e370c20-0c1e-47c3-aef4-1a9ca1673303");
                downloadfile(urigeo10);
                break;

            case R.id.ps9:
                Uri urips10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Class-9-Political-Science.pdf?alt=media&token=1b894a57-3897-42ed-8f6f-463d2ebb9353");
                downloadfile(urips10);
                break;

            case R.id.history9:
                Uri urihis10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Class-9-History.pdf?alt=media&token=db6bfebe-b9da-41b1-a442-652b6858d79a");
                downloadfile(urihis10);
                break;

            case R.id.english9:
                Uri urieng10 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Class-9-English-Part-1.pdf?alt=media&token=e5e3ea47-a211-469a-b911-3d01f599f3df");
                downloadfile(urieng10);
                break;

            case R.id.hindi1_9:
                Uri urihi110 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-9-Hindi-Part-1.pdf?alt=media&token=5e622a1c-ec3e-4c91-870f-ec7aadc6c72e");
                downloadfile(urihi110);
                break;

            case R.id.hindi2_9:
                Uri urihi210 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-9-Hindi-Part-2.pdf?alt=media&token=7f349d0e-dfbe-4b95-9e56-75924c78e150");
                downloadfile(urihi210);
                break;

            case R.id.hindi3_9:
                Uri urihi310 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-9-Hindi-Part-3.pdf?alt=media&token=e4af9de4-383d-4fbc-8e5f-33370868cd28");
                downloadfile(urihi310);
                break;

            case R.id.hindi4_9:
                Uri urihi410 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/little-giants-edutree.appspot.com/o/Central%20Board%20of%20Secondary%20Education%20-%209%2FNCERT%20Ebooks%2FNCERT-Hindi-Class-9-Hindi-Part-4.pdf?alt=media&token=999491c1-e3e6-42c2-9a34-7e4e0a72cad6");
                downloadfile(urihi410);

            case R.id.sanskrit9:
                //Uri urisans10 = Uri.parse("");
                //downloadfile(urisans10);

                break;
        }
    }

    private void downloadfile(Uri uri) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/Edutree/Ebooks/Class 9");
        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Downloading Cbse ebooks");
        request.setDescription("Please wait while your book is downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir("/Edutree/Ebooks/Class 9", "Ncert ebooks_class 9.pdf");

        Long reference = downloadManager.enqueue(request);

    }
}
