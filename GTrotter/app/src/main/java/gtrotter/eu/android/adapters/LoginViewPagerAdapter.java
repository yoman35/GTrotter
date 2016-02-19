package gtrotter.eu.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import gtrotter.eu.android.fragments.TabRegisterFragment;
import gtrotter.eu.android.fragments.TabSignInFragment;

public class LoginViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public LoginViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int nbTab) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = nbTab;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return (position == 0 ? new TabSignInFragment() : new TabRegisterFragment());
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}