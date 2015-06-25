package com.android.push_up.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.push_up.count.CountActivity;
import com.android.push_up.guide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeachFragment extends Fragment {


    public TeachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View teachView = inflater.inflate(R.layout.fragment_teach,container,false);

        teachView.findViewById(R.id.btnStartTrain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CountActivity.class));
            }
        });

        return teachView;
    }
}
