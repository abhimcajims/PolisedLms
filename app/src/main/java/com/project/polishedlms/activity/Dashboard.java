package com.project.polishedlms.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.project.polishedlms.fragment.ClassFragment;
import com.project.polishedlms.R;

public class Dashboard extends Fragment implements View.OnClickListener {

    View view;
    CardView card_online_class;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(view);
        setListener();
        return view;
    }

    private void init(View v) {
        try {
            card_online_class = v.findViewById(R.id.card_online_class);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void setListener() {
        try {
            card_online_class.setOnClickListener(this);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {

                case R.id.card_online_class:
                    new DashboardActivity().replace(new ClassFragment());
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new ClassFragment()).commit();
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}