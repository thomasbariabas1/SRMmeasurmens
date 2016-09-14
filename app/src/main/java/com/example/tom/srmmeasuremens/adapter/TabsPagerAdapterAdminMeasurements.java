package com.example.tom.srmmeasuremens.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.tom.admin.DeleteSafetyEvaluation;
import com.example.tom.admin.DeleteSpectrumAnalysis;


public class TabsPagerAdapterAdminMeasurements extends FragmentPagerAdapter {

    public TabsPagerAdapterAdminMeasurements(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return new DeleteSafetyEvaluation();
            case 1:

                return new DeleteSpectrumAnalysis();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}