package com.example.oshodimobile.Tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.oshodimobile.AcceptedbyMeMatchFragment;
import com.example.oshodimobile.LoadedbyMeMatchFragment;

public class TabAdapter extends FragmentStateAdapter {
    private  int NUM_TABS = 2;

    public TabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        if(position == 0){return new LoadedbyMeMatchFragment();}
        if(position == 1){return new AcceptedbyMeMatchFragment();}

        return null;
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
