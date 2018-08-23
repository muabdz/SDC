package com.example.app.jsdc.Utils.FragmentUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.app.jsdc.TesKomentar;
import com.example.app.jsdc.TesPraktek;
import com.example.app.jsdc.TesSikap;

/**
 * Created by Mu'adz on 4/2/2018.
 */

public class TestFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Praktek", "Sikap", "TesKomentar"};

    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return TesPraktek.newInstance(0);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return TesSikap.newInstance(1);
            case 2: // Fragment # 1 - This will show SecondFragment
                return TesKomentar.newInstance(2);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}