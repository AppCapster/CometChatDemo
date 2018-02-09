package com.monika.chatdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.inscripts.interfaces.Callbacks;
import com.inscripts.interfaces.SubscribeCallbacks;
import com.inscripts.utils.Logger;
import com.monika.chatdemo.Adapter.UsersListAdapter;
import com.monika.chatdemo.Common.APIConfig;
import com.monika.chatdemo.Common.SharedPreferenceHelper;
import com.monika.chatdemo.Holder.SingleUser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

public class MyCometChatActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MyCometChatActivity";
    private CometChat cometchat;

    private ListView usersListView;
    private UsersListAdapter adapter;

    private ArrayList<String> list;
    private ArrayList<SingleUser> usersList = new ArrayList<SingleUser>();
    private Boolean flag=true;
    private LinearLayout layoutProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comet_chat);

        cometchat = CometChat.getInstance(getApplicationContext());
        SharedPreferenceHelper.initialize(this);

        usersListView = (ListView) findViewById(R.id.listViewUsers);
        layoutProgress  =(LinearLayout)findViewById(R.id.layoutProgress);

        list = new ArrayList<String>();

        usersListView.setOnItemClickListener(this);
        adapter = new UsersListAdapter(this, usersList);
        usersListView.setAdapter(adapter);
        layoutProgress.setVisibility(View.VISIBLE);
        final SubscribeCallbacks subCallbacks = new SubscribeCallbacks(){
            @Override
            public void gotOnlineList(JSONObject jsonObject) {
                Log.d(TAG, "gotOnlineList: "+jsonObject.toString());
                SharedPreferenceHelper.save(APIConfig.USERS_LIST, jsonObject.toString());
                if (list.size() <= 0) {
                    populateList();
                }
            }

            @Override
            public void gotBotList(JSONObject jsonObject) {
            }

            @Override
            public void gotRecentChatsList(JSONObject jsonObject) {


            }

            @Override
            public void onError(JSONObject jsonObject) {
                Log.e(TAG, "onError: "+jsonObject);

            }

            @Override
            public void onMessageReceived(JSONObject jsonObject) {
                Log.i(TAG, "onMessageReceived: "+jsonObject);

            }

            @Override
            public void gotProfileInfo(JSONObject jsonObject) {
                Log.d(TAG, "gotProfileInfo: "+jsonObject);
                SharedPreferenceHelper.save(APIConfig.USER_PROFILE, jsonObject.toString());
            }

            @Override
            public void gotAnnouncement(JSONObject jsonObject) {


            }

            @Override
            public void onAVChatMessageReceived(JSONObject jsonObject) {


            }

            @Override
            public void onActionMessageReceived(JSONObject jsonObject) {


            }

            @Override
            public void onGroupMessageReceived(JSONObject jsonObject) {

            }

            @Override
            public void onGroupsError(JSONObject jsonObject) {

            }

            @Override
            public void onLeaveGroup(JSONObject jsonObject) {

            }

            @Override
            public void gotGroupList(JSONObject jsonObject) {

            }

            @Override
            public void gotGroupMembers(JSONObject jsonObject) {

            }

            @Override
            public void onGroupAVChatMessageReceived(JSONObject jsonObject) {

            }

            @Override
            public void onGroupActionMessageReceived(JSONObject jsonObject) {

            }
        };
        cometchat.initializeCometChat(APIConfig.siteUrl,APIConfig.licenseKey,APIConfig.apiKey,APIConfig.isCometOnDemand,new Callbacks(){
            @Override
            public void successCallback(JSONObject jsonObject) {
                Log.d(TAG, "successCallback: "+jsonObject);
                if (CometChat.isLoggedIn()) {
                    cometchat.subscribe(true, subCallbacks);
                }

            }

            @Override
            public void failCallback(JSONObject jsonObject) {
                Log.e(TAG, "failCallback: "+jsonObject);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    public void populateList() {
        try {
            if (null != list && null != usersList && null != adapter) {
                JSONObject onlineUsers;

                if (SharedPreferenceHelper.contains(APIConfig.USERS_LIST)) {
                    onlineUsers = new JSONObject(SharedPreferenceHelper.get(APIConfig.USERS_LIST));
                } else {
                    onlineUsers = new JSONObject();
                }
                Log.e(TAG, "populateList: "+onlineUsers.toString());
                Iterator<String> keys = onlineUsers.keys();
                list.clear();
                usersList.clear();
                /*for(Iterator it = onlineUsers.keys(); it.hasNext(); ) {
                    String key = (String) it.next();
                    Log.e(TAG, key+"-->: "+onlineUsers.get(key).toString());
                }*/
                while (keys.hasNext()) {
                    JSONObject user = onlineUsers.getJSONObject(keys.next().toString());
                    String username = user.getString("n");
                    list.add(username);
                    usersList.add(new SingleUser(username, user.getInt("id"), user.getString("m"), user.getString("s"), user.getString("a")));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layoutProgress.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        SingleUser user = usersList.get(arg2);

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("user_id", user.getId());
        intent.putExtra("user_name", user.getName());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(this, UsersListActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }

        return true;
    }
}
