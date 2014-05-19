package com.pixel.singletune.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.pixel.singletune.app.R;
import com.pixel.singletune.app.utils.MD5Util;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        ImageView avatar = (ImageView) rowView.findViewById(R.id.avatarImageView);
        ImageView selectedAvatar = (ImageView) rowView.findViewById(R.id.selectedAvatarImageView);
        TextView usernameLabel = (TextView) rowView.findViewById(R.id.usernameLabel);

        final ParseUser user = mParseUsers.get(position);

        String email = user.getEmail().toLowerCase();

        if(email.equals("")){
            avatar.setImageResource(R.drawable.default_avatar);
        }
        else{
            String hash = MD5Util.md5Hex(email);
            String gravatarUrl = "http://www.gravatar.com/avatar/"+ hash + "?s=272&d=404";
            Picasso.with(mContext).load(gravatarUrl).placeholder(R.drawable.default_avatar).into(avatar);
        }
        usernameLabel.setText(user.getUsername());

        GridView gridView = (GridView)parent;
        if(gridView.isItemChecked(position)){
            selectedAvatar.setVisibility(View.VISIBLE);
        }
        else {

            selectedAvatar.setVisibility(View.INVISIBLE);
        }


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
