package com.monika.chatdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.inscripts.custom.RecyclerTouchListener;
import com.monika.chatdemo.Adapter.UsersListAdapter;
import com.monika.chatdemo.Common.APIConfig;
import com.monika.chatdemo.Common.SharedPreferenceHelper;
import com.monika.chatdemo.Holder.SingleUser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.Iterator;

public class UsersListActivity extends AppCompatActivity{

    ImageView user_profile_photo;
    TextView user_profile_name,user_profile_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        user_profile_photo  = (ImageView)findViewById(R.id.user_profile_photo);
        user_profile_name = (TextView)findViewById(R.id.user_profile_name);
        user_profile_status = (TextView)findViewById(R.id.user_profile_status);

    }
    @Override
    protected void onStart() {
        super.onStart();
        getInfo();
    }
    public void getInfo() {
        try {

            JSONObject USER_PROFILE;

            if (SharedPreferenceHelper.contains(APIConfig.USER_PROFILE)) {
                USER_PROFILE = new JSONObject(SharedPreferenceHelper.get(APIConfig.USER_PROFILE));
            } else {
                USER_PROFILE = new JSONObject();
            }
            SharedPreferenceHelper.save(APIConfig.USER_ID, USER_PROFILE.getString("id"));
            user_profile_name.setText(USER_PROFILE.getString("n"));
            user_profile_status.setText(USER_PROFILE.getString("m"));
            Picasso.with(getApplicationContext())
                    .load(USER_PROFILE.getString("a"))
                    .into(user_profile_photo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferenceHelper.removeKey(APIConfig.USERS_LIST);
                SharedPreferenceHelper.removeKey(APIConfig.USER_NAME);
                SharedPreferenceHelper.removeKey(APIConfig.USER_ID);
                SharedPreferenceHelper.removeKey(APIConfig.USER_PROFILE);
                startActivity(new Intent(UsersListActivity.this, LoginActivity.class));
                finish();
                break;

            default:
                break;
        }

        return true;
    }
}
