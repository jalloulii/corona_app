package com.example.coronastat.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.coronastat.fragments.CoronaLiveF;
import com.example.coronastat.fragments.SaveYourSelf;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                CoronaLiveF coronaLiveF = new CoronaLiveF();
                return coronaLiveF;
            case 1 :
                SaveYourSelf saveYourSelf = new SaveYourSelf();
                return saveYourSelf;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
