package com.example.okhttp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitTest {

    @POST("post")
    @FormUrlEncoded
    abstract fun post(@Field("userName") username: String, @Field("password") pwd: String): Call<ResponseBody>

    @GET("get")
    abstract fun get(@Query("userName") username: String, @Query("password") pwd: String): Call<ResponseBody>
}