package com.omaar.bottomnavigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TestFragment1 extends Fragment {

    public TestFragment1(String text) {
        // Required empty public constructor
        this.text = text ;
    }

    private TextView textView;
    private String text ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test_fragment1, container, false);
        textView = v.findViewById(R.id.textfrag);
        textView.setText(text);

        return v;
    }

}
