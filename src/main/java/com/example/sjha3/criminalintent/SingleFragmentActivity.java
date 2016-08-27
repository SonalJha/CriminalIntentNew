package com.example.sjha3.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sjha3 on 6/11/16.
 * Creating a generic class for all fragments to avoid code duplication
 * class is abstract => methods will not have implementation here
 * public class SingleFragmentActivity extends FragmentActivity
 * if we write the above code the action bar stops showing
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // activity_fragment is a generic activity which has a frame layout
        setContentView(R.layout.activity_fragment);
        FragmentManager fm= getSupportFragmentManager();
        //FragmentContainer is the id of the frameLayout
        Fragment fragment = fm.findFragmentById(R.id.FragmentContainer);

        if(fragment==null){
            // we will implement createFragment in the class where we implement this abstract class
            fragment= createFragment();
            fm.beginTransaction().add(R.id.FragmentContainer,fragment).commit();
        }
    }

}
