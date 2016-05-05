package com.snotsoft.hungrr.menu;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.snotsoft.hungrr.domain.Menu;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Menu menu;

        public SectionsPagerAdapter(FragmentManager fm, Menu menu) {
            super(fm);
            this.menu = menu;
        }

        @Override
        public Fragment getItem(int position) {
                  return PlaceholderFragment.newInstance(position + 1, menu.getSections().get(0));
        }

        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }