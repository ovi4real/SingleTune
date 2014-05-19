package com.pixel.singletune.app.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

import com.parse.SaveCallback;
import com.pixel.singletune.app.ParseConstants;
import com.pixel.singletune.app.R;
import com.pixel.singletune.app.adapters.UserListAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class SearchActivity extends Activity {

    private ParseQueryAdapter<ParseUser> mainAdapter;

    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;
    protected GridView mGridView;

    @InjectView(R.id.search_editText) EditText mSearchText;
    @InjectView(R.id.search_button) ImageButton mSearchButton;

    protected List<ParseUser> mUsers;

    protected final String mUsername = null;

    public static final String TAG = SearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mGridView = (GridView)findViewById(R.id.friendsGrid);

        mGridView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mGridView.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ButterKnife.inject(this);

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueryUser();
            }
        });
    }

    private void QueryUser() {
        EditText gUname;
        gUname = (EditText)findViewById(R.id.search_editText);
        setProgressBarIndeterminateVisibility(true);

        String gUsername = gUname.getText().toString();

        Log.d("singleTune", "Check for "+gUsername);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContains("username", gUsername);
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
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
                    mGridView.setAdapter(adapter);

                    friendFollowCheck();
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

    private void friendFollowCheck(){
        mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {
                if(e == null){
                    for (int i = 0; i < mUsers.size(); i++){
                        ParseUser user = mUsers.get(i);
                        for (ParseUser friend : friends){
                            if (friend.getObjectId().equals(user.getObjectId())){
                                //TODO: Change button style and text
                                mGridView.setItemChecked(i, true);
                            }
                        }
                    }
                }
                else {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    protected AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        Boolean followed;
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            TODO: Remember to use overlay image
        if (mGridView.isItemChecked(i)) {
//            // add the friend
            mFriendsRelation.add(mUsers.get(i));
            followed = true;
            sendPushNotification(mUsers.get(i).getObjectId(), followed);
        }
        else {
            // remove the friend
            mFriendsRelation.remove(mUsers.get(i));
            followed = false;
            sendPushNotification(mUsers.get(i).getObjectId(), followed);
        }
        mCurrentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
        }
    };

    private void sendPushNotification(String userId, Boolean b){
        ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
        query.whereEqualTo(ParseConstants.KEY_USER_ID, userId);

        ParsePush push = new ParsePush();
        push.setQuery(query);
        if (b){
            push.setMessage("Yay! "+ ParseUser.getCurrentUser().getUsername()+ " is now following you.");
        }
        else{
            push.setMessage("Aww! "+ ParseUser.getCurrentUser().getUsername()+ " has unfollowed you.");
        }
        push.sendInBackground();
    }

}
