package com.example.sjha3.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by sjha3 on 7/2/16.
 */
public class CriminalIntentJSONSerializer {
    private final Context mContext;
    private final String mFilename;

    public CriminalIntentJSONSerializer(final Context c, final String f) {
        mContext = c;
        mFilename = f;
    }

    public void saveCrimes(final ArrayList<Crime> crimes) throws JSONException, IOException {
        final JSONArray array = new JSONArray();
        // build an array in JSON
        for (final Crime c : crimes)
            array.put(c.toJSON());

        // write the JSON to the disk
        Writer writer = null;
        try {
            final OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }

    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        final ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try {
            final InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            final StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            // parse the json
            final JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of crimes from JSON objects
            for (int i = 0; i < array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (final FileNotFoundException e) {
            // ignore this one, it will happen when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return crimes;
    }

}
