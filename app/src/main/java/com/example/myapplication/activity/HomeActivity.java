package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.myapplication.R;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.Tab1Fragment;
import com.example.myapplication.Tab2Fragment;
import com.example.myapplication.Tab3Fragment;
import com.example.myapplication.Tab4Fragment;
import com.example.myapplication.TabAdapter;
import com.example.myapplication.model.BaseResult;
import com.example.myapplication.model.Notification.BadgeNumber;
import com.example.myapplication.util.SharedPrefManager;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.tablayout) TabLayout tabLayout;

    private TabAdapter adapter;

    CircleImageView circleImageView;
    TextView fullname;

    TextView textCartItemCount;
    int mCartItemCount;

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSPLogin()==false){
            startActivity(new Intent(mContext, Login.class));
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        adapter = new TabAdapter(getSupportFragmentManager());
        if (sharedPrefManager.getSPObjectUser()!=null){
            JSONObject objUser = sharedPrefManager.getSPObjectUser();

            View header = navigationView.getHeaderView(0);
            fullname = (TextView) header.findViewById(R.id.profile_fullname);
            circleImageView = (CircleImageView) header.findViewById(R.id.profile_image);
            try {
                if (objUser.getString("fullName")!=null){
                    fullname.setText(objUser.getString("fullName"));
                }
                if (sharedPrefManager.getSPObjectUser().getString("avatar")!=null){
                    Picasso.get().load(sharedPrefManager.getSPObjectUser().getString("avatar")).into(circleImageView);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

//        add fragments
        adapter.addFragment(new Tab1Fragment(), "All");
        adapter.addFragment(new Tab2Fragment(), "Recent");
        adapter.addFragment(new Tab3Fragment(), "Past");
        adapter.addFragment(new Tab4Fragment(), "Self");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_item_chat:
                                startActivity(new Intent(mContext, Chat.class));
                                break;
                            case R.id.nav_item_qrCode:
                                startActivity(new Intent(mContext, ScanQRCode.class));
                                break;
                            case R.id.nav_item_creditCard:
                                startActivity(new Intent(mContext, ListCard.class));
                                break;
                            case R.id.nav_item_paymentHistory:
                                startActivity(new Intent(mContext, Payment.class));
                                break;
                            case R.id.nav_item_changePassword:
                                startActivity(new Intent(mContext, ChangePassword.class));
                                break;
                            case R.id.nav_item_logout:
                                getLogout();
                                break;
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        setupBadge();
        //Refresh your stuff here
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_notification);


        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_notification:
                startActivity(new Intent(mContext, Notification.class));
                break;
            case R.id.action_scanQR:
                startActivity(new Intent(mContext, ScanQRCode.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupBadge() {
        mApiService.getBadgeNumber().enqueue(new Callback<BadgeNumber>() {
            @Override
            public void onResponse(Call<BadgeNumber> call, Response<BadgeNumber> response) {
                if (response.isSuccessful())
                {
                    mCartItemCount = response.body().getResult();
                    Log.e("test","onFailure: ERROR 600 > " + mCartItemCount);
                    if (mCartItemCount == 0) {
                        if (textCartItemCount.getVisibility() != View.GONE) {
                            textCartItemCount.setVisibility(View.GONE);
                        }
                    } else {
                        textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                        if (textCartItemCount.getVisibility() != View.VISIBLE) {
                            textCartItemCount.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else {
                    if (response.code()==600){
                        JSONObject jsonError = null;
                        try {
                            jsonError = new JSONObject(response.errorBody().string());
                            Log.e("debug", "onFailure: ERROR 600 > " + jsonError.getJSONObject("error").getString("message") );
                            Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        signOutGoogle();
                        sharedPrefManager.logout();
                        sharedPrefManager.removeCookies();
                        startActivity(new Intent(mContext, Login.class));
                    }
                }
            }
            @Override
            public void onFailure(Call<BadgeNumber> call, Throwable t) {

            }
        });
    }


    public void get_profile(View v)
    {
        Intent register = new Intent(HomeActivity.this, profile.class);
        startActivity(register);
    }
    public void getLogout(){
        mApiService.getlogout().enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                if(response.isSuccessful()){
                    signOutGoogle();
                    sharedPrefManager.logout();
                    sharedPrefManager.removeCookies();
                    Toast.makeText(mContext, "Logout successfully!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(mContext, Login.class));
                }
                else
                {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Log.e("debug", "onFailure: ERROR 600 > " + jsonError.getJSONObject("error").getString("message") );
                        Toast.makeText(mContext, jsonError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {

            }
        });
    }

    private void signOutGoogle() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    // [END signOut]

}
