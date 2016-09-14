package com.example.tom.srmmeasuremens.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tom.engineer.SafetyInfoGraph1;
import com.example.tom.engineer.SafetyInfoGraph2;
import com.example.tom.engineer.SafetyInfoMap;
import com.example.tom.engineer.SafetyInfoTable;



public class TabsPagerAdapterEngineerSearchSafetyInfo extends FragmentPagerAdapter {
    Bundle args;

    public TabsPagerAdapterEngineerSearchSafetyInfo(FragmentManager fm,Bundle args) {
        super(fm);
        this.args=args;

    }



    @Override
    public Fragment getItem(int index) {
        Fragment pagefragment;
        switch (index) {
            case 0:
                pagefragment = new SafetyInfoTable();
                pagefragment.setArguments(args);
                return pagefragment;
            case 1:
                pagefragment = new SafetyInfoGraph1();
                pagefragment.setArguments(args);
                return pagefragment;
            case 2:

                pagefragment = new SafetyInfoMap();
                pagefragment.setArguments(args);
                return pagefragment;
            case 3:
                pagefragment = new SafetyInfoGraph2();
                pagefragment.setArguments(args);
                return pagefragment;
            default:
                pagefragment = null;
                return pagefragment;
        }

    }
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
}