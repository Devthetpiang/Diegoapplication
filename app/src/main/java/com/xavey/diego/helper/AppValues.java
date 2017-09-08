package com.xavey.diego.helper;

import android.os.Environment;

import com.xavey.diego.adapter.CallNumberAdapter;
import com.xavey.diego.adapter.MellerAdapter;
import com.xavey.diego.api.model.CallNumber;
import com.xavey.diego.api.model.Meller;
import com.xavey.diego.api.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tinmaungaye on 5/19/15.
 */
public class AppValues{
    private static AppValues mInstance = null;

    private static String root = Environment.getExternalStorageDirectory().toString();
    public static File XAVEY_DIRECTORY = new File(root,"/MellOffline");

    public static User loginUser = new User();
    public static Boolean admin_login = false;

    public static String GCM_ID="";

    public static MellerAdapter mellerAdapter;
    public ArrayList<Meller> mellers = null;
    public static CallNumberAdapter callNumberAdapter;
    public ArrayList<CallNumber> mNumbers = null;

    public static String sync_none = "unsync";
    public static String sync_ok = "ok";
    public static String sync_skip = "skip";
    public static String sync_reg_ok = "reg_ok";
    public static String sync_reg_error = "reg_error";
    public static String sync_full_profile_error = "full_profile_error";

    public HashMap<String,String> mellermap = new HashMap<String,String>();

    private AppValues(){
        mellermap.put("tayzar","tayzar7");
        mellermap.put("khineeaindra","eaindra123");
        mellermap.put("eiyadana","eiyadanar7");
        mellermap.put("tayzar","tayzar7");
        mellermap.put("juujuu","doublej");
        mellermap.put("nweniwin","nweniwin15");
        mellermap.put("hanzar","hanzaw96");
        mellermap.put("eithinzar","eithinzar2");
        mellermap.put("tinzar","tinzar4");
        mellermap.put("nwenioo","nihon");
        mellermap.put("shwesin","shwe11");
    }

    public static AppValues getInstance(){
        if(mInstance == null)
        {
            mInstance = new AppValues();
        }
        return mInstance;
    }

    public static String getGcmId() {
        return GCM_ID;
    }

    public static void setGcmId(String gcmId) {
        GCM_ID = gcmId;
    }

}
