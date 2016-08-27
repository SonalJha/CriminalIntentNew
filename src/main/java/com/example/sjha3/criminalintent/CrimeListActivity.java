package com.example.sjha3.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by sjha3 on 6/11/16.
 * This class will implement the abstract method createFragment()
 * We are not doing fragment manager thing and start fragment in here because
 * we did all that in the abstract class
 * This is the main screen for now
 * This is an activity, here we create a fragment, crimelistfragment
 */
public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
