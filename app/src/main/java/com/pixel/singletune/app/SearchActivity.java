package com.pixel.singletune.app;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cengalabs.flatui.views.FlatEditText;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.List;

import adapters.StringArrayAdapter;
import adapters.UserListAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class SearchActivity extends ListActivity {

    private ParseQueryAdapter<ParseUser> mainAdapter;

    @InjectView(R.id.search_editText) FlatEditText mSearchText;
    @InjectView(R.id.search_button) ImageButton mSearchButton;

    protected List<ParseUser> mUsers;

    protected final String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ButterKnife.inject(this);

        final String  username = mSearchText.getText().toString().trim();
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueryUser(username);
            }
        });
    }

    private void QueryUser(final String username) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e != null){
                    Log.d("singleTune", "Nothing found");
                }else{
                    Log.d("singleTune", "I got " + users.size() + " records back.");
                    mUsers = users;
                    String[] usernames = new String[mUsers.size()];
                    int i = 0;
                    for(ParseUser user : mUsers){
                        usernames[i] = user.getUsername().toString();
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
