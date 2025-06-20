package com.conexa.techsupport.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.conexa.techsupport.AktivasiFragment;
import com.conexa.techsupport.MaintenanceFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new AktivasiFragment();
        }
        return new MaintenanceFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
