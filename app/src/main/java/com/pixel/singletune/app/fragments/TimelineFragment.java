package com.pixel.singletune.app.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pixel.singletune.app.ParseConstants;
import com.pixel.singletune.app.R;
import com.pixel.singletune.app.adapters.TuneAdapter;

import java.util.List;


public class TimelineFragment extends ListFragment {

    protected List<ParseObject> mTunes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        getActivity().setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_TUNES);
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> tunes, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);

                if (e == null){
                    mTunes = tunes;
                    String[] usernames = new String[mTunes.size()];

                    int i = 0;
                    for (ParseObject tune : mTunes){
                        usernames[i] = tune.getString(ParseConstants.KEY_USER_ID);
                    }
                    TuneAdapter adapter = new TuneAdapter(getListView().getContext(), mTunes);
                }
            }
        });
    }

}
