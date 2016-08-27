package com.example.sjha3.criminalintent;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * We are setting the title as crimes
 * This will come as the 1st screen
 */

public class CrimeListFragment extends ListFragment {

    private ArrayList<Crime> mCrimes;
    private static final String TAG= "CrimeListFragment";
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        mCrimes= CrimeLab.get(getActivity()).getCrimes();
        setRetainInstance(true);
        mSubtitleVisible=false;
        // Right now all we can see is a spinner
        // We will create an arrayadaptor to help us see more
        //simple_list_item_1 is predefined layout from android sdk
        // Crime.java returns class name and memory address of the object because we have not overrridden tostring() method there
//        ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1,mCrimes);
//        setListAdapter(adapter);
        // Now we need a new adapter which can display more than 1 textview
        // we have to pass an arraylist here because its constructor needs one
        CrimeAdapter adapter= new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v,int position, long id)
    {
        // getListAdapter returns the adapter that is set on listview
        //getItem will return the item
      //  Crime c= (Crime) (getListAdapter().getItem(position));
        Crime c= ((CrimeAdapter) (getListAdapter())).getItem(position);
      //  Log.d(TAG,c.getTitle()+"was clicked");
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    //we created a new class here
    // we are switching to another adapter because we want more than 1 textview to be displayed in the listview
    private class CrimeAdapter extends ArrayAdapter<Crime>
    {
        public CrimeAdapter(ArrayList<Crime> crimes)
        {
            // we are passing 0 here because we are not going to use a pre defined layout
            super(getActivity(),0,crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // if we weren't given the view, inflate one
            if(convertView==null)
            {
                // list_item_crime is the new xml we created to be displayed instead of a single textview
                convertView= getActivity().getLayoutInflater().inflate(R.layout.list_item_crime,parent,false);
            }
            // configure the view for this crime
            Crime c= getItem(position);
            TextView titleTextView= (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView= (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            CheckBox solvedCheckBox= (CheckBox) convertView.findViewById(R.id.crime_list_item_solved_checkbox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible && showSubtitle!=null)
            showSubtitle.setTitle(R.string.hide_subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent i = new Intent(getActivity(),CrimePagerActivity.class);
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID,crime.getId());
                startActivityForResult(i,0);
                return true;
            case R.id.menu_item_show_subtitle:
                if(( (AppCompatActivity)getActivity()).getSupportActionBar().getSubtitle()==null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible=true;
                    item.setTitle(R.string.hide_subtitle);
                }
                else{
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
                    item.setTitle(R.string.show_subtitle);
                    mSubtitleVisible=false;
                }
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v= super.onCreateView(inflater,parent,savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(mSubtitleVisible){
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
            }
        }
        return v;
    }

}
