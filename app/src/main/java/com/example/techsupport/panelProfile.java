package com.example.techsupport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class panelProfile extends Fragment {

    public static final String NamaTeknisi = "nama_teknisi";
    private String username;

    public panelProfile() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static panelProfile newInstance(String username) {
        panelProfile fragment = new panelProfile();
        Bundle args = new Bundle();
        args.putString(NamaTeknisi, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(NamaTeknisi);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_panel_profile, container, false);

        TextView textView = view.findViewById(R.id.nama_teknisi);
        textView.setText(username);
        return view;
    }
}