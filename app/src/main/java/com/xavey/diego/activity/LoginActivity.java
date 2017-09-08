package com.xavey.diego.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xavey.diego.R;
import com.xavey.diego.api.WoodyClient;
import com.xavey.diego.api.model.User;
import com.xavey.diego.helper.AppValues;
import com.xavey.diego.helper.DBHelper;
import com.xavey.diego.api.model.Auth;
import com.xavey.diego.helper.UtilityHelper;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_FROM_ACTIVITY = "LoginActivity.FROM_ACTIVITY";
    public static DBHelper dbH = null;
    private Boolean admin= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ImageView imageview = (ImageView) findViewById(R.id.imageView);
        final Button button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                attemptLogin();
            }
        });
        imageview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(admin){
                    admin = false;
                    imageview.setImageResource(R.mipmap.ic_logo);
                }
                else{
                    admin=true;
                    imageview.setImageResource(R.mipmap.ic_logo_admin);
                }
                Vibrator vb = (Vibrator) LoginActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                return false;
            }
        });

        dbH = new DBHelper(this);
        dbH.setupRecruiters();
    }

    public void attemptLogin() {
        UtilityHelper uh = new UtilityHelper();
        uh.hideSoftKeyboard(LoginActivity.this);

        final String id = ((EditText)findViewById(R.id.txtMellerID)).getText().toString();
        final String password = ((EditText)findViewById(R.id.txtPwd)).getText().toString();
        final Boolean this_is_admin = admin;

        User loggedinUser = new User();
        loggedinUser = dbH.login(id, password);
        if (loggedinUser.getUser_name() != null) {

            AppValues.getInstance().loginUser = loggedinUser;
            AppValues.getInstance().admin_login = this_is_admin;
            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, DashboardActivity.class);
//            intent.putExtra("U_NAME", id);
            startActivity(intent);
        }
        else {
            String grant = this.getString(R.string.api_grant_type);
            String clientID = this.getString(R.string.api_client_id);
            String clientSecret = this.getString(R.string.api_client_secret);

            WoodyClient.getWoodyApiClient(this).postAuthToken(grant, clientID, clientSecret, id, password, new Callback<Auth>() {

                @Override
                public void success(Auth pAuth, Response response) {
                    if (pAuth != null) {
                        Log.d("AuthAPI", pAuth.getAccess_token());
                        try {
                            User u = new User();
                            u.set_id(id);
                            u.setUser_name(id);
                            u.setHashed_password(password);
                            u.setFullName(id);
                            dbH.createUser(u);
                            attemptLogin();
                        } catch (Exception e) {
                            Log.d("AuthAPI", e.getStackTrace().toString());
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    if (error.getMessage().equals(getResources().getString(R.string.api_error_offline))) {

                    }
                    Toast.makeText(LoginActivity.this, "Login Fail!", Toast.LENGTH_SHORT).show();
                }
            });
        }

//        try {
//
//            String grant = this.getString(R.string.api_grant_type);
//            String clientID = this.getString(R.string.api_client_id);
//            String clientSecret = this.getString(R.string.api_client_secret);
//
//            WoodyClient.getWoodyApiClient(this).postAuthToken(grant, clientID, clientSecret,
//                    id, password, new Callback<Auth>() {
//
//                @Override
//                public void success(Auth pAuth, Response response) {
//                    if (pAuth != null) {
//                        Log.d("AuthAPI", pAuth.getAccess_token());
//                        UtilityHelper uh = new UtilityHelper();
//                        uh.hideSoftKeyboard(LoginActivity.this);
//
//                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//
//                        try {
//                            dbH.createAuth(pAuth);
//                            finish();
//                            Intent intent = new Intent();
//                            intent.setClass(LoginActivity.this, DashboardActivity.class);
//                            startActivity(intent);
//                        } catch (Exception e) {
//                            Log.d("AuthAPI", e.getStackTrace().toString());
//                        }
//                    }
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    try {
//                        dbH.deleteUser();
//                        Log.e("Wrong pwd:", "Wrong pwd.");
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                    if (error.getMessage().equals(getResources().getString(R.string.api_error_offline))) {
//
//                        Log.e("Network error:", "Offline");
//                    }
//                    Log.d("AuthAPI", error.toString());
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
