package com.compubase.taxi.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compubase.taxi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutOneFragment extends Fragment {


    public RoutOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rout_one, container, false);
    }

}
