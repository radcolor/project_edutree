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
public class Syll9_Fragment extends Fragment {


    public Syll9_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_syll9_10, container, false);

        Calligrapher calligrapher = new Calligrapher(getActivity());

        calligrapher.setFont(v.findViewById(R.id.maths_syll_910), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.physics_syll_910), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.chemistry_syll_910), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.biology_syll_910), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.englishcore_syll_910), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.english_elec_ncert_syll_910), "truenolt.otf");
        calligrapher.setFont(v.findViewById(R.id.cs_syll_910), "truenolt.otf");

        return v;
    }

}
