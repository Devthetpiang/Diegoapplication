package com.xavey.diego.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telecom.Call;
import android.util.Log;

import com.xavey.diego.api.model.Auth;
import com.xavey.diego.api.model.CallNumber;
import com.xavey.diego.api.model.Meller;
import com.xavey.diego.api.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tinmaungaye on 4/9/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MellOffline";

    // Table Names
    protected static final String TABLE_NOTI = "noti";
    protected static final String TABLE_AUTH = "auth";
    protected static final String TABLE_USER = "user";
    protected static final String TABLE_MELLER = "meller";
    protected static final String TABLE_CALL_LIST = "call_list";

    // post column names
    protected static final String N__ID = "_id";
    protected static final String N_USER = "user";
    protected static final String N_TITLE = "title";
    protected static final String N_POST = "post";
    protected static final String N_CREATED_ON = "created";
    protected static final String N_TYPE = "type";
    protected static final String N_CHECKED = "checked";

    // auth column names
    protected static final String A_ATOKENT = "access_token";
    protected static final String A_RTOKEN = "refresh_token";
    protected static final String A_EXPIRESIN = "expires_in";
    protected static final String A_TOKENTYPE = "token_type";

    // user column names
    protected static final String U__ID = "_id";
    protected static final String U_USER_NAME = "user_name";
    protected static final String U_HASHED_PASSWORD = "hashed_password";
    protected static final String U_NAME = "name";
    protected static final String U_EMAIL = "email";
    protected static final String U_PHONE = "phone";
    protected static final String U_DOB = "dob";

    // meller column names
    protected static final String M_ID = "_id";
    protected static final String M_PHONE = "phone"; //user
    protected static final String M_FULLNAME = "full_name"; //user
    protected static final String M_GENDER = "gender"; //user
    protected static final String M_CURRENT_CITY = "current_city"; //user
    protected static final String M_DOB = "dob"; //user
    protected static final String M_NRIC = "nric"; //user
    protected static final String M_EDU = "edu"; //user
    protected static final String M_INCOME = "income"; //user
    protected static final String M_MARITAL = "marital";
    protected static final String M_EMPLOYMENT = "employment";
    protected static final String M_CAREER = "career";
    protected static final String M_JOBFUNC = "job_function";
    protected static final String M_INDUSTRY = "industry";
    protected static final String M_TELCO = "telco";
    protected static final String M_RELIGION = "religion";
    protected static final String M_RACE = "race";
    protected static final String M_BANK = "bank_account";
    protected static final String M_SMOKE = "smoker";
    protected static final String M_DRINK = "drinker";
    protected static final String M_CREATED = "created_on";
    protected static final String M_REFERRER = "referrer_id";
    protected static final String M_STATUS = "status";

    // number column names
    protected static final String P_ID = "_id";
    protected static final String P_NUMBER = "number";
    protected static final String P_STATUS = "status";
    protected static final String P_ASSIGNED_ID = "assigned_id";
    protected static final String P_LASTCALLED = "last_called";

    // Table Create Statements

    // Auth table create statement
    private static final String CREATE_TABLE_AUTH = "CREATE TABLE IF NOT EXISTS "
            + TABLE_AUTH + "(" + A_ATOKENT + " TEXT PRIMARY KEY,"
            + A_RTOKEN + " TEXT,"
            + A_EXPIRESIN + " INTEGER,"
            + A_TOKENTYPE + " TEXT" + ")";

    // User table create statement
    private static final String CREATE_TABLE_MELLER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_MELLER + "(" + M_ID + " TEXT PRIMARY KEY,"
            + M_PHONE + " TEXT,"
            + M_FULLNAME + " TEXT,"
            + M_GENDER + " TEXT,"
            + M_CURRENT_CITY + " TEXT,"
            + M_DOB + " DATETIME,"
            + M_NRIC + " TEXT,"
            + M_EDU + " TEXT,"
            + M_INCOME + " TEXT,"
            + M_MARITAL + " TEXT,"
            + M_EMPLOYMENT + " TEXT,"
            + M_CAREER + " TEXT,"
            + M_JOBFUNC + " TEXT,"
            + M_INDUSTRY + " TEXT,"
            + M_TELCO + " TEXT,"
            + M_RELIGION+ " TEXT,"
            + M_RACE + " TEXT,"
            + M_BANK + " TEXT,"
            + M_SMOKE + " TEXT,"
            + M_DRINK + " TEXT,"
            + M_CREATED + " TEXT,"
            + M_REFERRER + " TEXT,"
            + M_STATUS + " TEXT," +
            "unique ("+M_PHONE+"))";

    // Number table create statement
    private static final String CREATE_TABLE_CALLLIST = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CALL_LIST + "(" + P_ID + " TEXT PRIMARY KEY,"
            + P_NUMBER + " TEXT,"
            + P_STATUS + " TEXT,"
            + P_ASSIGNED_ID + " TEXT,"
            + P_LASTCALLED + " DATETIME" + ")";

    // User table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER + "(" + U__ID + " TEXT PRIMARY KEY,"
            + U_USER_NAME + " TEXT,"
            + U_HASHED_PASSWORD + " TEXT,"
            + U_NAME + " TEXT,"
            + U_EMAIL + " TEXT,"
            + U_PHONE + " TEXT,"
            + U_DOB + " DATETIME" + ")";

    private static final String CREATE_TABLE_NOTI = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NOTI + "(" + N__ID + " TEXT PRIMARY KEY,"
            + N_CHECKED + " INTEGER,"
            + N_CREATED_ON + " DATETIME,"
            + N_USER + " TEXT,"
            + N_POST + " TEXT,"
            + N_TITLE + " TEXT,"
            + N_TYPE + " TEXT " + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MELLER);
        db.execSQL(CREATE_TABLE_CALLLIST);
        db.execSQL(CREATE_TABLE_AUTH);
        db.execSQL(CREATE_TABLE_NOTI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MELLER);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALL_LIST);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTH);
        // create new tables
