package com.samsonjabin.uwall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsonjabin.uwall.helpout.ComputerFragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class HelpoutActivity extends Fragment implements MaterialTabListener {


    private ViewPager viewPager;
    private MaterialTabHost tabHost;
    CharSequence Titles[]={"Computer","Electronics"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.helpout_layout, container, false);


        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }
        return v;
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {

        viewPager.setCurrentItem(materialTab.getPosition());

    }



    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] tabText = getResources().getStringArray(R.array.helpout_titles);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.helpout_titles);
        }

        @Override
        public Fragment getItem(int position) {
            ComputerFragment compfrag = ComputerFragment.getInstances(position);
            return compfrag ;
        }



        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
             //String[] tabText = getResources().getStringArray(R.array.helpout_titles);
            //return "Section " + tabText;
            switch (position) {
                case 0:
                    return "Computer";
                case 1:
                    return "Electrical";
                case 2:
                    return "Electronics";
            }
            return null;
        }

    }

}
