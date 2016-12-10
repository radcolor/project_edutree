package com.littlegiants.android.edutree;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.anwarshahriar.calligrapher.Calligrapher;


/**
 * A simple {@link Fragment} subclass.
 */
public class Syll_11_Fragment extends Fragment {


    public Syll_11_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_syll11_12, container, false);

        Calligrapher calligrapher = new Calligrapher(getActivity());

        calligrapher.setFont(v.findViewById(R.id.maths_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.physics_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.chemistry_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.biology_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.accountancy_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.economics_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.bst_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.englishcore_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.english_elec_ncert_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.cs_syll_11), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.pe_syll_11), "truenolt.otf");

        return v;
    }

}
