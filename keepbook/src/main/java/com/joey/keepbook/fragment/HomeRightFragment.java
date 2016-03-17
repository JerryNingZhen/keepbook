package com.joey.keepbook.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.joey.keepbook.R;
import com.joey.keepbook.activity.AddNewBillActivity;
import com.joey.keepbook.utils.InputMethodUtils;

/**
 * Created by Joey on 2016/3/13.
 */
public class HomeRightFragment extends Fragment {
    private Button btAddFg;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_home_right,container,false);
        activity = getActivity();
        btAddFg = (Button) view.findViewById(R.id.bt_home_right_add);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        btAddFg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity,AddNewBillActivity.class));
                activity.finish();
            }
        });
    }
}
