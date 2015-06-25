package com.android.push_up.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.push_up.count.PlanCountActivity;
import com.android.push_up.guide.R;
import com.android.push_up.share.ShareActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;


public class MeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View meView = inflater.inflate(R.layout.fragment_me,container,false);

        meView.findViewById(R.id.btnMakePlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlanCountActivity.class));
            }
        });

        meView.findViewById(R.id.btnTakePhotoAndShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShareActivity.class));
            }
        });

        return meView;
    }

}