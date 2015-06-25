package com.android.push_up.home;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.push_up.alarm.AlarmActivity;
import com.android.push_up.guide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    public SetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View setView = inflater.inflate(R.layout.fragment_set,container,false);

        setView.findViewById(R.id.alarm_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AlarmActivity.class));
            }
        });
        return setView;
    }

}
