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
import android.util.Log;
import com.example.srmmeasurements.R;
import com.example.tom.srmmeasuremens.adapter.TabsPagerAdapterEngineerSearchSpectrumInfo;



public class SpectrumInfo extends FragmentActivity {
    private ViewPager viewPager;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Spectrum Info","Graph1","Spectrum Map","Graph2"};
    public static FragmentManager fragmentManager;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spectruminfo);
        fragmentManager = getSupportFragmentManager();
    }

    public void onStart(){
        super.onStart();
        new LoadUI().execute(null, null, null);
    }



    public class LoadUI extends AsyncTask<Void, Void, TabsPagerAdapterEngineerSearchSpectrumInfo> implements ActionBar.TabListener {
        public LoadUI() {
            viewPager = (ViewPager) findViewById(R.id.pager4);
            viewPager.setOffscreenPageLimit(3);
            actionBar = getActionBar();
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    // on changing the page
                    // make respected tab selected
                    Log.e("onpageselected", Integer.toString(position));
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
            Log.e("Inside PreExecute","before ialog");
            final ProgressDialog ialog = ProgressDialog.show(SpectrumInfo.this, "Working..", "Loading Graph,Map And More Graphs....", true, false);
            Log.e("Inside PreExecute","after ialog");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ialog.dismiss();
                }
            }, 5000);
        }
        @Override
        protected TabsPagerAdapterEngineerSearchSpectrumInfo doInBackground(Void... params) {
            Log.e("Inside doInBackground","before for");
            for (String tab_name : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
            }
            Log.e("Inside doInBackground","after for");
            TabsPagerAdapterEngineerSearchSpectrumInfo tabs = new TabsPagerAdapterEngineerSearchSpectrumInfo(fragmentManager,getIntent().getExtras());
            return  tabs;
        }
        @Override
        protected void onPostExecute(TabsPagerAdapterEngineerSearchSpectrumInfo result) {
            Log.e("Inside onPostExcecute", "before setadapter");
            viewPager.setAdapter(result);
            Log.e("Inside onPostExcecute", "aftersetadapter");
            Log.e("Inside onPostExcecute", "before if");
            Log.e("Inside onPostExcecute", "before if");
            super.onPostExecute(result);
        }
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            // on tab selected
            // show respected fragment view
            Log.e("Ontabselected",Integer.toString(tab.getPosition()));
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
