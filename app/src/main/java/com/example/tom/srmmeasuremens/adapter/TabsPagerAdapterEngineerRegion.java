package com.example.tom.srmmeasuremens.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tom.engineer.AddRegionMeasurements;
import com.example.tom.engineer.DeleteRegionMeasurements;
import com.example.tom.engineer.SearchSafety;
import com.example.tom.engineer.SearchSpectrum;

public class TabsPagerAdapterEngineerRegion extends FragmentPagerAdapter {

    public TabsPagerAdapterEngineerRegion(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return new AddRegionMeasurements();
            case 1:

                return new DeleteRegionMeasurements();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}