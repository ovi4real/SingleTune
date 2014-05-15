package com.pixel.singletune.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.cengalabs.flatui.views.FlatButton;
import com.parse.ParseUser;
import com.pixel.singletune.app.ParseConstants;
import com.pixel.singletune.app.R;

import java.util.List;

/**
 * Created by mrsmith on 4/28/14.
 */
public class UserListAdapter extends ArrayAdapter<ParseUser> {

    protected Context mContext;
    protected List<ParseUser> mParseUsers;

    public UserListAdapter(Context context, List<ParseUser> users){
        super(context, R.layout.user_item, users);
        mContext = context;
        mParseUsers = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, null);
            holder = new ViewHolder();
            holder.user_imageView    = (ImageView)convertView.findViewById(R.id.user_imageView);
            holder.usernameLabel = (TextView)convertView.findViewById(R.id.username_textView);
            holder.followButton = (FlatButton)convertView.findViewById(R.id.item_follow_button);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        ParseUser user = mParseUsers.get(position);
        Log.d("SingleTune", "Username from query is "+user.getUsername());
        holder.user_imageView.setImageResource(R.drawable.ic_action_picture);
        holder.usernameLabel.setText(user.getString(ParseConstants.KEY_USERNAME));

        return convertView                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
    }
//
    private static class ViewHolder{
        ImageView user_imageView;
        TextView usernameLabel;
        FlatButton followButton;
    }
}
