package com.android.push_up.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.push_up.count.DietPlanActivity;
import com.android.push_up.count.MyPlanActivity;
import com.android.push_up.count.PlanCountActivity;
import com.android.push_up.guide.R;
import com.android.push_up.share.ShareActivity;


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

        meView.findViewById(R.id.btnExercisePlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPlanActivity.class));
            }
        });

        meView.findViewById(R.id.btnDietPlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),DietPlanActivity.class));
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