package com.pixel.singletune.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.pixel.singletune.app.ParseConstants;
import com.pixel.singletune.app.R;

import java.util.List;

import static com.pixel.singletune.app.R.string.user_follow_text;

/**
 * Created by mrsmith on 4/28/14.
 */
public class UserListAdapter extends ArrayAdapter<ParseUser> {

    private static final String TAG = "Test";
    protected Context mContext;
    protected List<ParseUser> mParseUsers;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;
    protected Boolean followed;
    protected Boolean followStatus;

    protected List<ParseUser> mUsers;

    public UserListAdapter(Context context, List<ParseUser> users){
        super(context, R.layout.user_item, users);
        mContext = context;
        mParseUsers = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater.from(mContext));
        final View rowView = inflater.inflate(R.layout.user_item, parent, false);
        ImageView avatar = (ImageView) rowView.findViewById(R.id.avatar);
        TextView usernameLabel = (TextView) rowView.findViewById(R.id.usernameLabel);

        final ParseUser user = mParseUsers.get(position);

        avatar.setImageResource(R.drawable.ic_action_picture);
        usernameLabel.setText(user.getUsername());


        return rowView                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
    }



    //
    private static class ViewHolder{
    }

    public void refill(List<ParseUser> users){
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }
}
