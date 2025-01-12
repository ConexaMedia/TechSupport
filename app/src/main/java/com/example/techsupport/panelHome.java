package com.example.techsupport;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class panelHome extends Fragment {
    public panelHome(){

    }
    public static panelHome newInstance(String Name) {
        panelHome fragment = new panelHome();
        Bundle args = new Bundle();
        args.putString("NAME", Name); // Parameter opsional
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout fragment
        return inflater.inflate(R.layout.fragment_panel_home, container, false);
    }
}

