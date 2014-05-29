package com.pixel.singletune.app.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.parse.ParseObject;
import com.pixel.singletune.app.R;

import java.util.List;

/**
 * Created by mrsmith on 5/29/14.
 */
public class TuneAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mTunes;

    public TuneAdapter(Context context, List<ParseObject> tunes){
        super(context, R.layout.tune_item, tunes);
        mContext = context;
        mTunes = tunes;
    }
}
