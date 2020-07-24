package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.DetailProfile;
import com.example.myapplication.model.Profile;
import com.example.myapplication.util.SharedPrefManager;
import com.example.myapplication.util.Validate;
import com.example.myapplication.util.api.BaseApiService;
import com.example.myapplication.util.api.UtilsApi;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @BindView(R.id.input_email) EditText input_email;
    @BindView(R.id.input_password) EditText input_password;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.txt_forgetPassword) TextView txt_forgetPassword;
    @BindView(R.id.txt_createAccount) TextView txt_createAccount;
    @BindView(R.id.txt_loginGoogle) TextView txt_loginGoogle;

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    Validate mValidate;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService(mContext);
        sharedPrefManager = new SharedPrefManager(this);
        mValidate = new Validate();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


//        click button
//        button login clicked
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//               validate all fields and call login api
                if (mValidate.validate(input_email) && mValidate.validate(input_password)){
                    login();
                }
            }
        });

//        textview txt_loginGoogle clicked
        txt_loginGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
//        textview txt_createAccount clicked
        txt_createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent createAccount = new Intent(mContext, Register.class);
                startActivity(createAccount);
            }
        });
//        textview txt_forgetPassword clicked
        txt_forgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent forgetPassword = new Intent(mContext, ForgetPassword.class);
                startActivity(forgetPassword);
            }
        });
//        if (sharedPrefManager.getSPLogin()){
//            Intent home = new Intent(mContext, HomeActivity.class);
//            startActivity(home);
//            finish();
//        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("debug", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    private void updateUI(@Nullable GoogleSignInAccount account) {

        if (account != null) {
            DetailProfile detailProfile = new DetailProfile(account.getId(), account.getEmail(), Objects.requireNonNull(account.getPhotoUrl()).toString(), account.getDisplayName());
            Profile profile = new Profile(detailProfile);

            Log.e("debug", account.getId() + account.getDisplayName() + account.getEmail() + account.getPhotoUrl() );
            mApiService.login_google(profile).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful())
                    {
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            JSONObject accessToken = null;
                            accessToken = jsonRESULTS.getJSONObject("result").getJSONObject("accessToken");
                            Toast.makeText(mContext, "Logined", Toast.LENGTH_SHORT).show();
                            sharedPrefManager.saveSPObjectUser(SharedPrefManager.SP_OBJUSER, jsonRESULTS.getJSONObject("result"));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_IDUSER,sharedPrefManager.getSPObjectUser().getString("_id") );
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, accessToken.getString("token") );
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);
                            startActivity(new Intent(mContext, HomeActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } else {
            Log.e("debug", "error 123");
        }
    }

    private void login(){
        mApiService.loginRequest(input_email.getText().toString(), input_password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                JSONObject accessToken = null;
                                accessToken = jsonRESULTS.getJSONObject("result").getJSONObject("accessToken");

                                Toast.makeText(mContext, "Logined", Toast.LENGTH_SHORT).show();
                                sharedPrefManager.saveSPObjectUser(SharedPrefManager.SP_OBJUSER, jsonRESULTS.getJSONObject("result"));
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_IDUSER,sharedPrefManager.getSPObjectUser().getString("_id") );
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, accessToken.getString("token") );
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);
                                Log.e("debug", "token phi save> " + sharedPrefManager.getSpToken());
                                Log.e("debug", "onFailure: sharepreferences > " + sharedPrefManager.getSPLogin() );
                                Log.e("debug", "onFailure: id is> " + sharedPrefManager.getSPObjectUser().getString("_id"));

                                startActivity(new Intent(mContext, HomeActivity.class));
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
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
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

}
