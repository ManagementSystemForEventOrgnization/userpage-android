package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.FileAdapter;
import com.example.myapplication.adapter.SessionEvent;
import com.example.myapplication.adapter.TimeAdapter;
import com.example.myapplication.model.DetailEvent.Event;
import com.example.myapplication.model.DetailEvent.Example;
import com.example.myapplication.model.ListEvent.Detail;
import com.example.myapplication.model.ListEvent.Document;
import com.example.myapplication.model.ListEvent.Session;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEvent extends AppCompatActivity implements OnMapReadyCallback{

    @BindView(R.id.img_banner) ImageView img_banner;
    @BindView(R.id.txt_name) TextView txt_name;
    @BindView(R.id.txt_status) TextView txt_status;
    @BindView(R.id.txt_comment) TextView txt_comment;
    @BindView(R.id.rvListDate) RecyclerView rvListDate;
//    bindview session detail
    @BindView(R.id.txt_joinNumber) TextView txt_joinNumber;
    @BindView(R.id.txt_address1) TextView txt_address;
    @BindView(R.id.roomMap) ImageView roomMap;
//    recyclerview
    @BindView(R.id.relatedFile) TextView relatedFile;
    @BindView(R.id.scheduleTime) TextView scheduleTime;
    @BindView(R.id.rvSchedules) RecyclerView rvSchedules;
    @BindView(R.id.rvFiles) RecyclerView rvFiles;
    @BindView(R.id.btn_applyEvent) Button btn_applyEvent;
    @BindView(R.id.btn_cancelEvent) Button btn_cancelEvent;
    @BindView(R.id.btn_returnListUser) TextView btn_returnListUser;

    @BindView(R.id.btn_back) TextView btn_back;
    @BindView(R.id.btn_scanQr) TextView btn_scanQr;
    @BindView(R.id.txt_priceDetail) TextView txt_priceDetail;
    @BindView(R.id.txt_categoryDetail) TextView txt_categoryDetail;
    @BindView(R.id.itemApplyUserDetail) LinearLayout itemApplyUserDetail;
    @BindView(R.id.img_user_apply_detail) CircleImageView img_user_apply_detail;
    @BindView(R.id.txt_fullname_userApply_detail) TextView txt_fullname_userApply_detail;

    @BindView(R.id.btn_getQrcode) TextView btn_getQrcode;
    @BindView(R.id.btn_scanQrcode) TextView btn_scanQrcode;


//    item in recyclerview
    SupportMapFragment mapFrag;
    private GoogleMap mMap;
    Session sessionItem;
    int joinNumber = 0;
    Event event;
    String eventId, typeTab,statusSession;
    Boolean isCancelSession = false;
    Boolean isReject = false;
    Date date = new Date();
    int positionMap = 0;
    Double Lat = -34.0;
    Double Lng= 154.0;

//    private MarkerOptions options = new MarkerOptions();


    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);


//        get Intent from tabs
        Intent intent = getIntent();
        eventId = intent.getStringExtra(Constants.KEY_ID);
        typeTab = intent.getStringExtra(Constants.KEY_STATUS);
//        adapter
        rvListDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvListDate.setItemAnimator(new DefaultItemAnimator());
//        set up maps

//        adapter list time
        rvSchedules.setLayoutManager(new LinearLayoutManager(this));
        rvSchedules.setItemAnimator(new DefaultItemAnimator());
//        adapter list file
        rvFiles.setLayoutManager(new LinearLayoutManager(this));
        rvFiles.setItemAnimator(new DefaultItemAnimator());
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getDetailEvent();

