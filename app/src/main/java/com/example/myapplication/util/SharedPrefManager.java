package com.example.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;


public class SharedPrefManager{


    public static final String SP_APP = "spApp";
    public static final String SP_LOGIN = "spLogin";
    public static final String SP_OBJUSER = "spObjUser";
    public static final String SP_IDUSER = "spIdUser";
    public static final String SP_COOKIES = "spCookies";
    public static final String SP_TOKEN = "spToken";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    SharedPreferences.Editor memes;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void saveSPObjectUser(String keySP, JSONObject userObject){
        sp.edit().remove(keySP).apply();
        Gson gson = new Gson();
        String json = gson.toJson(userObject);
        spEditor.putString(keySP, json);
        spEditor.commit();
    }
    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public Boolean getSPLogin(){
        return sp.getBoolean(SP_LOGIN, false);
    }

    public JSONObject getSPObjectUser(){
        Gson gson = new Gson();
        String json = sp.getString(SP_OBJUSER, "");
        JSONObject obj = gson.fromJson(json, JSONObject.class);
        return  obj;
    }
    public String getSpIduser(){
        return sp.getString(SP_IDUSER, "");
    }
    public String getSpToken() { return sp.getString(SP_TOKEN, "");}

    public void logout(){
        spEditor.clear();
        spEditor.commit();
    }
    public void removeCookies(){
        memes.clear();
        memes.commit();
    }
}
