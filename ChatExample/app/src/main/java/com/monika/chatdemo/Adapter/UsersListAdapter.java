package com.monika.chatdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monika.chatdemo.Holder.SingleUser;
import com.monika.chatdemo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Monika on 2/8/2018.
 */

public class UsersListAdapter extends BaseAdapter {

    private LayoutInflater inflator;
    private ArrayList<SingleUser> buddyList;
    private Context context;
    public UsersListAdapter(Context con, ArrayList<SingleUser> userlist) {
        inflator = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        buddyList = userlist;
        context=con;
    }

    @Override
    public int getCount() {
        return buddyList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return buddyList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    private static class ViewHolder {
        TextView userName, statusMessage;
        ImageView status,imageViewUserAvatar;
    }

    @Override
    public View getView(int position, View vi, ViewGroup parent) {

        ViewHolder holder;

        if (vi == null) {
            holder = new ViewHolder();
            vi = inflator.inflate(R.layout.custom_userlist_item, null);
            holder.userName = (TextView) vi.findViewById(R.id.textViewUserName);
            holder.statusMessage = (TextView) vi.findViewById(R.id.textViewStatusMessage);
            holder.status = (ImageView) vi.findViewById(R.id.imageViewStatusIcon);
            holder.imageViewUserAvatar = (ImageView) vi.findViewById(R.id.imageViewUserAvatar);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        SingleUser user = buddyList.get(position);
        if (user != null) {
            holder.userName.setText(user.getName());
            holder.statusMessage.setText(user.getStatusMessage());
            Picasso.with(context)
                    .load(user.getProfile())
                    .into(holder.imageViewUserAvatar);
            String status = user.getStatus().trim();
            switch (status) {
                case "available":
                    holder.status.setImageResource(R.drawable.ic_user_available);
                    break;
                case "away":
                    holder.status.setImageResource(R.drawable.ic_user_away);
                    break;
                case "busy":
                    holder.status.setImageResource(R.drawable.ic_user_busy);
                    break;
                case "offline":
                    holder.status.setImageResource(R.drawable.ic_user_offline);
                    break;
                case "invisible":
                    holder.status.setImageResource(R.drawable.ic_user_offline);
                    break;
                default:
                    holder.status.setImageResource(R.drawable.ic_user_available);
                    break;
            }
        }
        return vi;
    }
}