//        Lat = 10.7624229;
//        Lng = 106.6790081;
//        Lat = event.getSession().get(positionMap).getAddress().getMap().getLat();
//        Lng = event.getSession().get(positionMap).getAddress().getMap().getLng();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        getDetailEvent();
        //Refresh your stuff here
    }
    private void getDetailEvent(){
        mApiService.get_event_inf(eventId).enqueue(new Callback<Example>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful())
                {
                    event = response.body().getResult().getEvent();
                    Picasso.get().load(event.getBannerUrl()).into(img_banner);
                    txt_name.setText(event.getName());
                    txt_status.setText(event.getStatus());
                    txt_comment.setText(response.body().getResult().getCountComment().toString() + " comments");
//                    adapter session event
                    final List<Session> dateItems = event.getSession();
//                    rvListDate.setAdapter(new SessionEvent(mContext, dateItems));
                    getDetailSession(dateItems.get(0));
                    rvListDate.setAdapter(new SessionEvent(mContext, dateItems, new SessionEvent.MyAdapterListener() {
                        @Override
                        public void itemListSessionDateOnClick(View v, int position) {
                            sessionItem = dateItems.get(position);
                            positionMap = position;
                            getDetailSession(sessionItem);
                            Log.e("positionMap", Integer.toString(positionMap));
                        }
                    }));
                    if (event.getIsSellTicket()){
                        txt_priceDetail.setText( event.getTicket().getPrice().toString() + " VND");
                    }
                    else {
                        txt_priceDetail.setText("FREE");
                    }
                    txt_categoryDetail.setText(event.getEventCategory().getName());
                    if (!typeTab.equals("SELF")){
                        itemApplyUserDetail.setVisibility(View.VISIBLE);
                        txt_fullname_userApply_detail.setText(event.getUser().getFullName());
                        if(event.getUser().getAvatar()!=null && !event.getUser().getAvatar().equals(""))
                        {
                            Picasso.get().load(event.getUser().getAvatar()).into(img_user_apply_detail);
                        }
                        itemApplyUserDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(mContext, ProfileUser.class);
                                intent.putExtra(Constants.KEY_USERID, event.getUser().getId());
                                startActivity(intent);
                            }
                        });
                    }
                    txt_comment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent cmtScreen = new Intent(mContext, Comments.class);
                            cmtScreen.putExtra(Constants.KEY_EVENTID, event.getId());
                            cmtScreen.putExtra(Constants.KEY_EVENTNAME, event.getName());
                            startActivity(cmtScreen);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });
    }
    private void getDetailSession(Session sessionItem){
//                            each session
            if (sessionItem.getJoinNumber()==null){
                txt_joinNumber.setVisibility(View.GONE);
            }
            else{
                joinNumber = sessionItem.getJoinNumber();
                txt_joinNumber.setText(sessionItem.getJoinNumber().toString() + " people particated");
            }
            if(sessionItem.getAddress()!=null){
                if (sessionItem.getAddress().getLocation()!=null)
                {
                    txt_address.setText(sessionItem.getAddress().getLocation());
                }
                else{
                    txt_address.setVisibility(View.GONE);
                }
                if (sessionItem.getAddress().getDetailImage()!=null){
                    roomMap.setVisibility(View.VISIBLE);
                    Picasso.get().load(sessionItem.getAddress().getDetailImage()).into(roomMap);
                }
                else{
                    roomMap.setVisibility(View.GONE);
                }
            }
            else {
                txt_address.setVisibility(View.GONE);
                roomMap.setVisibility(View.GONE);
            }
//        Double lat1 = sessionItem.getAddress().getMap().getLat();
//        Log.e("debug detail",lat1.toString());
//
//        Double lng1 = sessionItem.getAddress().getMap().getLng();
//        Log.e ("debug lng detail", lng1.toString());

//        options.position(new LatLng(10.7624176, 106.6811968));
//        options.title("My event here");
//        options.snippet("someDesc");
//        googleMap.addMarker(options);

            List<Detail> detailItems = sessionItem.getDetail();
            if (detailItems.size()>0)
            {
                scheduleTime.setVisibility(View.VISIBLE);
            }
            rvSchedules.setAdapter(new TimeAdapter(mContext,detailItems));
            List<Document> documentItems = sessionItem.getDocuments();
            if (detailItems.size()>0)
            {
                relatedFile.setVisibility(View.VISIBLE);
            }
            rvFiles.setAdapter(new FileAdapter(mContext, documentItems));

            String statusEvent = event.getStatus();
//                            fields in a session
            String sessionId = sessionItem.getIdSession();

            if (sessionItem.getStatus()==null)
            {
                statusSession = "CANJOIN";
            }
            if (sessionItem.getIsCancel()==null)
            {
                statusSession = "CANJOIN";
            }

            if (sessionItem.getStatus()!=null)
            {
                statusSession = sessionItem.getStatus();
            }
            if (sessionItem.getIsCancel()!=null){
                isCancelSession = sessionItem.getIsCancel();
            }


//                            Boolean isCancelSession = sessionItem.getIsCancel();
            String[] arrIdSessions = {sessionId};
//                  handle button apply event && cancel event
            btn_applyEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent choosePaymentScreen = new Intent(mContext, ChoosePayment.class);
                    choosePaymentScreen.putExtra(Constants.KEY_EVENTID, event.getId());
                    choosePaymentScreen.putExtra(Constants.KEY_SESSIONID, sessionId);
                    startActivity(choosePaymentScreen);
                }
            });

            btn_cancelEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                                    show verify notification to cancel session
                    mApiService.cancelEvent(event.getId(), arrIdSessions).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(mContext,"Canceled", Toast.LENGTH_LONG).show();
                                getIntent();
                            }
                            else
                            {
                                try {
                                    JSONObject jsonError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            });


