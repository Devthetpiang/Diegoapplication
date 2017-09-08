package com.xavey.diego.api;

import com.xavey.diego.api.model.API_Response;
import com.xavey.diego.api.model.Auth;
import com.xavey.diego.api.model.User;
import com.xavey.diego.api.model.Version;

import java.util.Date;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 *
 * Created by tinmaungaye on 23.10.14.
 */
public interface WoodyApiInterface {

    @GET("/api/version")
    void getVersion(Callback<Version> result);

    @FormUrlEncoded
    @POST("/api/oauth/token")
    void postAuthToken(@Field("grant_type") String granttype,
                       @Field("client_id") String clientid,
                       @Field("client_secret") String clientsecret,
                       @Field("username") String username,
                       @Field("password") String password,
                       Callback<Auth> result);

    @Multipart
    @POST("/api/users/regen/")
    void postFullProfile(@Part("user_id") String user_id,
                         @Part("full_name") String full_name,
                         @Part("phone") String phone,
                         @Part("gender") String gender,
                         @Part("dob") Date dob,
                         @Part("nric") String nric,
                         @Part("current_city") String current_city,
                         @Part("income") String income,
                         @Part("education") String education,
                         @Part("marital") String marital,
                         @Part("employment") String employment,
                         @Part("career") String career,
                         @Part("job") String job,
                         @Part("industry") String industry,
                         @Part("telco") String telco,
                         @Part("religion") String religion,
                         @Part("race") String race,
                         @Part("bank_account") Boolean bank_account,
                         @Part("smoker") Boolean smoker,
                         @Part("alcoholic") Boolean alcoholic,
                         @Part("ref_camp") String ref_camp,
                            Callback<API_Response> result);

    //basic registration
    @POST("/api/users/reg/{referalName}")
    void postNewUser(@Body User UserJSON, @Path("referalName") String referalName, Callback<API_Response> result);
}