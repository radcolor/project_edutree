package com.littlegiants.android.edutree;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import me.anwarshahriar.calligrapher.Calligrapher;

public class SyllabusActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Calligrapher calligrapher;

    CoordinatorLayout activity_syllabus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        activity_syllabus = (CoordinatorLayout) findViewById(R.id.activity_syllabus);

        calligrapher = new Calligrapher(this);

        ActivityCompat.requestPermissions(SyllabusActivity.this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        toolbar = (Toolbar) findViewById(R.id.syll_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void setupViewPager(ViewPager viewPager){

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Syll9_Fragment(), "Class 9");
        adapter.addFragment(new Syll9_Fragment(), "Class 10");
        adapter.addFragment(new Syll_11_Fragment(), "Class 11");
        adapter.addFragment(new Syll_11_Fragment(), "Class 12");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTittleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTittleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String tittle){
            mFragmentList.add(fragment);
            mFragmentTittleList.add(tittle);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewPager = (ViewPager) findViewById(R.id.syll_vp);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.syll_tablay);
        tabLayout.setupWithViewPager(viewPager);

        calligrapher.setFont(findViewById(R.id.syll_tool), "truenolt.otf");
        calligrapher.setFont(findViewById(R.id.syll_vp), "truenolt.otf");
        calligrapher.setFont(findViewById(R.id.syll_tablay), "truenolt.otf");
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
                    Toast.makeText(SyllabusActivity.this, "Storage Permission needed to download Syllabus", Toast.LENGTH_SHORT).show();
                }

                return;
            }

        }


    }
}
