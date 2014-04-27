package com.pixel.singletune.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.cengalabs.flatui.views.FlatEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SearchActivity extends Activity {

    protected String mUser = "user";

    @InjectView(R.id.search_editText) FlatEditText mSearchText;
    @InjectView(R.id.search_button) ImageButton mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        mSearchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ParseQuery query = ParseUser.getQuery();
                query.whereStartsWith("username", String.valueOf(mSearchText));
                query.findInBackground(new FindCallback<ParseObject>(){
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        if (parseObjects == null) {
                            Log.d("score", "The getFirst request failed.");
                        } else {
                            Log.d("score", "Retrieved the object.");
                        }
                    }
                });
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
