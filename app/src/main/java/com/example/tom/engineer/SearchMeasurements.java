package com.example.tom.engineer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.srmmeasurements.R;
import com.example.tom.srmmeasuremens.adapter.TabsPagerAdapterEngineerRegion;
import com.example.tom.srmmeasuremens.adapter.TabsPagerAdapterEngineerSearch;


public class SearchMeasurements extends FragmentActivity implements ActionBar.TabListener{

    private ViewPager viewPager;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {  "Spectrum Analysis","Safety Evaluation"};






    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchmeasurements);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager3);
        actionBar = getActionBar();
        TabsPagerAdapterEngineerSearch mAdapter;
        mAdapter = new TabsPagerAdapterEngineerSearch(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });



    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}
