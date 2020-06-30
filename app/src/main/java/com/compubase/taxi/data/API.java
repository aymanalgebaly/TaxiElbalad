package com.compubase.taxi.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register (
            @Field("fn") String fname,
            @Field("ln") String lname,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("pass") String password,
            @Field("img") String img
    );
    @FormUrlEncoded
    @POST("update_user")
    Call<ResponseBody> updateUser (
            @Field("id_user") String id_user,
            @Field("fn") String fname,
            @Field("ln") String lname,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("pass") String password,
            @Field("img") String img
    );
    @FormUrlEncoded
    @POST("sendactivemail")
    Call<ResponseBody>SendSMS(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("entercode_activemail")
    Call<String>EnterCode(
            @Field("enter_code") String code
    );

    @FormUrlEncoded
    @POST("login_user")
    Call<ResponseBody>UserLogin(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("insert_ticket")
    Call<ResponseBody>insertTicket(
            @Field("id_user") String id_user,
            @Field("number_up_five") String number_up_five,
            @Field("number_down_five") String number_down_five,
            @Field("station_arrive") String station_arrive,
            @Field("date_arrive") String date_arrive,
            @Field("time_arrive") String time_arrive,
            @Field("typr_ticket") String typr_ticket
    );

    @FormUrlEncoded
    @POST("select_ticket_by_id_user")
    Call<ResponseBody>Older(
            @Field("id_user") String id_user
    );
}
