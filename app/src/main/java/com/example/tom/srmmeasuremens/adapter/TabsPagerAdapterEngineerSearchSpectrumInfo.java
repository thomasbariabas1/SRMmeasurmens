package com.example.tom.srmmeasuremens.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.tom.engineer.SpectrumInfoGraph1;
import com.example.tom.engineer.SpectrumInfoGraph2;
import com.example.tom.engineer.SpectrumInfoMap;
import com.example.tom.engineer.SpectrumInfoTable;


public class TabsPagerAdapterEngineerSearchSpectrumInfo extends FragmentPagerAdapter {
     Bundle args;

    public TabsPagerAdapterEngineerSearchSpectrumInfo(FragmentManager fm,Bundle args) {
        super(fm);
        this.args=args;

    }



    @Override
    public Fragment getItem(int index) {
       Fragment pagefragment;
        switch (index) {
            case 0:
                pagefragment = new SpectrumInfoTable();
                pagefragment.setArguments(args);
                return pagefragment;
            case 1:
                pagefragment = new SpectrumInfoGraph1();
                pagefragment.setArguments(args);
                return pagefragment;
            case 2:

                pagefragment = new SpectrumInfoMap();
                pagefragment.setArguments(args);
                return pagefragment;
            case 3:
                pagefragment = new SpectrumInfoGraph2();
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