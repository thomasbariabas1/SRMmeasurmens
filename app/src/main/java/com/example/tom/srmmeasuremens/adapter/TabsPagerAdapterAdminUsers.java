package com.example.tom.srmmeasuremens.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tom.admin.AddUser;
import com.example.tom.admin.DeleteUser;

public class TabsPagerAdapterAdminUsers extends FragmentPagerAdapter {

    public TabsPagerAdapterAdminUsers(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return new AddUser();
            case 1:

                return new DeleteUser();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}