//                            condition to set visible button
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(date);
        String sessionDate = sdf.format(sessionItem.getDay());
        if (sessionItem.getIsReject()!=null){
            isReject = sessionItem.getIsReject();
        }

        if ( typeTab.equals("RECENT") || (typeTab.equals("ALL") &&
                Integer.parseInt(sessionDate)>=Integer.parseInt(currentDate)))
            {
//                delete & reject event
                if( isCancelSession || isReject || !statusEvent.equals("PUBLIC") || joinNumber>=sessionItem.getLimitNumber())
                {
                    btn_cancelEvent.setVisibility(View.GONE);
                    btn_applyEvent.setVisibility(View.GONE);
                }
                else if(statusSession.equals("JOINED")){
                    btn_cancelEvent.setVisibility(View.VISIBLE);
                    btn_applyEvent.setVisibility(View.GONE);
//                    check time before start event session
                    btn_scanQrcode.setVisibility(View.VISIBLE);
                    btn_scanQrcode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ScanQRCode.class);
                            intent.putExtra(Constants.KEY_EVENTID, eventId);
                            intent.putExtra(Constants.KEY_SESSIONID, sessionItem.getIdSession());
                            startActivity(intent);
                        }
                    });

                }
                else {
                    btn_applyEvent.setVisibility(View.VISIBLE);
                    btn_cancelEvent.setVisibility(View.GONE);
                }
            }

        if(typeTab.equals("SELF"))
        {
            btn_getQrcode.setVisibility(View.VISIBLE);
            btn_getQrcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, QRCode.class);
                    intent.putExtra(Constants.KEY_SESSIONID, sessionItem.getIdSession());
                    intent.putExtra(Constants.KEY_EVENTNAME, event.getName());
                    intent.putExtra(Constants.KEY_EVENTID, eventId);
                    intent.putExtra(Constants.KEY_SESSIONNAME,sessionItem.getName() !=null ? sessionItem.getName() : "My session");
                    startActivity(intent);
                }
            });
        }
        if(typeTab.equals("SELF") && joinNumber>0)
        {
            btn_returnListUser.setVisibility(View.VISIBLE);
            btn_returnListUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ListApplyUser.class);
                    intent.putExtra(Constants.KEY_EVENTNAME, event.getName());
                    intent.putExtra(Constants.KEY_EVENTID, eventId);
                    startActivity(intent);
                }
            });
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getApplicationContext());

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        10.7624229
//        106.6790081
//        LatLng sydney = new LatLng(sessionItem.getAddress().getMap().getLat(), sessionItem.getAddress().getMap().getLng());
        Log.e("position test", Integer.toString(positionMap));
        mApiService.get_event_inf(eventId).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()){
                    event = response.body().getResult().getEvent();
                    Lat = event.getSession().get(positionMap).getAddress().getMap().getLat();
                    Lng = event.getSession().get(positionMap).getAddress().getMap().getLng();
                    LatLng myplace = new LatLng(Lat, Lng);
                    mMap.addMarker(new MarkerOptions().position(myplace).title("My event"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myplace));
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
}
