package com.conexa.techsupport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class panelTaskAktivasi extends Fragment {

    public panelTaskAktivasi(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_taskaktivasi, container,false);
        return view;
    }
}
