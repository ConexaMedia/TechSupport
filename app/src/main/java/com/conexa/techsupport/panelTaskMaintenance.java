package com.conexa.techsupport;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class panelTaskMaintenance extends Fragment{

    public panelTaskMaintenance(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_taskmaintenance, container,false);
        return view;
    }
}
