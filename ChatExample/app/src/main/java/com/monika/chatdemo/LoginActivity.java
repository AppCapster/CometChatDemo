package com.monika.chatdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.inscripts.interfaces.Callbacks;
import com.inscripts.utils.Logger;
import com.monika.chatdemo.Common.APIConfig;
import com.monika.chatdemo.Common.SharedPreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText usernameField;
    Button loginBtn;
    private CometChat cometchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (EditText) findViewById(R.id.txtUserName);
        loginBtn = (Button) findViewById(R.id.btnLogin);

        cometchat = CometChat.getInstance(getApplicationContext());
        SharedPreferenceHelper.initialize(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String username = usernameField.getText().toString().trim();
                cometchat.initializeCometChat(APIConfig.siteUrl,APIConfig.licenseKey,APIConfig.apiKey,APIConfig.isCometOnDemand,new Callbacks(){
                    @Override
                    public void successCallback(JSONObject jsonObject) {
                        cometchat.login( username, new Callbacks() {

                            @Override
                            public void successCallback(JSONObject success) {
                                SharedPreferenceHelper.save(APIConfig.USER_NAME, username);
                                SharedPreferenceHelper.save(APIConfig.IS_LOGGEDIN, "1");
                                startCometchat();
                            }

                            @Override
                            public void failCallback(JSONObject fail) {
                                usernameField.setError("Incorrect userid");
                            }
                        });
                    }

                    @Override
                    public void failCallback(JSONObject jsonObject) {
                        Log.e(TAG, "failCallback: "+jsonObject);
                        usernameField.setError("Incorrect username");
                    }
                });


            }
        });
    }
    private void startCometchat() {
        Intent intent = new Intent(LoginActivity.this, MyCometChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
