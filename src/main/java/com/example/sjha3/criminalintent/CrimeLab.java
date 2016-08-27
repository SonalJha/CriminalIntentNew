package com.example.sjha3.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by sjha3 on 6/11/16.
 * This is a singleton class
 * we can make only one object of this class
 * That object will remain alive this the program lives in memory
 * we make the constructor private
 * we declare another method using which we return the object
 * <p/>
 * Apart from the singleton class it contains an arraylist of type Crime
 * Crime contains 100 dummy data
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private static CrimeLab sCrimeLab;
    private final Context mAppContext;
    public ArrayList<Crime> mCrimes;
    private final CriminalIntentJSONSerializer mSerializer;

    private CrimeLab(final Context appContext) {
        mAppContext = appContext;
        // mCrimes = new ArrayList<>();
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);

        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (final Exception e) {
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "error loading crimes", e);
        }
    }

    public static CrimeLab get(final Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(final UUID id) {
        for (final Crime c : mCrimes) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addCrime(final Crime c) {
        mCrimes.add(c);
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (final Exception e) {
            Log.e(TAG, "Error saving crimes", e);
            return false;
        }
    }

}
