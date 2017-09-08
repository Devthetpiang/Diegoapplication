package com.xavey.diego.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.xavey.diego.R;
import com.xavey.diego.api.DiegoClient;
import com.xavey.diego.api.WoodyClient;
import com.xavey.diego.api.model.API_Response;
import com.xavey.diego.api.model.Meller;
import com.xavey.diego.fragment.ProspectsFragment;
import com.xavey.diego.fragment.ReferralsFragment;
import com.xavey.diego.helper.AppValues;
import com.xavey.diego.helper.DBHelper;
import com.xavey.diego.helper.UtilityHelper;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    String AuthToken="";
    Menu mMenu;
    DBHelper dbH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbH = new DBHelper(mContext);
        try {
            View v = (View) navigationView.getHeaderView(0);
            TextView tvMID = (TextView)v.findViewById(R.id.txtDrawerMellerID);
            tvMID.setText(AppValues.getInstance().loginUser.getFull_name());
            TextView tvMobile = (TextView)v.findViewById(R.id.txtDrawerMobile);
            Date d=new Date();
            tvMobile.setText(UtilityHelper.getDateTime(d,false));

        } catch (Exception e) {
            Log.d("AuthAPI", e.getStackTrace().toString());
            getSupportFragmentManager().beginTransaction()
            .add(R.id.container, new ReferralsFragment())
            .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_meller) {
            Intent intent = new Intent(mContext,CreateActivity.class);
            mContext.startActivity(intent);
            return true;
        }
        else if (id == R.id.action_sync_meller) {
            SyncLoop();
            return true;
        }
        else if (id == R.id.action_dump_meller) {
            DumpLoop();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_referrals) {
            if (findViewById(R.id.container) != null) {
                Fragment newFragment = new ReferralsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } else if (id == R.id.nav_prospects) {
            mMenu.setGroupVisible(R.id.sync_menu_group, false);
            if (findViewById(R.id.container) != null) {
                Fragment newFragment = new ProspectsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } else if (id == R.id.nav_settings) {
            mMenu.setGroupVisible(R.id.sync_menu_group, false);
        } else if (id == R.id.nav_logout) {
            finish();
            Intent intent = new Intent(mContext,LoginActivity.class);
            mContext.startActivity(intent);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SyncLoop(){
        try {
            Meller m = dbH.getMellerUnSynced();
            if(m!=null && m.getStatus().equals(AppValues.getInstance().sync_reg_ok)){
                SyncProfile(m);
            }
            else if(m!=null && m.getStatus().equals(AppValues.getInstance().sync_none)){
                SyncReg(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DumpLoop(){
        try {
            Meller m = dbH.getMellerSynced();
            if(m!=null){
                DumpSync(m);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void DumpSync(final Meller m){
        DiegoClient.getDiegoApiClient(this).postDump(m, new Callback<API_Response>() {//change update process
            @Override
            public void success(API_Response apiRes, Response response) {
                if(!apiRes.getMessage().equals(null)&&apiRes.getMessage().equals("010")){
                    dbH.deleteByPhone(m.getPhone());
                    DumpLoop();
                }
                else{
                    Toast.makeText(DashboardActivity.this, "Diego Dump Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(DashboardActivity.this, "Diego Dump Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SyncReg(final Meller m){
        m.setUser_name(m.getPhone());
        if(m.getUser_name().indexOf("+")==0){
            m.setUser_name(m.getUser_name().substring(1,m.getUser_name().length()));
        }
        m.setHashed_password(m.getPhone().substring(m.getPhone().length()-6,m.getPhone().length()));
        WoodyClient.getWoodyApiClient(this).postNewUser(m,m.getReferrer(), new Callback<API_Response>() {//change update process
                    @Override
                    public void success(API_Response apiRes, Response response) {
                        if(!apiRes.getMessage().equals(null)&&apiRes.getMessage().equals("010")){
                            dbH.updateRegStatus(m.getPhone(),AppValues.getInstance().sync_reg_ok,apiRes.getExtra());
                            m.set_id(apiRes.getExtra());
                            SyncProfile(m);
                        }
                        else{
                            dbH.updateRegStatus(m.getPhone(),AppValues.getInstance().sync_reg_error,null);
                            SyncLoop();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dbH.updateRegStatus(m.getPhone(),AppValues.getInstance().sync_reg_error,null);
                        SyncLoop();
                    }
                });
    }
    private void SyncProfile(final Meller m){
        WoodyClient.getWoodyApiClient(this).postFullProfile(m.get_id(),m.getFull_name(),m.getPhone(),m.getGender(),m.getDob(),m.getNric()
                ,m.getCurrent_city(),m.getIncome(),m.getEducation(),m.getMarital(),m.getEmployment(),m.getCareer(),m.getJobFunction(),m.getIndustry()
                ,m.getTelco(),m.getReligion(),m.getRace(),(m.getBankAccount().equals("true")?true:false),(m.getSmoker().equals("true")?true:false)
                ,(m.getDrinker().equals("true")?true:false),"tele_inhouse", new Callback<API_Response>() {//change update process
            @Override
            public void success(API_Response apiRes, Response response) {
                if(!apiRes.getMessage().equals(null)&&apiRes.getMessage().equals("010")){
                    dbH.updateRegStatus(m.getPhone(), AppValues.getInstance().sync_ok, null);
                    SyncLoop();
                }
                else{
                    dbH.updateRegStatus(m.getPhone(),AppValues.getInstance().sync_full_profile_error,null);
                    SyncLoop();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                dbH.updateRegStatus(m.getPhone(),AppValues.getInstance().sync_full_profile_error,null);
                SyncLoop();
            }
        });
    }
}
