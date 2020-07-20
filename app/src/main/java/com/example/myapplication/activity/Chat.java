package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ChatAdapter;
import com.example.myapplication.model.Chat.Example;
import com.example.myapplication.model.Chat.Result;
import com.example.myapplication.util.SharedPrefManager;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {
    @BindView(R.id.rvListChat) RecyclerView rvListChat;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;
    @BindView(R.id.btn_sendChat) ImageButton btn_sendChat;
    @BindView(R.id.input_chat) EditText input_chat;

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    List<Result> listChat;
    public ChatAdapter chatAdapter;
    Socket socket;
    String Nickname, myUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        sharedPrefManager = new SharedPrefManager(this);
        myUserId = sharedPrefManager.getSpIduser();

        toolbar_title.setText("Chat with admin");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        try {
            Nickname = sharedPrefManager.getSPObjectUser().getString("fullName");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try
        {
        socket = IO.socket("https://event-chat.herokuapp.com");
        socket.connect();
            JSONObject clientSendUsername=new JSONObject();
            try
            {
                clientSendUsername.put("_id", myUserId);
                clientSendUsername.put("fullName", Nickname);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            socket.emit("client-send-Username", clientSendUsername);
    } catch (URISyntaxException e) {
        e.printStackTrace();

    }
        listChat = new ArrayList<>();
        mApiService.get_list_chat(sharedPrefManager.getSpIduser()).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()){
                    listChat = response.body().getResult();
                    Collections.reverse(listChat);
                    chatAdapter = new ChatAdapter(mContext, listChat, myUserId);
                    rvListChat.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
        Collections.reverse(listChat);
        rvListChat.setLayoutManager(new LinearLayoutManager(mContext));
        rvListChat.setItemAnimator(new DefaultItemAnimator());
        // message send action
        btn_sendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject clientMessage = new JSONObject();
                try
                {
                    clientMessage.put("content", input_chat.getText().toString());
                    clientMessage.put("id", myUserId);
                    clientMessage.put("fullName", Nickname);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                socket.emit("user-send-message", clientMessage);
                Date date = new Date();
                // make instance of message

                Result myChat = new Result(myUserId, "admin", Nickname, input_chat.getText().toString(), date);

                //add the message to the messageList

                listChat.add(myChat);
                // add the new updated list to the dapter
                chatAdapter = new ChatAdapter(mContext, listChat, myUserId);

                // notify the adapter to update the recycler view

                chatAdapter.notifyDataSetChanged();

                //set the adapter for the recycler view

                rvListChat.setAdapter(chatAdapter);
                //retrieve the nickname and the message content and fire the event messagedetectionif(!messagetxt.getText().toString().isEmpty()){
                input_chat.setText(" ");
        }
    });

        //implementing socket listeners

        socket.on("disconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];
                        Toast.makeText(mContext,data,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        socket.on("admin-reply", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event

                            String message = data.getString("content");
                            Date date = new Date();
                            // make instance of message

                            Result m = new Result("admin",myUserId, "admin", message, date);

                            //add the message to the messageList

                            listChat.add(m);
                            // add the new updated list to the dapter
                            chatAdapter = new ChatAdapter(mContext, listChat, myUserId);

                            // notify the adapter to update the recycler view

                            chatAdapter.notifyDataSetChanged();

                            //set the adapter for the recycler view

                            rvListChat.setAdapter(chatAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }
    private void getChats(){
        mApiService.get_list_chat(sharedPrefManager.getSpIduser()).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()){
                    listChat = response.body().getResult();
                    Collections.reverse(listChat);
                    chatAdapter = new ChatAdapter(mContext, listChat,myUserId);
                    rvListChat.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
