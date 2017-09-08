package com.xavey.diego.api;

import com.xavey.diego.api.model.API_Response;
import com.xavey.diego.api.model.Auth;
import com.xavey.diego.api.model.Meller;
import com.xavey.diego.api.model.User;
import com.xavey.diego.api.model.Version;

import java.util.Date;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 *
 * Created by tinmaungaye on 23.10.14.
 */
public interface DiegoApiInterface {

    @GET("/echo/hello")
    void getEcho(Callback<String> echo);

    @POST("/dump/")
    void postDump(@Body Meller mellerJSON, Callback<API_Response> result);
}