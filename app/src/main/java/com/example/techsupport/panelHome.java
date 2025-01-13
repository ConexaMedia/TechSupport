package com.example.techsupport;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

public class panelHome extends Fragment {

    public static  final  String NamaTeknisi = "nama_teknisi";
    private String username;

    public panelHome(){

    }
    public static panelHome newInstance(String username) {
        panelHome fragment = new panelHome();
        Bundle args = new Bundle();
        args.putString(NamaTeknisi, username); // Parameter opsional
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(NamaTeknisi);
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout fragment\
        View view = inflater.inflate(R.layout.fragment_panel_home, container, false);

        TextView textView = view.findViewById(R.id.nama_teknisi);
        textView.setText(username);

        return view;
    }
}

