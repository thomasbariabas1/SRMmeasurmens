package com.example.tom.engineer;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import com.example.srmmeasurements.R;
import com.example.tom.srmmeasuremens.adapter.TabsPagerAdapterEngineerSearchSafetyInfo;




public class SafetyInfo extends FragmentActivity {
    private ViewPager viewPager;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Safety Info","Graph1","Safety Map","Graph2"};
    public static FragmentManager fragmentManager;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safetyinfo);
        fragmentManager = getSupportFragmentManager();
    }

    public void onStart(){
        super.onStart();
        new LoadUI().execute(null, null, null);
    }



    public class LoadUI extends AsyncTask<Void, Void, TabsPagerAdapterEngineerSearchSafetyInfo> implements ActionBar.TabListener {
        public LoadUI() {
            viewPager = (ViewPager) findViewById(R.id.pager5);
            viewPager.setOffscreenPageLimit(3);
            actionBar = getActionBar();
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
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
        protected void onPreExecute() {

            final ProgressDialog ialog = ProgressDialog.show(SafetyInfo.this, "Working..", "Loading Graph,Map And More Graphs....", true, false);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ialog.dismiss();
                }
            }, 5000);
        }
        @Override
        protected TabsPagerAdapterEngineerSearchSafetyInfo doInBackground(Void... params) {

            for (String tab_name : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
            }

            TabsPagerAdapterEngineerSearchSafetyInfo tabs = new TabsPagerAdapterEngineerSearchSafetyInfo(fragmentManager,getIntent().getExtras());
            return  tabs;
        }
        @Override
        protected void onPostExecute(TabsPagerAdapterEngineerSearchSafetyInfo result) {

            viewPager.setAdapter(result);

            super.onPostExecute(result);
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
        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        }
    }
}
