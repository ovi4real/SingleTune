package com.pixel.singletune.app.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.cengalabs.flatui.views.FlatEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.List;

import com.pixel.singletune.app.ParseConstants;
import com.pixel.singletune.app.R;
import com.pixel.singletune.app.adapters.UserListAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class SearchActivity extends ListActivity {

    private ParseQueryAdapter<ParseUser> mainAdapter;

    @InjectView(R.id.search_editText) FlatEditText mSearchText;
    @InjectView(R.id.search_button) ImageButton mSearchButton;

    protected List<ParseUser> mUsers;

    protected final String mUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ButterKnife.inject(this);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueryUser();
            }
        });
    }

    private void QueryUser() {
        FlatEditText gUname;
        gUname = (FlatEditText)findViewById(R.id.search_editText);

        String gUsername = gUname.getText().toString();

        Log.d("singleTune", "Check for "+gUsername);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContains("username", gUsername);
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e != null){
                    Log.d("singleTune", "Nothing found");
                }else{
                    mUsers = users;
                    String[] usernames = new String[mUsers.size()];
                    int i = 0;
                    for(ParseUser user : mUsers){
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    UserListAdapter adapter = new UserListAdapter(SearchActivity.this, mUsers);
                    setListAdapter(adapter);
                }

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