//        onCreate(db);
    }

    public void updateCheckedNoti(String id, Date date) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(CREATE_TABLE_NOTI); //with if not exists
        ContentValues cv=new ContentValues();
        cv.put(N_CHECKED, 1);
        db.update(TABLE_NOTI, cv, N_POST + "='" + id + "' AND date(" + N_CREATED_ON + ")<=?",
                new String[]{"date('" + UtilityHelper.getDateTime(date, false) + "')"});
    }

    public void deleteNoti() throws Exception{
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NOTI, null, null);
    }

    public void createAuth(Auth a) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(A_ATOKENT, a.getAccess_token());
        values.put(A_RTOKEN, a.getRefresh_token());
        values.put(A_EXPIRESIN, a.getExpires_in());
        values.put(A_TOKENTYPE, a.getToken_type());

        //maintain single row in auth table
        db.delete(TABLE_AUTH, null, null);
        //inset new row
        db.insert(TABLE_AUTH, null, values);
    }

    public Auth getAUTH() throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_AUTH + " LIMIT 1";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        Auth a = new Auth();
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            a.setAccess_token(c.getString(c.getColumnIndex(A_ATOKENT)));
            a.setRefresh_token(c.getString(c.getColumnIndex(A_RTOKEN)));
            a.setExpires_in(c.getInt(c.getColumnIndex(A_EXPIRESIN)));
            a.setToken_type(c.getString(c.getColumnIndex(A_TOKENTYPE)));
        }
        return a;
    }

    public void deleteAuth() throws Exception{
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_AUTH, null, null);
    }

    public void createUser(User u) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U__ID, u.get_id());
        values.put(U_HASHED_PASSWORD, u.getHashed_password());
        values.put(U_NAME, u.getFull_name());
        values.put(U_USER_NAME,u.getUser_name());
        db.insert(TABLE_USER, null, values);
    }

    public void createMeller(Meller meller) throws  Exception {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(M_PHONE, meller.getPhone());
        values.put(M_FULLNAME, meller.getFull_name());
        values.put(M_GENDER, meller.getGender());
        values.put(M_CURRENT_CITY, meller.getCurrent_city());
        values.put(M_DOB, UtilityHelper.getDateTime(meller.getDob(), true));
        values.put(M_NRIC, meller.getNric());
        values.put(M_EDU, meller.getEducation());
        values.put(M_INCOME, meller.getIncome());
        values.put(M_MARITAL, meller.getMarital());
        values.put(M_EMPLOYMENT, meller.getEmployment());
        values.put(M_CAREER, meller.getCareer());
        values.put(M_INCOME, meller.getIncome());
        values.put(M_JOBFUNC, meller.getJobFunction());
        values.put(M_TELCO, meller.getTelco());
        values.put(M_RELIGION, meller.getReligion());
        values.put(M_RACE, meller.getRace());
        values.put(M_BANK, meller.getBankAccount());
        values.put(M_SMOKE, meller.getSmoker());
        values.put(M_DRINK, meller.getDrinker());
        values.put(M_CREATED, UtilityHelper.getDateTime(meller.getCreatedOn(),false).toString());
        values.put(M_REFERRER, meller.getReferrer());
        values.put(M_STATUS, meller.getStatus());

        db.insert(TABLE_MELLER, null, values);
    }

    public void updateMeller(Meller meller) throws  Exception {
        SQLiteDatabase db = getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MELLER + " WHERE " + M_PHONE + " = '" + meller.getPhone() + "' LIMIT 1";
        Log.e(LOG, selectQuery);
        Cursor mCursor = db.rawQuery(selectQuery, null);

        //Meller m = null;

        if (mCursor != null && mCursor.getCount() > 1) {
            mCursor.close();

        } else {

            ContentValues values = new ContentValues();
            values.put(M_PHONE, meller.getPhone());
            values.put(M_FULLNAME, meller.getFull_name());
            values.put(M_GENDER, meller.getGender());
            values.put(M_CURRENT_CITY, meller.getCurrent_city());
            values.put(M_DOB, UtilityHelper.getDateTime(meller.getDob(), true));
            values.put(M_NRIC, meller.getNric());
            values.put(M_EDU, meller.getEducation());
            values.put(M_INCOME, meller.getIncome());
            values.put(M_MARITAL, meller.getMarital());
            values.put(M_EMPLOYMENT, meller.getEmployment());
            values.put(M_CAREER, meller.getCareer());
            values.put(M_INCOME, meller.getIncome());
            values.put(M_JOBFUNC, meller.getJobFunction());
            values.put(M_TELCO, meller.getTelco());
            values.put(M_RELIGION, meller.getReligion());
            values.put(M_RACE, meller.getRace());
            values.put(M_BANK, meller.getBankAccount());
            values.put(M_SMOKE, meller.getSmoker());
            values.put(M_DRINK, meller.getDrinker());
            values.put(M_CREATED, UtilityHelper.getDateTime(meller.getCreatedOn(), false).toString());
            values.put(M_REFERRER, meller.getReferrer());
            values.put(M_STATUS, meller.getStatus());

            db.update(TABLE_MELLER, values, M_PHONE + "='" + meller.getPhone() + "'",null);
        }
    }

    public void deleteUser() throws Exception{
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, null, null);
    }

    public User login(String id, String pwd) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + U_USER_NAME + " = '" +
                id + "' AND " + U_HASHED_PASSWORD + " = '" + pwd +
                "' LIMIT 1";

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        User loginUser = new User();

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            loginUser.set_id(c.getString(c.getColumnIndex(U__ID)));
            //u.setDob(getDateTime(c.getString(c.getColumnIndex(U_DOB))));
            loginUser.setEmail(c.getString(c.getColumnIndex(U_EMAIL)));
            loginUser.setHashed_password(c.getString(c.getColumnIndex(U_HASHED_PASSWORD)));
            loginUser.setPhone(c.getString(c.getColumnIndex(U_PHONE)));
            loginUser.setUser_name(c.getString(c.getColumnIndex(U_USER_NAME)));
            loginUser.setFullName(c.getString(c.getColumnIndex(U_NAME)));
        }

        return loginUser;
    }

    public ArrayList<Meller> getMellers() throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MELLER +" WHERE "+M_REFERRER+"='"+AppValues.getInstance().loginUser.getUser_name()+"'";
        if(AppValues.getInstance().admin_login){
            selectQuery = "SELECT * FROM " + TABLE_MELLER +"";
        }
        Log.e(LOG, selectQuery);
        Cursor mCursor = db.rawQuery(selectQuery, null);

        ArrayList<Meller> mellers = new ArrayList<Meller>();

        if (mCursor.moveToFirst()) {
            do {
                Meller m = new Meller();

                m.setFullName(mCursor.getString(mCursor.getColumnIndex(M_FULLNAME)));
                m.setPhone(mCursor.getString(mCursor.getColumnIndex(M_PHONE)));
                m.setGender(mCursor.getString(mCursor.getColumnIndex(M_GENDER)));
                m.setDob(UtilityHelper.getDateTime(mCursor.getString(mCursor.getColumnIndex(M_DOB)),true));
                m.setCurrentCity(mCursor.getString(mCursor.getColumnIndex(M_CURRENT_CITY)));
                m.setNric(mCursor.getString(mCursor.getColumnIndex(M_NRIC)));
                m.setEducation(mCursor.getString(mCursor.getColumnIndex(M_EDU)));
                m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
                m.setMarital(mCursor.getString(mCursor.getColumnIndex(M_MARITAL)));
                m.setEmployment(mCursor.getString(mCursor.getColumnIndex(M_EMPLOYMENT)));
                m.setCareer(mCursor.getString(mCursor.getColumnIndex(M_CAREER)));
                m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
                m.setJobFunction(mCursor.getString(mCursor.getColumnIndex(M_JOBFUNC)));
                m.setTelco(mCursor.getString(mCursor.getColumnIndex(M_TELCO)));
                m.setReligion(mCursor.getString(mCursor.getColumnIndex(M_RELIGION)));
                m.setRace(mCursor.getString(mCursor.getColumnIndex(M_RACE)));
                m.setBankAccount(mCursor.getString(mCursor.getColumnIndex(M_BANK)));
                m.setSmoker(mCursor.getString(mCursor.getColumnIndex(M_SMOKE)));
                m.setDrinker(mCursor.getString(mCursor.getColumnIndex(M_DRINK)));

                String strCreatedOn = mCursor.getString(mCursor.getColumnIndex(M_CREATED));

                m.setCreatedOn(UtilityHelper.getDateTime(strCreatedOn,false));
                m.setReferrer(mCursor.getString(mCursor.getColumnIndex(M_REFERRER)));
                m.setStatus(mCursor.getString(mCursor.getColumnIndex(M_STATUS)));

                try {
                    mellers.add(m);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return mellers;
    }

    public Meller getMellerUnSynced() throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MELLER + " WHERE "
                                +M_REFERRER+"='"+AppValues.getInstance().loginUser.getUser_name()+"' AND "
                                +M_PHONE+"!='' AND "
                                + "("+M_STATUS +" = '"+ AppValues.getInstance().sync_none + "' OR "
                                +  M_STATUS +" = '"+ AppValues.getInstance().sync_reg_ok +"') LIMIT 1";
        if(AppValues.getInstance().admin_login){
            selectQuery = "SELECT * FROM " + TABLE_MELLER + " WHERE "
                    +M_PHONE+"!='' AND "
                    + "("+M_STATUS +" = '"+ AppValues.getInstance().sync_none + "' OR "
                    +  M_STATUS +" = '"+ AppValues.getInstance().sync_reg_ok +"') LIMIT 1";
        }
        Log.e(LOG, selectQuery);
        Cursor mCursor = db.rawQuery(selectQuery, null);

        Meller m = null;

        if (mCursor != null && mCursor.getCount()>0) {
            mCursor.moveToFirst();
            m = new Meller();
            m.set_id(mCursor.getString(mCursor.getColumnIndex(M_ID)));
            m.setFullName(mCursor.getString(mCursor.getColumnIndex(M_FULLNAME)));
            m.setPhone(mCursor.getString(mCursor.getColumnIndex(M_PHONE)));
            m.setGender(mCursor.getString(mCursor.getColumnIndex(M_GENDER)));
            m.setDob(UtilityHelper.getDateTime(mCursor.getString(mCursor.getColumnIndex(M_DOB)), true));
            m.setCurrentCity(mCursor.getString(mCursor.getColumnIndex(M_CURRENT_CITY)));
            m.setNric(mCursor.getString(mCursor.getColumnIndex(M_NRIC)));
            m.setEducation(mCursor.getString(mCursor.getColumnIndex(M_EDU)));
            m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
            m.setMarital(mCursor.getString(mCursor.getColumnIndex(M_MARITAL)));
            m.setEmployment(mCursor.getString(mCursor.getColumnIndex(M_EMPLOYMENT)));
            m.setCareer(mCursor.getString(mCursor.getColumnIndex(M_CAREER)));
            m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
            m.setJobFunction(mCursor.getString(mCursor.getColumnIndex(M_JOBFUNC)));
            m.setTelco(mCursor.getString(mCursor.getColumnIndex(M_TELCO)));
            m.setReligion(mCursor.getString(mCursor.getColumnIndex(M_RELIGION)));
            m.setRace(mCursor.getString(mCursor.getColumnIndex(M_RACE)));
            m.setBankAccount(mCursor.getString(mCursor.getColumnIndex(M_BANK)));
            m.setSmoker(mCursor.getString(mCursor.getColumnIndex(M_SMOKE)));
            m.setDrinker(mCursor.getString(mCursor.getColumnIndex(M_DRINK)));

            String strCreatedOn = mCursor.getString(mCursor.getColumnIndex(M_CREATED));

            m.setCreatedOn(UtilityHelper.getDateTime(strCreatedOn, false));
            m.setReferrer(mCursor.getString(mCursor.getColumnIndex(M_REFERRER)));
            m.setStatus(mCursor.getString(mCursor.getColumnIndex(M_STATUS)));
            mCursor.close();
        }
        return m;
    }

    public Meller getMellerSynced() throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MELLER + " WHERE "
                +M_REFERRER+"='"+AppValues.getInstance().loginUser.getUser_name()+"' AND "
                +M_PHONE+"!='' AND "
                + "("+M_STATUS +" != '"+ AppValues.getInstance().sync_none + "') LIMIT 1";

        if(AppValues.getInstance().admin_login){
            selectQuery = "SELECT * FROM " + TABLE_MELLER + " WHERE "
                    +M_PHONE+"!='' AND "
                    + "("+M_STATUS +" != '"+ AppValues.getInstance().sync_none + "') LIMIT 1";
        }

        Log.e(LOG, selectQuery);
        Cursor mCursor = db.rawQuery(selectQuery, null);

        Meller m = null;

        if (mCursor != null && mCursor.getCount()>0) {
            mCursor.moveToFirst();
            m = new Meller();
            m.set_id(mCursor.getString(mCursor.getColumnIndex(M_ID)));
            m.setFullName(mCursor.getString(mCursor.getColumnIndex(M_FULLNAME)));
            m.setPhone(mCursor.getString(mCursor.getColumnIndex(M_PHONE)));
            m.setGender(mCursor.getString(mCursor.getColumnIndex(M_GENDER)));
            m.setDob(UtilityHelper.getDateTime(mCursor.getString(mCursor.getColumnIndex(M_DOB)), true));
            m.setCurrentCity(mCursor.getString(mCursor.getColumnIndex(M_CURRENT_CITY)));
            m.setNric(mCursor.getString(mCursor.getColumnIndex(M_NRIC)));
            m.setEducation(mCursor.getString(mCursor.getColumnIndex(M_EDU)));
            m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
            m.setMarital(mCursor.getString(mCursor.getColumnIndex(M_MARITAL)));
            m.setEmployment(mCursor.getString(mCursor.getColumnIndex(M_EMPLOYMENT)));
            m.setCareer(mCursor.getString(mCursor.getColumnIndex(M_CAREER)));
            m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
            m.setJobFunction(mCursor.getString(mCursor.getColumnIndex(M_JOBFUNC)));
            m.setTelco(mCursor.getString(mCursor.getColumnIndex(M_TELCO)));
            m.setReligion(mCursor.getString(mCursor.getColumnIndex(M_RELIGION)));
            m.setRace(mCursor.getString(mCursor.getColumnIndex(M_RACE)));
            m.setBankAccount(mCursor.getString(mCursor.getColumnIndex(M_BANK)));
            m.setSmoker(mCursor.getString(mCursor.getColumnIndex(M_SMOKE)));
            m.setDrinker(mCursor.getString(mCursor.getColumnIndex(M_DRINK)));

            String strCreatedOn = mCursor.getString(mCursor.getColumnIndex(M_CREATED));

            m.setCreatedOn(UtilityHelper.getDateTime(strCreatedOn, false));
            m.setReferrer(mCursor.getString(mCursor.getColumnIndex(M_REFERRER)));
            m.setStatus(mCursor.getString(mCursor.getColumnIndex(M_STATUS)));
            mCursor.close();
        }

        return m;
    }

    public Meller getMellerByPhone(String phone) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MELLER + " WHERE "+ M_PHONE +" = '"+ phone +"' LIMIT 1";
        Log.e(LOG, selectQuery);
        Cursor mCursor = db.rawQuery(selectQuery, null);

        Meller m = null;

        if (mCursor != null && mCursor.getCount()>0) {
            mCursor.moveToFirst();
            m = new Meller();
            m.set_id(mCursor.getString(mCursor.getColumnIndex(M_ID)));
            m.setFullName(mCursor.getString(mCursor.getColumnIndex(M_FULLNAME)));
            m.setPhone(mCursor.getString(mCursor.getColumnIndex(M_PHONE)));
            m.setGender(mCursor.getString(mCursor.getColumnIndex(M_GENDER)));
            m.setDob(UtilityHelper.getDateTime(mCursor.getString(mCursor.getColumnIndex(M_DOB)), true));
            m.setCurrentCity(mCursor.getString(mCursor.getColumnIndex(M_CURRENT_CITY)));
            m.setNric(mCursor.getString(mCursor.getColumnIndex(M_NRIC)));
            m.setEducation(mCursor.getString(mCursor.getColumnIndex(M_EDU)));
            m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
            m.setMarital(mCursor.getString(mCursor.getColumnIndex(M_MARITAL)));
            m.setEmployment(mCursor.getString(mCursor.getColumnIndex(M_EMPLOYMENT)));
            m.setCareer(mCursor.getString(mCursor.getColumnIndex(M_CAREER)));
            m.setIncome(mCursor.getString(mCursor.getColumnIndex(M_INCOME)));
            m.setJobFunction(mCursor.getString(mCursor.getColumnIndex(M_JOBFUNC)));
            m.setTelco(mCursor.getString(mCursor.getColumnIndex(M_TELCO)));
            m.setReligion(mCursor.getString(mCursor.getColumnIndex(M_RELIGION)));
            m.setRace(mCursor.getString(mCursor.getColumnIndex(M_RACE)));
            m.setBankAccount(mCursor.getString(mCursor.getColumnIndex(M_BANK)));
            m.setSmoker(mCursor.getString(mCursor.getColumnIndex(M_SMOKE)));
            m.setDrinker(mCursor.getString(mCursor.getColumnIndex(M_DRINK)));

            String strCreatedOn = mCursor.getString(mCursor.getColumnIndex(M_CREATED));

            m.setCreatedOn(UtilityHelper.getDateTime(strCreatedOn, false));
            m.setReferrer(mCursor.getString(mCursor.getColumnIndex(M_REFERRER)));
            m.setStatus(mCursor.getString(mCursor.getColumnIndex(M_STATUS)));
            mCursor.close();
        }

        return m;
    }

    public void updateRegStatus(String number, String status, String id) {
        SQLiteDatabase db = getWritableDatabase();
        if(id==null){
            db.execSQL("update "+ TABLE_MELLER +" set "+M_STATUS+" ='" + status + "' where "+M_PHONE+" = '" + number + "' ");
        }
        else{
            db.execSQL("update " + TABLE_MELLER + " set " + M_STATUS + " ='" + status + "', " + M_ID + " ='" + id + "'  where " + M_PHONE + " = '" + number + "' ");
        }

        try {
            AppValues.getInstance().mellers.clear();
            AppValues.getInstance().mellers.addAll(getMellers());
            AppValues.getInstance().mellerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByPhone(String number) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_MELLER + " WHERE " + M_PHONE + " = '" + number + "' ");
            AppValues.getInstance().mellers.clear();
            AppValues.getInstance().mellers.addAll(getMellers());
            AppValues.getInstance().mellerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER + " LIMIT 1";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        User u = new User();

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            u.set_id(c.getString(c.getColumnIndex(U__ID)));
            //u.setDob(getDateTime(c.getString(c.getColumnIndex(U_DOB))));
            u.setEmail(c.getString(c.getColumnIndex(U_EMAIL)));
            u.setHashed_password(c.getString(c.getColumnIndex(U_HASHED_PASSWORD)));
            u.setPhone(c.getString(c.getColumnIndex(U_PHONE)));
            u.setUser_name(c.getString(c.getColumnIndex(U_USER_NAME)));
            //u.setName(c.getString(c.getColumnIndex(U_NAME)));
        }

        return u;
    }

    public User getLoggedinUser(String u_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + U_USER_NAME + " = '" +
                u_name + "' LIMIT 1";

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        User loginUser = new User();

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            loginUser.set_id(c.getString(c.getColumnIndex(U__ID)));
            //u.setDob(getDateTime(c.getString(c.getColumnIndex(U_DOB))));
            loginUser.setEmail(c.getString(c.getColumnIndex(U_EMAIL)));
            loginUser.setHashed_password(c.getString(c.getColumnIndex(U_HASHED_PASSWORD)));
            loginUser.setPhone(c.getString(c.getColumnIndex(U_PHONE)));
            loginUser.setUser_name(c.getString(c.getColumnIndex(U_USER_NAME)));
            loginUser.setFullName(c.getString(c.getColumnIndex(U_NAME)));
        }

        return loginUser;
    }

    public void setupRecruiters() {

        try {
            this.deleteUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"astk\",\"astk\",\"9684\", \"Aung Sithu\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"demo\",\"demo\",\"0000\", \"Demo\");");

        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"nawhtoo\",\"nawhtoo\",\"7146\", \"Naw Htoo\");");

        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"tayzar\",\"tayzar\",\"8078\", \"Tay Zar\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"khineeaindra\",\"khineeaindra\",\"9126\",\"Khine Eaindra\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"eiyadana\",\"eiyadana\",\"5710\",\"Ei Yadana\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"juujuu\",\"juujuu\",\"5231\",\"Juu Juu\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"nweniwin\",\"nweniwin\",\"1895\",\"Nwe Ni Win\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"hanzar\",\"hanzar\",\"3511\",\"Han Zar\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"eithinzar\",\"eithinzar\",\"2597\",\"Ei Thinzar\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"tinzar\",\"tinzar\",\"0782\",\"Tin Zar\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"nwenioo\",\"nwenioo\",\"8758\",\"Nwe Ni Oo\");");
        db.execSQL("INSERT INTO user (_id,user_name,hashed_password,name) VALUES (\"shwesin\",\"shwesin\",\"7653\",\"Shwe Sin\");");
    }

    public ArrayList<CallNumber> getNumbers() {


        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CALL_LIST +
                " WHERE assigned_id = '" + AppValues.getInstance().loginUser.getUser_name() + "'";
        Log.e(LOG, selectQuery);
        Cursor mCursor = db.rawQuery(selectQuery, null);

        ArrayList<CallNumber> numbers = new ArrayList<CallNumber>();

        if (mCursor.moveToFirst()) {
            do {
                CallNumber m = new CallNumber();

                m.set_id(mCursor.getString(mCursor.getColumnIndex(P_ID)));
                m.setAssignedTo(mCursor.getString(mCursor.getColumnIndex(P_ASSIGNED_ID)));
                m.setNumber(mCursor.getString(mCursor.getColumnIndex(P_NUMBER)));
                m.setStatus(mCursor.getString(mCursor.getColumnIndex(P_STATUS)));

                try {
                    numbers.add(m);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return numbers;
    }

    public void updateCallNumberStatus(String number, String status) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update call_list set status ='" + status + "' where number = '" + number + "' ");
        AppValues.getInstance().mNumbers = this.getNumbers();

        if (AppValues.getInstance().mNumbers != null &&
        AppValues.getInstance().mNumbers.size() > 0) {
            for (CallNumber cn : AppValues.getInstance().mNumbers) {
                if (cn.getNumber().equals(number)) {
                    cn.setStatus(status);
                }
            }

            AppValues.getInstance().callNumberAdapter.notifyDataSetChanged();
        }
    }
}
